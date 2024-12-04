#include "card.h"
#include "creator.h"
#include "deck.h"
#include "engine.h"
#include "engine_1.h"
#include "factory.h"
#include "hand.h"
#include "user_interface.h"
#include "player.h"
#include "strategy.h"
#include <memory>
#include <vector>


void Engine_1::BlackJack(std::vector<std::unique_ptr<Strategy>>& strategy_, std::string& CurDeck, std::string& CurInter){
    //std::unique_ptr<Strategy> str_1 = (Factory<std::string, Strategy, Strategy* (*)()>::GetInstance())->CreateByName(strategy_[0]);
    Player player_1(std::move(strategy_[0]), 1);
    //std::unique_ptr<Strategy> str_2 = (Factory<std::string, Strategy, Strategy* (*)()>::GetInstance())->CreateByName(strategy_[1]);
    Player player_2(std::move(strategy_[1]), 2);
    
    Game(player_1, player_2, CurDeck, CurInter);
}

void Engine_1::Game(Player& player_1, Player& player_2, std::string& CurDeck, std::string& CurInter){
    std::unique_ptr<Deck> deck = (Factory<std::string, Deck, std::function<Deck*()>>::GetInstance())->CreateByName(CurDeck);
    std::unique_ptr<User_Interface> interface = (Factory<std::string, User_Interface, std::function<User_Interface*()>>::GetInstance())->CreateByName(CurInter);
    
    player_1.strategy ->hit(deck->GetCard(), player_1);
    player_1.GetHand().ShowHand();
    player_2.strategy ->hit(deck->GetCard(), player_2);
    player_2.GetHand().ShowHand();

    while((!(player_1.strategy ->hit(deck->GetCard(), player_1))) || (!(player_2.strategy ->hit(deck->GetCard(), player_2)))){

        
        if(player_1.GetHand().GetVicMode() == true){
            interface->ShowWiner(player_1);
            return;
        }
        if(player_1.GetHand().GetBustMode() == true){
            interface->ShowWiner(player_2);
            return;
        }
        if(player_2.GetHand().GetVicMode() == true){
            interface->ShowWiner(player_2);
            return;
        }
        if(player_2.GetHand().GetBustMode() == true){
            interface->ShowWiner(player_1);
            return;
        }
    }
    interface->ShowWiner(ChooseWinner(player_1,player_2));
}


Player& Engine_1::ChooseWinner(Player& pl_1, Player& pl_2){
    return (pl_1.GetHand().GetTotalSum() >= pl_2.GetHand().GetTotalSum()) ? pl_1 : pl_2;
}


namespace{
    Creator<Engine_1, Engine, std::string> c("fast");
}





// Engine * CreateEngine(){
//     return new Engine_1;
// }

// namespace{
//     bool b = (Factory<string, Engine, Engine*(*)()>::GetInstance())->Register("mode_1", &CreateEngine);
// }