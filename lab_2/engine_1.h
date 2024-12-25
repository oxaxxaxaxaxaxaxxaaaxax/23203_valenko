#pragma once
#include <vector>
#include "engine_play.h"



class Engine_1 : public Engine_Play{
public:
    Engine_1(std::string CurDeck,int deck_data,std::string CurInter):Engine_Play(CurDeck,deck_data,CurInter){}
    Engine_1(const Engine_1& b) = delete;
    void BlackJack(std::vector<std::unique_ptr<Strategy>>& strategy_) override;
    void Game(const std::unique_ptr<Player>& player_1,const std::unique_ptr<Player>& player_2) override;
    const std::unique_ptr<Player>& ChooseWinner(const std::unique_ptr<Player>& pl_1, const std::unique_ptr<Player> &pl_2) override;
    void EndGame(const std::unique_ptr<Player>& pl_1, const std::unique_ptr<Player>& pl_2) override;
}; 