    #pragma once
#include <cstdlib>
#include <vector>

#include "card.h"
#include "deck.h"



class Basic_Deck : public Deck{
public:
    Basic_Deck(const int& n);
    Basic_Deck(Basic_Deck&& other):deck(std::move(other.deck)){}
    Card GetCard()override;  
    void GetCardBack(const std::vector<Card>& cards) override;
    void Shuffle(); 
    void ShowDeck() const override;
    ~Basic_Deck() = default; 
private:
    std::vector<Card> deck;
};