#include "engine_3.h"

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



void Engine_3::BlackJack(std::vector<std::unique_ptr<Strategy>>& strategy_, std::string& CurDeck, std::string& CurInter){
    Player player_1(strategy_[0], 1);
    Player player_2(strategy_[1], 2);
    
    Game(player_1, player_2, CurDeck, CurInter);
}

void Game(Player& player_1, Player& player_2, std::string& CurDeck, std::string& CurInter){
    std::unique_ptr<Deck> deck = (Factory<std::string, Deck, Deck* (*)()>::GetInstance())->CreateByName(CurDeck);
    std::unique_ptr<User_Interface> interface = (Factory<std::string, User_Interface, User_Interface* (*)()>::GetInstance())->CreateByName(CurInter);
    
    player_1.strategy ->hit(deck->GetCard(), player_1);
    player_1.GetHand().ShowHand();
    player_2.strategy ->hit(deck->GetCard(), player_2);
    player_2.GetHand().ShowHand();

}