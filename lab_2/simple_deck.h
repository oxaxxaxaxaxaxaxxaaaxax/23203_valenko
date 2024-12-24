#pragma once
#include <cstdlib>
#include <vector>
#include "card.h"
#include "deck.h"

class Simple_Deck : public Deck{
public:
    Simple_Deck(const int& n);
    Card GetCard() override;
    void GetCardBack(const std::vector<Card>& cards) override;
    void ShowDeck() const override;
    void Shuffle();
    ~Simple_Deck() = default;
private:
    std::vector<Card> deck;
};