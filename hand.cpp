#include <iostream>
#include "hand.h"
#include "card.h"
#include "deck.h"


namespace{
    constexpr size_t ace_value = 11;
    constexpr int ace_bust = -10;
    constexpr size_t victory_point = 21;
}

void Hand::HitCard(Card card){
    hand_.push_back(card);
    AddToSum(card.GetValue());
    CheckVictory();
    if(GetTotalSum() > victory_point){
        UpdateSum();
    }
    CheckBust();
}

void Hand::CheckVictory(){
    if(GetTotalSum() == victory_point){
        victory_mode = true;
    }  
} 

void Hand::CheckBust(){
    if(GetTotalSum() > victory_point){
        bust_mode = true;
    }
}

void Hand::ShowHand()const {
    std::cout<< " " << std::endl;
    std::cout<< "Cards: ";
    for(const auto& card : Hand::hand_){
        card.Show();
    }
}

int Hand::GetTotalSum() const{
    return total_summ;
}

void Hand::AddToSum(int value){
    total_summ += value;
}

void Hand::UpdateSum(){
    for(const auto& card: hand_){
        if(card.GetValue() == ace_value){
            AddToSum(ace_bust);
        }
    }
}

void Hand::FreeHand(){
    while(!(hand_.empty())){
        hand_.pop_back();
    }
    bust_mode = false;
    victory_mode = false;
    total_summ =0;
}
