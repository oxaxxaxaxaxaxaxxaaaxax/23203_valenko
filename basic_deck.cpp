#include "basic_deck.h"

#include <assert.h>
#include "card.h"
#include "creator.h"
#include "factory.h"
#include <algorithm>
#include <cstdlib>
#include <random>
#include <vector>


Basic_Deck::Basic_Deck(){
    for(const auto& suit : Card::suits){
        for(const auto& rank : Card::ranks){
            deck.emplace_back(suit, rank, Card::RankValue.at(rank));
        }
    }
    Shuffle();
}

Card Basic_Deck::GetCard(){
    assert(!deck.empty());
    Card tmp = deck.back();
    deck.pop_back();
    return tmp;
}

void Basic_Deck::Shuffle(){
    std::random_device rd;
    std::default_random_engine rng(rd());
    std::shuffle(deck.begin(), deck.end(), rng);
}



namespace{
    Creator<Basic_Deck, Deck, std::string> c("basic_deck");
}

// Deck* CreateBasDeck(){
//     return new Basic_Deck;
// }

// namespace{
//     bool b = (Factory<string, Deck, Deck* (*)()>::GetInstance())->Register("Basic_Deck", &CreateBasDeck);
// }