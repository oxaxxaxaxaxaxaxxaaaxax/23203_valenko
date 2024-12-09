#pragma once
#include <cstdlib>
#include <vector>

#include "card.h"
#include "deck.h"



class Basic_Deck : public Deck{
public:
    Basic_Deck();
    Basic_Deck(Basic_Deck&& other):deck(std::move(other.deck)){}
    Card GetCard() override;  
    void GetCardBack(std::vector<Card>& cards) override;
    std::vector <Card>& GetDeck(){
        return deck;
    }
    void Shuffle(); 
    void ShowDeck() override;
    ~Basic_Deck() = default;           
    //динамический массив карт
private:
    std::vector<Card> deck;
};