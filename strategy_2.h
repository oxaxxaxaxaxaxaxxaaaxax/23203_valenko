#pragma once
#include "hand.h"
#include "player.h"
#include "strategy.h"
#include "strategy_play.h"

class Strategy_2 : public Strategy_Play{
public:
    Strategy_2(std::vector<int> strategy_data){}
    bool hit(Card card, Player & player, Card& opponent_card) override;
    //void stand() override;
    //int GetSumm() override;
    //Hand hand_2; 
private:
    size_t hit_count = 0;
    int hit_count_lim =3;
    //bool stand_mode = false;
      
};

