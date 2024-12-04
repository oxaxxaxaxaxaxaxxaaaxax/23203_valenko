#pragma once
#include "engine.h"
#include <vector>


class Engine_1 : public Engine{
public:
    void BlackJack(std::vector<std::unique_ptr<Strategy>>& strategy_, std::string& CurDeck,std::string& CurInter) override;
    void Game(std::shared_ptr<Player> player_1, std::shared_ptr<Player> player_2, std::string& CurDeck, std::string& CurInter) override;
    std::shared_ptr<Player> ChooseWinner(std::shared_ptr<Player> pl_1, std::shared_ptr<Player> pl_2) override;
    // void Game(Player& player_1, Player& player_2, std::string& CurDeck, std::string& CurInter) override;
    // Player& ChooseWinner(Player& pl_1, Player& pl_2) override;
    //Player& ShowBust(Player& pl_1, Player& pl_2);
    //void BustOrVic(Player& player);
}; 