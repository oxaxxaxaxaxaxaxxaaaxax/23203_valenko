#pragma once
#include <vector>
#include "engine.h"



class Engine_1 : public Engine{
public:
    Engine_1(){}
    Engine_1(const Engine_1& b) = delete;
    void BlackJack(std::vector<std::unique_ptr<Strategy>>& strategy_,const std::string& CurDeck,const int& deck_data,const std::string& CurInter) override;
    void Game(std::shared_ptr<Player> player_1, std::shared_ptr<Player> player_2,const std::string& CurDeck,const int& deck_data, const std::string& CurInter) override;
    std::shared_ptr<Player> ChooseWinner(std::shared_ptr<Player> pl_1, std::shared_ptr<Player> pl_2) override;
    void EndGame(std::shared_ptr<Player> pl_1, std::shared_ptr<Player> pl_2) override;
}; 