#pragma once
#include "card.h"
#include "deck.h"
#include <cstdlib>
#include <vector>


class N_Deck : public Deck{
public:
    N_Deck(size_t& n);
    Card & GetCard() override;  
    void Shuffle();            
private:
    std::vector<Card> deck;
};