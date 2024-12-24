#pragma once
#include <any>
#include <vector>
#include "player.h"
#include "strategy.h"


class Engine{
public:
    virtual void BlackJack(std::vector<std::unique_ptr<Strategy>>& strategy_,const std::string& CurDeck,const int& deck_data,const std::string& CurInter)=0;
    virtual void Game(const std::unique_ptr<Player>& player_1,const std::unique_ptr<Player>& player_2,const std::string& CurDeck,const int& deck_data,const std::string& CurInter)=0;
    virtual const std::unique_ptr<Player>& ChooseWinner(const std::unique_ptr<Player>& pl_1,const std::unique_ptr<Player>& pl_2) =0;
    virtual void EndGame(const std::unique_ptr<Player>& pl_1,const std::unique_ptr<Player>& pl_2) =0;
    virtual ~Engine()=default;
};