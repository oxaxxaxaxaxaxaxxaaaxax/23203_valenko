#include <any>
#include <boost/program_options.hpp>
#include <fstream>
#include <functional>
#include <iostream>
#include <memory>
#include <vector>
#include "deck.h"
#include "engine.h"
#include "factory.h"

namespace po = boost::program_options;
int main(int argc, char* argv[]){ 

    // std::ifstream file("strategy_config.txt");
    // if (!file.is_open()) {
    //     std::cerr << "Ошибка при открытии файла" << std::endl;
    //     return 1;
    // }
    // std::vector <std::string> for_strategy;
    // std::string line;
    // while (std::getline(file, line)) {
    //     if(line.empty()){
    //         continue;
    //     }
    //     std::istringstream iss(line);
    //     std::string key, value;
    //     if (std::getline(iss, key, '=') && std::getline(iss, value)) {
    //         for_strategy.push_back(value);
    //     }
    // }

    po::options_description desc("Allowed options");
    desc.add_options()
    ("help", "produce help message")
    ("strategy", po::value<std::vector<std::string>>()->multitoken(), "set --strategy= your strategy name")
    ("deck", po::value<std::string>()->default_value("basic_deck"), "set --deck=set kind of deck ")
    ("count", po::value<int>()->default_value(1), "set --count=set count of deck, if count==1 skip")
    ("game", po::value<std::string>(), "set --game= your selected mode for game")
    ("interface", po::value<std::string>(), "set --interface=selected interface")
;

    po::variables_map vm;
    po::store(po::parse_command_line(argc, argv, desc), vm);
    po::notify(vm);    



    if (vm.count("help")) {
        std::cout << desc << "\n";
        return 1;
    }

    if(!vm.count("strategy")){
        std::cout << desc << std::endl;
        return 1;
    }
    std::vector<std::string> strategy_name = vm["strategy"].as<std::vector<std::string>>();
        if(strategy_name.size() == 1){
            std::cout << desc << std::endl;
            return 1;
        }
    // std::vector<std::string> strategy_name;
    // strategy_name.push_back("strategy_1");
    // strategy_name.push_back("strategy_2");
    // strategy_name.push_back("strategy_2");
    // strategy_name.push_back("strategy_2");
    // strategy_name.push_back("strategy_1");
    // strategy_name.push_back("strategy_1");
    // strategy_name.push_back("strategy_2");
    // strategy_name.push_back("strategy_2");
    // strategy_name.push_back("strategy_2");
    // strategy_name.push_back("strategy_1");
    // std::string deck_name = "basic_deck";
    // std::string interface_ = "console";
    // std::string game_ = "fast";

    std::vector<std::unique_ptr<Strategy>> strategy_;
    for(const auto& str : strategy_name){
        strategy_.emplace_back(Factory<std::string, Strategy>::GetInstance()->CreateByName(str));
        //std::unique_ptr<Strategy> str_ = Factory<std::string, Strategy, std::function<Strategy*()>>::GetInstance()->CreateByName(str);
    }
    //std::any deck;
    std::vector<int> deck_data;
    int count_deck = vm["count"].as<int>();
    //deck = count_deck;
    deck_data.push_back(count_deck);
    std::string deck_name = vm["deck"].as<std::string>();
    
    if(!vm.count("interface")){
        std::cout << desc << std::endl;
        return 1;
    }
    std::any interface;
    std::vector<std::any> interface_data;
    std::string interface_ = vm["interface"].as<std::string>();
    if(!vm.count("game")){
        std::cout << desc << std::endl;
        return 1;
    }
    std::string game_ = vm["game"].as<std::string>();
    std::unique_ptr<Engine> mode = (Factory<std::string, Engine>::GetInstance()->CreateByName(game_));
    mode->BlackJack(strategy_, deck_name, deck_data, interface_);
    return 0;
}