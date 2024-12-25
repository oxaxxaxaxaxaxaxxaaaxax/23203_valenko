#pragma once
#include <any>
#include <vector>
#include "engine.h"
#include "player.h"
#include "strategy.h"


class Engine_Play : public Engine{
public:
    Engine_Play(std::string CurDeck_,int deck_data_,std::string CurInter_):CurDeck(CurDeck_),deck_data(deck_data_),CurInter(CurInter_){}
    virtual void Game(const std::unique_ptr<Player>& player_1,const std::unique_ptr<Player>& player_2)=0;
    virtual const std::unique_ptr<Player>& ChooseWinner(const std::unique_ptr<Player>& pl_1,const std::unique_ptr<Player>& pl_2) =0;
    virtual void EndGame(const std::unique_ptr<Player>& pl_1,const std::unique_ptr<Player>& pl_2) =0;
    virtual ~Engine_Play()=default;
protected:
    std::string CurDeck;
    int deck_data;
    std::string CurInter;
};