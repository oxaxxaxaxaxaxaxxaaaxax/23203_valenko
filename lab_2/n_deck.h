#pragma once
#include "card.h"
#include "deck.h"
#include <cstdlib>
#include <vector>


class N_Deck : public Deck{
public:
    N_Deck(const int& n);
    Card GetCard() override;  
    void GetCardBack(const std::vector<Card>& cards) override;
    void ShowDeck() const override;
    void Shuffle(); 
    ~N_Deck() = default;           
private:
    std::vector<Card> deck;
};