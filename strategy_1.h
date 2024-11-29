#pragma once
#include "card.h"
#include "hand.h"
#include "player.h"
#include "strategy.h"


class Strategy_1 : public Strategy{
public:
    Strategy_1();
    void hit(Card & card, Player & player) override;
    //void stand() override;
    //int GetSumm() override;
    //Hand hand_1;
private:
    bool stand_mode = false;
    
};

