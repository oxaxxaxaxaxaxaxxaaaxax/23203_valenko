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
#include <map>
#include <memory>
#include <numeric>
#include <string>
#include <vector>

void Engine_2::BlackJack(std::vector<std::unique_ptr<Strategy>>& strategy_, std::string& CurDeck, std::string& CurInter){
    std::unique_ptr<User_Interface> interface = (Factory<std::string, User_Interface, std::function<User_Interface*()>>::GetInstance())->CreateByName(CurInter);
    //std::vector<Player> players_;
    std::vector<size_t> numbers_(strategy_.size());
    std::iota(numbers_.begin(), numbers_.end(), 1);
    for(auto& str : strategy_){
        // Player player_(std::move(str), numbers_.back());
        // players_.push_back(std::move(player_));
        // numbers_.pop_back();
        //players_.emplace(std::make_shared<Player>()б);
        players_.emplace_back(std::make_shared<Player>(std::move(str), numbers_.back()));
        numbers_.pop_back();
    }
    for(auto& player : players_){
        tournament_table[player] = 0; 
    }
    for(auto& player_1 : players_){
        for(auto& player_2 : players_){
            if(player_1 >= player_2) continue;
            Game(player_1, player_2, CurDeck, CurInter);
        }
    } 
    interface->ShowWiner(ChooseTournamentWinner());
}

void Engine_2::Game(std::shared_ptr<Player> player_1, std::shared_ptr<Player> player_2, std::string& CurDeck, std::string& CurInter){
    std::unique_ptr<Deck> deck = (Factory<std::string, Deck, std::function<Deck*()>>::GetInstance())->CreateByName(CurDeck);
    std::unique_ptr<User_Interface> interface = (Factory<std::string, User_Interface, std::function<User_Interface*()>>::GetInstance())->CreateByName(CurInter);
    
    player_1->strategy ->hit(deck->GetCard(), *player_1);
    player_1->GetHand().ShowHand();
    player_2->strategy ->hit(deck->GetCard(), *player_2);
    player_2->GetHand().ShowHand();

    while((!(player_1->strategy ->hit(deck->GetCard(), *player_1))) || (!(player_2->strategy ->hit(deck->GetCard(), *player_2)))){

        if(player_1->GetHand().GetVicMode() == true){
            tournament_table[player_1] = ++tournament_table.at(player_1);
            //interface->ShowWiner(player_1);
            return;
        }
        if(player_1->GetHand().GetBustMode() == true){
            tournament_table[player_2] = ++tournament_table.at(player_2);
            //interface->ShowWiner(player_2);
            return;
        }
        if(player_2->GetHand().GetVicMode() == true){
            tournament_table[player_2] = ++tournament_table.at(player_2);
            //interface->ShowWiner(player_2);
            return;
        }
        if(player_2->GetHand().GetBustMode() == true){
            tournament_table[player_1] = ++tournament_table.at(player_1);
            //interface->ShowWiner(player_1);
            return;
        }
    }
    tournament_table[ChooseWinner(player_1,player_2)] = ++tournament_table.at(ChooseWinner(player_1,player_2));
    //deck->GetCardBack(player_1->GetHand());
    player_1->GetHand().ReturnCards();

}

std::shared_ptr<Player> Engine_2::ChooseWinner(std::shared_ptr<Player> pl_1, std::shared_ptr<Player> pl_2){
    return (pl_1->GetHand().GetTotalSum() >= pl_2->GetHand().GetTotalSum()) ? pl_1 : pl_2;
}

size_t Engine_2::ChooseTournamentWinner(){
    size_t winner_score = 0;
    size_t winner_number = 0;
    for(auto& player : players_){
        if(winner_score < tournament_table[player]){
            winner_score = tournament_table[player];
            winner_number = (*player).GetNumber();
        }
    }
    return winner_number;
}


namespace{
    Creator<Engine_2, Engine, std::string> c("tournament");
}


