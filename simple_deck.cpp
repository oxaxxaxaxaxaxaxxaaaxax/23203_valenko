#include "card.h"
#include "simple_deck.h"
#include "factory.h"
#include <algorithm>
#include <cstdlib>
#include <ctime>
#include <vector>


void Simple_Deck::DeckInit(){
    for(int i=0;i<1000;i++){
        std::srand(std::time(0));
        Card card(std::rand()%9 + 2);
        deck.push_back(card);
    }
}

Card Simple_Deck::GetCard(){
    Card tmp = deck.back();
    deck.pop_back();
    return tmp;
}

Deck* CreateSimDeck(){
    return new Simple_Deck;
}

namespace{
    bool b = (Factory<string, Deck, Deck* (*)()>::GetInstance())->Register("Simple_Deck", &CreateSimDeck);
}