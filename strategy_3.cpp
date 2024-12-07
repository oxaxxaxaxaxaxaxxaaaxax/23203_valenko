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
}


bool Strategy_3:: hit(Card card, Player & player){
    if (!stand_mode){
        player.GetHand().HitCard(card);
        hit_count++;
        if(card.GetValue()<picture_value){
            hit_count --;
        }
        if(card.GetValue() == ace_value){
            hit_count --;
        }
        if(hit_count == 3){
            stand_mode = true;
        }
    }
    return stand_mode;
}



namespace {
static Creator<Strategy_3, Strategy, std::string> c("strategy_3");
}