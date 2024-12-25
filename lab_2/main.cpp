#include <any>
#include <boost/program_options.hpp>
#include <fstream>
#include <functional>
#include <iostream>
#include <memory>
#include <sstream>
#include <string>
#include <vector>
#include "deck.h"
#include "engine.h"
#include "factory.h"

std::vector<int> GetDataStr(std::string config_name){
    std::ifstream file(config_name);
    file.exceptions(std::ios::failbit | std::ios::badbit);
    std::vector <int> for_strategy;
    std::string line, key, value;
    while (true) {
        if(file.eof()){
            break;
        }
        std::getline(file, line);
        std::istringstream iss(line);
        iss.exceptions(std::ios::failbit | std::ios::badbit);
        if (std::getline(iss, key, '=')){
            std::getline(iss, value);
            for_strategy.push_back(std::stoi(value));
        }
    }
    file.close();
    return for_strategy;
}

void EditConfigs(std::vector<std::string>& configs_name, std::string& dir_path, std::vector<std::unique_ptr<Strategy>>& strategy_){
    for(const auto& conf : configs_name){
        std::string file_path = dir_path + "/" + conf;
        strategy_.emplace_back(Factory<std::string, Strategy>::GetInstance()->CreateByName("strategy_config", GetDataStr(file_path)));
    }
}

namespace po = boost::program_options;
int main(int argc, char* argv[]){ 

    po::options_description desc("Allowed options");
    desc.add_options()
    ("help", "produce help message")
    ("strategy", po::value<std::vector<std::string>>()->multitoken(), "set --strategy= your strategy name")
    ("configs", po::value<std::vector<std::string>>()->multitoken(), "set filename to your strategy")
    ("dir_path", po::value<std::string>(), "set path to directory which contains configs file")
    ("deck", po::value<std::string>()->default_value("basic_deck"), "set --deck=set kind of deck ")
    ("count", po::value<int>()->default_value(1), "set --count=set count of deck, if count==1 skip")
    ("game", po::value<std::string>()->default_value("tournament"), "set --game= your selected mode for game")
    ("interface", po::value<std::string>(), "set --interface=selected interface")
;

    po::variables_map vm;
    po::store(po::parse_command_line(argc, argv, desc), vm);
    po::notify(vm);    



    if (vm.count("help")) {
        std::cout << desc << "\n";
        return 1;
    }

    std::vector<std::unique_ptr<Strategy>> strategy_;
    
    if(vm.count("configs")){
        std::string dir_path = vm["dir_path"].as<std::string>();
        std::vector<std::string> configs_name = vm["configs"].as<std::vector<std::string>>();
        if(!configs_name.empty()){
            try{
                EditConfigs(configs_name, dir_path, strategy_);
            }
            catch(const std::ios_base::failure& e){
                std::cerr << "Error while reading a file " << e.what() << std::endl;
                return 1;
            }
        }        
    }


    if(vm.count("strategy")){
        std::vector<std::string> strategy_name = vm["strategy"].as<std::vector<std::string>>();
        std::vector<int> data;
        for(const auto& str : strategy_name){
            strategy_.emplace_back(Factory<std::string, Strategy>::GetInstance()->CreateByName(str, data));
        }
    }
    
    int count_deck = vm["count"].as<int>();
    std::string deck_name = vm["deck"].as<std::string>();
    
    if(!vm.count("interface")){
        std::cout << desc << std::endl;
        return 1;
    }
    
    std::string interface_ = vm["interface"].as<std::string>();
    
    std::string game_ = vm["game"].as<std::string>();
    if(game_ != "tournament"&& game_!="detailed" && game_!= "fast"){
        std::cout<< "Bad game name"<< std::endl;
        std::cout << desc << std::endl;
        return 0;
    }
    std::unique_ptr<Engine> mode = (Factory<std::string, Engine>::GetInstance()->CreateByName(game_, deck_name, count_deck, interface_));
    mode->BlackJack(strategy_);
    return 0;
}