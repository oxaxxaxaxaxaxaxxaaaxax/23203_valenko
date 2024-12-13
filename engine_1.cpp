#include "engine_1.h"

#include <memory>
#include <vector>

#include "card.h"
#include "creator.h"
#include "deck.h"
#include "engine.h"
#include "factory.h"
#include "hand.h"
#include "user_interface.h"
#include "player.h"
#include "strategy.h"



void Engine_1::BlackJack(std::vector<std::unique_ptr<Strategy>>& strategy_, std::string& CurDeck,  int deck_data, std::string& CurInter){
    //std::unique_ptr<User_Interface> interface = (Factory<std::string, User_Interface, std::function<User_Interface*()>>::GetInstance())->CreateByName(CurInter);
    std::shared_ptr<Player> player_1(std::make_shared <Player>(std::move(strategy_[0]), 1));
    std::shared_ptr<Player> player_2(std::make_shared <Player>(std::move(strategy_[1]), 2));
    
    Game(player_1, player_2, CurDeck, deck_data, CurInter);
    EndGame(player_1, player_2);
}

void Engine_1::Game(std::shared_ptr<Player> player_1, std::shared_ptr<Player> player_2, std::string& CurDeck,int deck_data, std::string& CurInter){
    std::unique_ptr<Deck> deck = (Factory<std::string, Deck>::GetInstance())->CreateByName(CurDeck,deck_data);
    std::unique_ptr<User_Interface> interface = (Factory<std::string, User_Interface>::GetInstance())->CreateByName(CurInter);
    
    Card card_to_first = deck->GetCard();
    Card card_to_second = deck->GetCard();

    player_1->strategy ->hit(card_to_first, *player_1, card_to_second);
    player_1->GetHand().ShowHand();
    player_2->strategy ->hit(card_to_second, *player_2, card_to_first);
    player_2->GetHand().ShowHand();

    
    while (true) {
        Card card_to_first = deck->GetCard();
        Card card_to_second = deck->GetCard();

        bool player1_stand= player_1->strategy->hit(card_to_first, *player_1,card_to_second);
        bool player2_stand = player_2->strategy->hit(card_to_second, *player_2,card_to_first);

        if (player1_stand && player2_stand) {
            break;
        }
        
        if (player_1->GetHand().GetVicMode() == true){
            interface->ShowWiner(*player_1);
            deck->GetCardBack( player_1->GetHand().ReturnCards());
            deck->GetCardBack( player_2->GetHand().ReturnCards());
            return;
        }
        if (player_1->GetHand().GetBustMode() == true){
            interface->ShowWiner(*player_2);
            deck->GetCardBack( player_1->GetHand().ReturnCards());
            deck->GetCardBack( player_2->GetHand().ReturnCards());
            return;
        }
        if (player_2->GetHand().GetVicMode() == true){
            interface->ShowWiner(*player_2);
            deck->GetCardBack( player_1->GetHand().ReturnCards());
            deck->GetCardBack( player_2->GetHand().ReturnCards());
            return;
        }
        if (player_2->GetHand().GetBustMode() == true){
            interface->ShowWiner(*player_1);
            deck->GetCardBack( player_1->GetHand().ReturnCards());
            deck->GetCardBack( player_2->GetHand().ReturnCards());
            return;
        }
    }
    deck->GetCardBack( player_1->GetHand().ReturnCards());
    deck->GetCardBack( player_2->GetHand().ReturnCards());
    interface->ShowWiner(*ChooseWinner(player_1,player_2));
}

std::shared_ptr<Player> Engine_1::ChooseWinner(std::shared_ptr<Player> pl_1, std::shared_ptr<Player> pl_2){
    return (pl_1->GetHand().GetTotalSum() >= pl_2->GetHand().GetTotalSum()) ? pl_1 : pl_2;
}


void Engine_1::EndGame(std::shared_ptr<Player> pl_1, std::shared_ptr<Player> pl_2){
    pl_1->GetHand().FreeHand();
    pl_1->strategy->End();
    pl_2->GetHand().FreeHand();
    pl_2->strategy->End();
}

namespace{
    Creator<Engine_1, Engine, std::string> c("fast");
}

