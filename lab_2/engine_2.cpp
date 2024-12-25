#include "engine_2.h"

#include <map>
#include <memory>
#include <numeric>
#include <string>
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
#include "strategy_play.h"


void Engine_2::BlackJack(std::vector<std::unique_ptr<Strategy>>& strategy_){
    std::unique_ptr<User_Interface> interface = (Factory<std::string, User_Interface>::GetInstance())->CreateByName(CurInter);
    std::vector<size_t> numbers_(strategy_.size());
    std::iota(numbers_.begin(), numbers_.end(), 1);
    for(auto& str : strategy_){
        players_.push_back(std::move(std::make_unique<Player>(std::move(str), numbers_.back())));
        numbers_.pop_back();
    }
    for(auto& player : players_){
        tournament_table[player->GetNumber()] = 0; 
    }
    for(auto& player_1 : players_){
        for(auto& player_2 : players_){
            if(player_1 >= player_2) continue;
            Game(player_1, player_2);

        }
    } 
    interface->ShowWiner(ChooseTournamentWinner());
}

void Engine_2::Game(const std::unique_ptr<Player>& player_1,const std::unique_ptr<Player>& player_2){
    std::unique_ptr<Deck> deck = (Factory<std::string, Deck>::GetInstance())->CreateByName(CurDeck,deck_data);
    std::unique_ptr<User_Interface> interface = (Factory<std::string, User_Interface>::GetInstance())->CreateByName(CurInter);
    
    round++;

    interface->ShowRound(round);
    
    player_1->PlayerHit(deck->GetCard());
    player_1->ShowHand();
    player_2->PlayerHit(deck->GetCard());
    player_2->ShowHand();


    while (true) {

        bool player1_stand = player_1->PlayerHit(deck->GetCard());
        bool player2_stand = player_2->PlayerHit(deck->GetCard());
        if (player1_stand && player2_stand) {
            break;
        }

        if(player_1->GetHand().GetVicMode() == true){
            tournament_table[player_1->GetNumber()] = ++tournament_table.at(player_1->GetNumber());
            deck->GetCardBack( player_1->GetHand().ReturnCards());
            deck->GetCardBack( player_2->GetHand().ReturnCards());
            EndGame(player_1, player_2);
            return;
        }
        if(player_1->GetHand().GetBustMode() == true){
            tournament_table[player_2->GetNumber()] = ++tournament_table.at(player_2->GetNumber());
            deck->GetCardBack( player_1->GetHand().ReturnCards());
            deck->GetCardBack( player_2->GetHand().ReturnCards());
            EndGame(player_1, player_2);
            return;
        }
        if(player_2->GetHand().GetVicMode() == true){
            tournament_table[player_2->GetNumber()] = ++tournament_table.at(player_2->GetNumber());
            deck->GetCardBack( player_1->GetHand().ReturnCards());
            deck->GetCardBack( player_2->GetHand().ReturnCards());
            EndGame(player_1, player_2);
            return;
        }
        if(player_2->GetHand().GetBustMode() == true){
            tournament_table[player_1->GetNumber()] = ++tournament_table.at(player_1->GetNumber());
            deck->GetCardBack( player_1->GetHand().ReturnCards());
            deck->GetCardBack( player_2->GetHand().ReturnCards());
            EndGame(player_1, player_2);
            return;
        }
    }
    tournament_table[ChooseWinner(player_1,player_2)->GetNumber()] = ++tournament_table.at(ChooseWinner(player_1,player_2)->GetNumber());
    deck->GetCardBack( player_1->GetHand().ReturnCards());
    deck->GetCardBack( player_2->GetHand().ReturnCards());
    EndGame(player_1, player_2);
}

const std::unique_ptr<Player>& Engine_2::ChooseWinner(const std::unique_ptr<Player>& pl_1,const std::unique_ptr<Player>& pl_2){
    return (pl_1->GetHand().GetTotalSum() >= pl_2->GetHand().GetTotalSum()) ? pl_1 : pl_2;
}

size_t Engine_2::ChooseTournamentWinner(){
    size_t winner_score = 0;
    size_t winner_number = 0;
    for(auto& player : players_){
        if(winner_score < tournament_table[player->GetNumber()]){
            winner_score = tournament_table[player->GetNumber()];
            winner_number = (*player).GetNumber();
        }
    }
    return winner_number;
}


void Engine_2::EndGame(const std::unique_ptr<Player>& pl_1,const std::unique_ptr<Player>& pl_2){
    pl_1->GetHand().FreeHand();
    pl_1->strategy->End();
    pl_2->GetHand().FreeHand();
    pl_2->strategy->End();
}

namespace{
    Creator<Engine_2, Engine,std::string,std::string, int,std::string> c("tournament");
}


