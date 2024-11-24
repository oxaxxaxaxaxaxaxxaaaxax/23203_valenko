#include "card.h"
#include "deck.h"
#include "hand.h"

namespace{
    constexpr size_t ace_value = 11;
    constexpr int ace_bust = -10;
    constexpr size_t victory_point = 21;
}

void Hand::HitCard(Card card){
    hand_.push_back(card);
    AddToSum(card.GetValue());
    if(GetTotalSum() == victory_point){
        bust_mode = true;
    }
    UpdateSum();
}

void Hand::ShowHand()const {
    for(const auto& card : Hand::hand_){
        card.Show();
    }
}

int Hand::GetTotalSum() const{
    return total_summ;
}

void Hand::AddToSum(int value){
    total_summ+=value;
}

void Hand::UpdateSum(){
    if(!bust_mode){
        return;
    }
    for(const auto& card: hand_){
        if(card.GetValue() == ace_value){
            AddToSum(ace_bust);
            //total_summ-= ace_bust;
        }
    }
}