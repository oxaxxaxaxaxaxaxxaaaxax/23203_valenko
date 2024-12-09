#include "strategy_1.h"

#include <iostream>

#include "card.h"
#include "creator.h"
#include "factory.h"
#include "hand.h"
#include "player.h"
#include "strategy.h"


bool Strategy_1:: hit(Card card, Player & player){
    if (!stand_mode){
        player.GetHand().HitCard(card);
        if(player.GetHand().GetTotalSum() >= 17){
            stand_mode = true; 
        }
    }
    return stand_mode;
}

namespace {
static Creator<Strategy_1, Strategy, std::string> c("strategy_1");
}
