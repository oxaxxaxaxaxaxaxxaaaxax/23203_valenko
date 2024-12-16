#include "strategy_1.h"

#include <iostream>

#include "card.h"
#include "creator.h"
#include "factory.h"
#include "hand.h"
#include "player.h"
#include "strategy.h"

namespace{
    constexpr int hit_limit = 17;
}

bool Strategy_1::hit(Card card,int total_summ){
    if (!stand_mode){ 
        //player.GetHand().HitCard(card);
        //if(player.GetHand().GetTotalSum() >= hit_limit){
        if(total_summ >= hit_limit){
            stand_mode = true; 
        }
    }
    return stand_mode;
}

namespace {
static Creator<Strategy_1, Strategy, std::string,std::vector<int>> c("strategy_1");
}
