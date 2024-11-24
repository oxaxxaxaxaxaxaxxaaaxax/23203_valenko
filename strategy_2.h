#pragma once
#include "hand.h"
#include "strategy.h"

class Strategy_2 : public Strategy{
public:
    Strategy_2();
    void hit(Card & card) override;
    //void stand() override;
    //int GetSumm() override;
    Hand hand_2; 
    ~Strategy_2() override;
private:
    //bool stand_mode = false;
      
};

