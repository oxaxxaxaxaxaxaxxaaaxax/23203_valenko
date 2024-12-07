#pragma once
#include "card.h"
#include "hand.h"
#include "player.h"
#include "strategy_play.h"


class Strategy_3 : public Strategy_Play{
public:
    Strategy_3() = default;
    bool hit(Card card, Player & player) override;
    //friend std::ostream& operator<<(std::ostream& os, std::unique_ptr<Strategy_1> str);
    //void stand() override;
    //int GetSumm() override;
    //Hand hand_1;
private:
    size_t hit_count = 0;
    
};