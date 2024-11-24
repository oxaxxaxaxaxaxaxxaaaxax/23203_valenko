#include "card.h"
#include "factory.h"
#include "hand.h"
#include "strategy.h"
#include "strategy_1.h"
#include <iostream>

using string = std::string;

void Strategy_1:: hit(Card & card){
    if (!stand_mode){
        hand_1.SetSum(card.GetValue());
    }
    if(hand_1.GetSumm() >= 17){
        stand_mode = true;
    }
    //std::cout<< "hit:" << total_summ << std::endl;
}

// void Strategy_1:: stand(){
//     std::cout<< "stand" << std::endl;
// }


Strategy * CreateStrategy_1(){
    return new Strategy_1();
}



namespace{
    bool b = (Factory<string, Strategy, Strategy * (*)()>::GetInstance())->Register("Strategy_1", &CreateStrategy_1);
}
