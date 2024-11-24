#pragma once
#include "basic_deck.h"
#include "card.h"
#include "factory.h"
#include <algorithm>
#include <cstdlib>
#include <random>
#include <vector>


Basic_Deck::Basic_Deck(){
    for(const auto& suit : Card::suits){
        for(const auto& rank : Card::ranks){
            Card card(suit, rank, Card::RankValue.at(rank));
            deck.push_back(card);
        }
    }
}

Card Basic_Deck::GetCard(){
    Card tmp = deck.back();
    deck.pop_back();
    return tmp;
}

void Basic_Deck::Shuffle(){
    std::random_device rd;
    std::default_random_engine rng(rd());
    std::shuffle(deck.begin(), deck.end(), rng);
}

Deck* CreateBasDeck(){
    return new Basic_Deck;
}

namespace{
    bool b = (Factory<string, Deck, Deck* (*)()>::GetInstance())->Register("Basic_Deck", &CreateBasDeck);
}