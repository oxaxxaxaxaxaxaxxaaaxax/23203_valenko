#pragma once
#include "card.h"
#include "hand.h"
#include "player.h"
#include "strategy_play.h"


class Strategy_1 : public Strategy_Play{
public:
    Strategy_1(std::vector<int> strategy_data){}
    bool hit(Card card, Player & player, Card& opponent_card) override;
private:  
    int hit_limit=17;
};

