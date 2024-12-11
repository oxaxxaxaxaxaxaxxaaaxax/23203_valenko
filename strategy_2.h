#pragma once
#include "hand.h"
#include "player.h"
#include "strategy.h"
#include "strategy_play.h"

class Strategy_2 : public Strategy_Play{
public:
    //Strategy_2(){}
    bool hit(Card card, Player & player) override;
    //void stand() override;
    //int GetSumm() override;
    //Hand hand_2; 
private:
    size_t hit_count = 0;
    //bool stand_mode = false;
      
};

