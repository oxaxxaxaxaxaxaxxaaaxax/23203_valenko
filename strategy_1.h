#pragma once
#include "card.h"
#include "hand.h"
#include "player.h"
#include "strategy_play.h"


class Strategy_1 : public Strategy_Play{
public:
    Strategy_1() = default;
    bool hit(Card card, Player & player) override;
private:    
};

