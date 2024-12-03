#pragma once
#include "card.h"
#include "deck.h"
#include <cstdlib>
#include <vector>


class N_Deck : public Deck{
public:
    //N_Deck(size_t& n);
    N_Deck();
    Card GetCard() override;  
    void Shuffle(); 
    ~N_Deck() = default;           
private:
    std::vector<Card> deck;
};