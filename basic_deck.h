#pragma once
#include "card.h"
#include "deck.h"
#include <cstdlib>
#include <vector>


class Basic_Deck : public Deck{
public:
    Basic_Deck();
    Card GetCard() override;  
    void Shuffle(); 
    ~Basic_Deck() = default;           
    //динамический массив карт
private:
    std::vector<Card> deck;
};