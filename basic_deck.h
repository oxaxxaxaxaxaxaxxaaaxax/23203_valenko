#pragma once
#include "card.h"
#include "deck.h"
#include <cstdlib>
#include <vector>


class Basic_Deck : public Deck{
public:
    Basic_Deck();
    Card GetCard() override;  
    void GetCardBack(std::vector<Card>& cards) override;
    void Shuffle(); 
    void ShowDeck() override;
    ~Basic_Deck() = default;           
    //динамический массив карт
private:
    std::vector<Card> deck;
};