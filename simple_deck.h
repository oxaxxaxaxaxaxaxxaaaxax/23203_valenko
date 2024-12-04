#pragma once
#include "card.h"
#include "deck.h"
#include <cstdlib>
#include <vector>


class Simple_Deck : public Deck{
public:
    Simple_Deck();
    Card GetCard() override;
    void GetCardBack(std::vector<Card>& cards) override;
    void Shuffle();
    //int GetVal();
    ~Simple_Deck() = default;
private:
    std::vector<Card> deck;
};