#pragma once
#include "engine.h"
#include <vector>


class Engine_2 : public Engine{
public:
    void BlackJack(std::vector<std::unique_ptr<Strategy>>& strategy_, std::string& CurDeck,std::string& CurInter) override;
    void Game(Player& player_1, Player& player_2, std::string& CurDeck, std::string& CurInter) override;
}; 