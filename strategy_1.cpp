#include "card.h"
#include "factory.h"
#include "strategy.h"
#include "strategy_1.h"
#include <iostream>

using string = std::string;

void Strategy_1:: hit(Card & card){
    //static bool b = true;
    if (!stand_mode){
        total_summ+= card.GetValue();
    }
    if(total_summ >= 17){
        stand_mode = true;
    }
    //std::cout<< "hit:" << total_summ << std::endl;
}

// void Strategy_1:: stand(){
//     std::cout<< "stand" << std::endl;
// }

int Strategy_1::GetSumm(){
    return total_summ;
}

Strategy * CreateStrategy_1(){
    return new Strategy_1();
}



namespace{
    bool b = (Factory<string, Strategy, Strategy * (*)()>::GetInstance())->Register("Strategy_1", &CreateStrategy_1);
}
