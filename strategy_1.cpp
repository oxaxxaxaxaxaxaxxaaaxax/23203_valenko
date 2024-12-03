#include "strategy_1.h"

#include <iostream>

#include "card.h"
#include "creator.h"
#include "factory.h"
#include "hand.h"
#include "player.h"
#include "strategy.h"


// std::ostream& operator<<(std::ostream& os, std::unique_ptr<Strategy_1> str){
//     if(str->stand_mode){
//         os << "stand" ;
//     }
//     os << "hit";
// }


bool Strategy_1:: hit(Card card, Player & player){
    if (!stand_mode){
        player.GetHand().HitCard(card);
        if(player.GetHand().GetTotalSum() >= 17){
            stand_mode = true; 
        }
    }
    return stand_mode;
    //std::cout<< "hit:" << total_summ << std::endl;
}

// void Strategy_1:: stand(){
//     std::cout<< "stand" << std::endl;
// }




namespace {
static Creator<Strategy_1, Strategy, std::string> c("strategy_1");
}
// Strategy * CreateStrategy_1(){
//     return new Strategy_1();
// }



// namespace{
//     bool b = (Factory<string, Strategy, Strategy * (*)()>::GetInstance())->Register("Strategy_1", &CreateStrategy_1);
// }
