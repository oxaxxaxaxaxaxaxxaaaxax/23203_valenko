#pragma once
#include <cstdlib>
#include <vector>
#include "card.h"
#include "deck.h"

class Simple_Deck : public Deck{
public:
    Simple_Deck(std::vector<int>);
    Card GetCard() override;
    void GetCardBack(std::vector<Card>& cards) override;
    void ShowDeck() override;
    void Shuffle();
    ~Simple_Deck() = default;
private:
    std::vector<Card> deck;
};