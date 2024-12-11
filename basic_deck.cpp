#include "basic_deck.h"

#include <algorithm>
#include <assert.h>
#include <cstdlib>
#include <iostream>
#include <random>
#include <vector>

#include "card.h"
#include "creator.h"
#include "factory.h"



Basic_Deck::Basic_Deck(std::vector<int>){
    for(const auto& suit : Card::suits){
        for(const auto& rank : Card::ranks){
            deck.emplace_back(suit, rank, Card::RankValue.at(rank));
        }
    }
    Shuffle();
}

void Basic_Deck::ShowDeck(){
    std::cout<< " " << std::endl;
    std::cout<< "deck card" << std::endl;
    int count =0;
    for(const auto& card : deck){
        card.Show();
        count++;
    }
    std::cout<< count << std::endl;
}

Card Basic_Deck::GetCard(){
    assert(!deck.empty());
    Card tmp = deck.back();
    deck.pop_back();
    return tmp;
}

void Basic_Deck::GetCardBack(std::vector<Card>& cards){
    for(auto& card : cards){
        deck.push_back(card);
    }
    Shuffle();
}

void Basic_Deck::Shuffle(){
    std::random_device rd;
    std::default_random_engine rng(rd());
    std::shuffle(deck.begin(), deck.end(), rng);
}



namespace{
    Creator<Basic_Deck, Deck, std::string, std::vector<int>> c("basic_deck");
}

// Deck* CreateBasDeck(std::vector<std::any> data){
//     return new Basic_Deck(data);
// }

// namespace{
//     bool b = (Factory<std::string, Deck, Deck* (*)(std::vector<std::any>)>::GetInstance())->Register("basic_deck", &CreateBasDeck);
// }