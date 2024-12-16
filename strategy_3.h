#pragma once
#include "card.h"
#include "hand.h"
#include "player.h"
#include "strategy_play.h"


class Strategy_3 : public Strategy_Play{
public:
    Strategy_3(std::vector<int> strategy_data){}
    bool hit(Card card,int total_summ) override;
private:
    size_t hit_count = 0;
    
};