#include "strategy_3.h"

#include <iostream>

#include "card.h"
#include "creator.h"
#include "factory.h"
#include "hand.h"
#include "player.h"
#include "strategy.h"

namespace{
    constexpr size_t picture_value = 10;
    constexpr size_t ace_value = 11;
    constexpr size_t hit_count_lim = 3;
}


bool Strategy_3:: hit(Card card,int total_summ){
    if (!stand_mode){
        //player.GetHand().HitCard(card);
        hit_count++;
        if(card.GetValue()<picture_value){
            hit_count --;
        }
        if(card.GetValue() == ace_value){
            hit_count --;
        }
        if(hit_count == hit_count_lim){
            stand_mode = true;
        }
    }
    return stand_mode;
}



namespace {
static Creator<Strategy_3, Strategy, std::string, std::vector<int>> c("strategy_3");
}