#pragma once
#include <any>
#include <vector>
#include "player.h"
#include "strategy.h"


class Engine{
public:
    virtual void BlackJack(std::vector<std::unique_ptr<Strategy>>& strategy_, std::string& CurDeck, int deck_data, std::string& CurInter)=0;
    virtual void Game(std::shared_ptr<Player> player_1, std::shared_ptr<Player> player_2,std::string& CurDeck, int deck_data,std::string& CurInter)=0;
    virtual std::shared_ptr<Player> ChooseWinner(std::shared_ptr<Player> pl_1, std::shared_ptr<Player> pl_2) =0;
    virtual void EndGame(std::shared_ptr<Player> pl_1, std::shared_ptr<Player> pl_2) =0;
    virtual ~Engine()=default;
};