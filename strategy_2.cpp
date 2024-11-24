#include "card.h"
#include "deck.h"
#include "factory.h"
#include "strategy.h"
#include "strategy_2.h"


void Strategy_2::hit(Card & card){
    total_summ+= card.GetValue();
}

int Strategy_2::GetSumm(){
    return total_summ;
}

Strategy * CreateStrategy_2(){
    return new Strategy_2();
}

namespace{
    bool b = (Factory<string, Strategy, Strategy * (*)()>::GetInstance())->Register("Strategy_2", &CreateStrategy_2);
}