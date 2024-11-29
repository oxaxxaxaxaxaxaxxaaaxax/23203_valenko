#include "card.h"
#include "creator.h"
#include "deck.h"
#include "engine.h"
#include "engine_2.h"
#include "factory.h"
#include "hand.h"
#include "user_interface.h"
#include "player.h"
#include "strategy.h"
#include <memory>
#include <string>
#include <vector>

void Engine_2::BlackJack(std::vector<std::unique_ptr<Strategy>>& strategy_, std::string& CurDeck, std::string& CurInter){
    std::vector<size_t> number;
    std::iota(number.begin(), number.end(), 1);
    for(const auto& str : strategy_){
        Player play_ (str);
    }


    for(const auto& str_1 : strategy_){
        for(const auto& str_2 : strategy_){
            if(str_1 == str_2) continue;
            Player play_1(str_1);
            Player play_2(str_2);
            Game(play_1, play_2, CurDeck, CurInter);
        }
    }
    
    
}

void Engine_2::Game(Player& player_1, Player& player_2, std::string& CurDeck, std::string& CurInter){
    std::unique_ptr<Deck> deck = (Factory<std::string, Deck, std::function<Deck>>::GetInstance())->CreateByName(CurDeck);
    std::unique_ptr<User_Interface> interface = (Factory<std::string, User_Interface, std::function<User_Interface>>::GetInstance())->CreateByName(CurInter);
    
}

namespace{
    Creator<Engine_2> c;
}


