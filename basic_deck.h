#pragma once
#include "card.h"
#include "deck.h"
#include <cstdlib>
#include <vector>
#include <cstdlib>


class Basic_Deck : public Deck{
public:
    void DeckInit() override;
    Card GetCard() override;  
    void Shuffle();             
    //динамический массив карт
private:
    std::vector<Card> deck;
};