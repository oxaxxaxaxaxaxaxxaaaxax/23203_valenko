#pragma once
#include "card.h"
#include "hand.h"
#include "strategy.h"


class Strategy_1 : public Strategy{
public:
    void hit(Card & card) override;
    //void stand() override;
    //int GetSumm() override;
    Hand hand_1;
    ~Strategy_1() override;
private:
    bool stand_mode = false;
    
};

