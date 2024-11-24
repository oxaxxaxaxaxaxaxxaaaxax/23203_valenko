#pragma once
#include "strategy.h"

class Strategy_2 : public Strategy{
public:
    void hit(Card & card) override;
    //void stand() override;
private:
    size_t total_summ = 0;
    bool stand_mode = false;   
};

