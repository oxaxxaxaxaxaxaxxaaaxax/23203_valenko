#pragma once
#include "card.h"
#include "deck.h"
#include <cstdlib>
#include <vector>


class N_Deck : public Deck{
public:
    //N_Deck(std::vector<int> data);
    N_Deck(int deck_count);
    Card GetCard() override;  
    void GetCardBack(std::vector<Card>& cards) override;
    void ShowDeck() override;
    void Shuffle(); 
    ~N_Deck() = default;           
private:
    std::vector<Card> deck;
};