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
    std::vector<Player> players_;
    std::vector<size_t> numbers_;
    std::iota(numbers_.begin(), numbers_.end(), 1);
    for(auto& str : strategy_){
        players_.emplace_back(std::move(str), numbers_.back());
        numbers_.pop_back();
    }
    for(auto& player_1 : players_){
        for(auto& player_2 : players_){
            if(player_1 >= player_2) continue;
            Game(player_1, player_2, CurDeck, CurInter);
        }
    }  
}

void Engine_2::Game(Player& player_1, Player& player_2, std::string& CurDeck, std::string& CurInter){
    std::unique_ptr<Deck> deck = (Factory<std::string, Deck, std::function<Deck*()>>::GetInstance())->CreateByName(CurDeck);
    std::unique_ptr<User_Interface> interface = (Factory<std::string, User_Interface, std::function<User_Interface*()>>::GetInstance())->CreateByName(CurInter);
    
    player_1.strategy ->hit(deck->GetCard(), player_1);
    player_1.GetHand().ShowHand();
    player_2.strategy ->hit(deck->GetCard(), player_2);
    player_2.GetHand().ShowHand();

    while(1){

        player_1.strategy ->hit(deck->GetCard(), player_1);
        if(player_1.GetHand().GetVicMode() == true){
            interface->ShowWiner(player_1);
            return;
        }
        if(player_1.GetHand().GetBustMode() == true){
            interface->ShowWiner(player_2);
            return;
        }
        player_2.strategy ->hit(deck->GetCard(), player_2);
        if(player_2.GetHand().GetVicMode() == true){
            interface->ShowWiner(player_2);
            return;
        }
        if(player_2.GetHand().GetBustMode() == true){
            interface->ShowWiner(player_1);
            return;
        }
        
    }

}

namespace{
    Creator<Engine_2, Engine, std::string> c("tournament");
}


