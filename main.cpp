#include "deck.h"
#include "engine.h"
#include "factory.h"
#include <functional>
#include <iostream>
#include <memory>
#include <vector>
#include <boost/program_options.hpp>

namespace po = boost::program_options;

main(int argc, char* argv[]){
    po::options_description desc("Allowed options");
    desc.add_options()
    ("help", "produce help message")
    //("compression", po::value<int>(), "set compression level")
    ("strategy", po::value<std::vector<std::string>>(), "set --strategy= your strategy name")
    ("deck", po::value<std::string>(), "set --deck=set count of deck ")
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



    // if(!vm.count("strategy")){
    //     std::cout << desc << std::endl;
    //     return 1;
    // }
    // std::vector<std::string> strategy_name = vm["strategy"].as<std::vector<std::string>>();
    //     if(strategy_name.size() == 1){
    //         std::cout << desc << std::endl;
    //         return 1;
    //     }
    std::vector<std::string> strategy_name;
    strategy_name.push_back("strategy_1");
    strategy_name.push_back("strategy_2");
    strategy_name.push_back("strategy_2");
    strategy_name.push_back("strategy_2");
    std::string deck_name = "n_deck";
    std::string interface_ = "console";
    std::string game_ = "detailed";

    std::vector<std::unique_ptr<Strategy>> strategy_;
    for(const auto& str : strategy_name){
        strategy_.emplace_back(Factory<std::string, Strategy, std::function<Strategy*()>>::GetInstance()->CreateByName(str));
        //std::unique_ptr<Strategy> str_ = Factory<std::string, Strategy, std::function<Strategy*()>>::GetInstance()->CreateByName(str);
    }

    // if(!vm.count("deck")){
    //     std::cout << desc << std::endl;
    //     return 1;
    // }
    // std::string deck_name = vm["deck"].as<std::string>();
    // if(!vm.count("interface")){
    //     std::cout << desc << std::endl;
    //     return 1;
    // }
    // std::unique_ptr<Deck> deck = (Factory<std::string, Deck, std::function<Deck*()>>::GetInstance())->CreateByName(deck_name);
    // std::string interface_ = vm["interface"].as<std::string>();
    // if(!vm.count("game")){
    //     std::cout << desc << std::endl;
    //     return 1;
    // }
    // std::string game_ = vm["game"].as<std::string>();

    std::unique_ptr<Engine> mode = (Factory<std::string, Engine, std::function<Engine*()>>::GetInstance())->CreateByName(game_);
    mode->BlackJack(strategy_, deck_name, interface_);
    return 0;
}