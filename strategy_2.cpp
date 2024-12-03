#include "strategy_2.h"

#include "card.h"
#include "creator.h"
#include "deck.h"
#include "factory.h"
#include "strategy.h"


bool Strategy_2::hit(Card card, Player & player){
    if(!stand_mode){
        player.GetHand().HitCard(card);
        hit_count++;
        if(hit_count == 3){
            stand_mode = true;
        }
    }
    return stand_mode;
}


namespace{
    static Creator<Strategy_2,Strategy, std::string> c("strategy_2");
}

// Strategy * CreateStrategy_2(){
//     return new Strategy_2();
// }

// namespace{
//     bool b = (Factory<string, Strategy, Strategy * (*)()>::GetInstance())->Register("Strategy_2", &CreateStrategy_2);
// }