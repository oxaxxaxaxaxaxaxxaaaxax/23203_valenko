#include "card.h"
#include "creator.h"
#include "simple_deck.h"
#include "factory.h"
#include <algorithm>
#include <cstdlib>
#include <ctime>
#include <random>
#include <vector>


Simple_Deck::Simple_Deck(){
    std::random_device rd;
    std::default_random_engine rng(rd());
    std::uniform_int_distribution<> distrib(2,11);
    for(int i=0;i<1000;i++){
        deck.emplace_back(distrib(rng));
    }
}

Card & Simple_Deck::GetCard(){
    Card tmp = deck.back();
    deck.pop_back();
    return tmp;
}


namespace{
    Creator<Simple_Deck, Deck, std::string> c("simple_deck");
}

// Deck* CreateSimDeck(){
//     return new Simple_Deck;
// }

// namespace{
//     bool b = (Factory<string, Deck, Deck* (*)()>::GetInstance())->Register("Simple_Deck", &CreateSimDeck);
// }