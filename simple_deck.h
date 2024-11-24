#pragma once
#include "card.h"
#include "deck.h"
#include <cstdlib>
#include <vector>


class Simple_Deck : public Deck{
public:
    void DeckInit() override;
    Card GetCard() override;
    //int GetVal();
    ~Simple_Deck() override;
private:
    std::vector<Card> deck;
};