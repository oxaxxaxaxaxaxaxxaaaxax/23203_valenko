#include "engine_3.h"

#include <memory>
#include <vector>

#include "card.h"
#include "creator.h"
#include "deck.h"
#include "engine.h"
#include "engine_3.h"
#include "factory.h"
#include "hand.h"
#include "user_interface.h"
#include "player.h"
#include "strategy.h"
#include "strategy_play.h"


bool Engine_3::IsQuit(std::istream& is){
    std::getline(is, quit_mode);
    if(quit_mode == "quit"){
        return true;
    }
    return false;
}

void Engine_3::BlackJack(std::vector<std::unique_ptr<Strategy>>& strategy_, std::string& CurDeck, std::string& CurInter){
    // Player player_1(std::move(strategy_[0]), 1);
    // Player player_2(std::move(strategy_[1]), 2);
    std::unique_ptr<User_Interface> interface = (Factory<std::string, User_Interface, std::function<User_Interface*()>>::GetInstance())->CreateByName(CurInter);
    std::shared_ptr<Player> player_1(std::make_shared <Player>(std::move(strategy_[0]), 1));
    std::shared_ptr<Player> player_2(std::make_shared <Player>(std::move(strategy_[1]), 2));
    
    Game(player_1, player_2, CurDeck, CurInter);
    interface->ShowWiner(*ChooseWinner(player_1,player_2));
    EndGame(player_1, player_2);
}

void Engine_3::Game(std::shared_ptr<Player> player_1, std::shared_ptr<Player> player_2, std::string& CurDeck, std::string& CurInter){
    std::unique_ptr<Deck> deck = (Factory<std::string, Deck, std::function<Deck*()>>::GetInstance())->CreateByName(CurDeck);
    std::unique_ptr<User_Interface> interface = (Factory<std::string, User_Interface, std::function<User_Interface*()>>::GetInstance())->CreateByName(CurInter);
    
    player_1->strategy ->hit(deck->GetCard(), *player_1);
    player_1->GetHand().ShowHand();
    player_2->strategy ->hit(deck->GetCard(), *player_2);
    player_2->GetHand().ShowHand();

    while(!(player_1->strategy ->hit(deck->GetCard(), *player_1)) || !(player_2->strategy ->hit(deck->GetCard(), *player_2))){

        if(IsQuit(std::cin)){
            return;
        }
        player_1->GetHand().ShowHand();
        interface->ShowMethod(player_1->strategy->GetStandMode());
        interface->ShowCardScore(player_1->GetHand().ShowLastCard().GetValue());
        interface->ShowScore(player_1->GetHand().GetTotalSum());

        if(IsQuit(std::cin)){
            return;
        }
        player_2->GetHand().ShowHand();
        interface->ShowMethod(player_2->strategy->GetStandMode());
        interface->ShowCardScore(player_2->GetHand().ShowLastCard().GetValue());
        interface->ShowScore(player_2->GetHand().GetTotalSum());

        if(player_1->GetHand().GetVicMode() == true){
            interface->ShowWiner(*player_1);
            deck->GetCardBack( player_1->GetHand().ReturnCards());
            deck->GetCardBack( player_2->GetHand().ReturnCards());
            return;
        }
        if(player_1->GetHand().GetBustMode() == true){
            interface->ShowWiner(*player_2);
            deck->GetCardBack( player_1->GetHand().ReturnCards());
            deck->GetCardBack( player_2->GetHand().ReturnCards());
            return;
        }
        if(player_2->GetHand().GetVicMode() == true){
            interface->ShowWiner(*player_2);
            deck->GetCardBack( player_1->GetHand().ReturnCards());
            deck->GetCardBack( player_2->GetHand().ReturnCards());
            return;
        }
        if(player_2->GetHand().GetBustMode() == true){
            interface->ShowWiner(*player_1);
            deck->GetCardBack( player_1->GetHand().ReturnCards());
            deck->GetCardBack( player_2->GetHand().ReturnCards());
            return;
        }
    }
    if(IsQuit(std::cin)){
        return;
    }
    deck->GetCardBack( player_1->GetHand().ReturnCards());
    deck->GetCardBack( player_2->GetHand().ReturnCards()); 
}


std::shared_ptr<Player> Engine_3::ChooseWinner(std::shared_ptr<Player> pl_1, std::shared_ptr<Player> pl_2){
    return (pl_1->GetHand().GetTotalSum() >= pl_2->GetHand().GetTotalSum()) ? pl_1 : pl_2;
}

void Engine_3::EndGame(std::shared_ptr<Player> pl_1, std::shared_ptr<Player> pl_2){
    pl_1->GetHand().FreeHand();
    pl_1->strategy->End();
    pl_2->GetHand().FreeHand();
    pl_2->strategy->End();
}

namespace{
    Creator<Engine_3, Engine, std::string> c("detailed");
}