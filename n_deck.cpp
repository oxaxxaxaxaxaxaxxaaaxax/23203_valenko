#include "n_deck.h"
#include <algorithm>
#include <assert.h>
#include <cstdlib>
#include <iostream>
#include <random>
#include <vector>
#include "card.h"
#include "creator.h"
#include "factory.h"


N_Deck::N_Deck(std::vector<int> data){
    int n = data.back();
    //int n = std::any_cast<int>(data.back());
    //auto n = data.back();
    for(int i =0;i < n; i++){
        for(const auto& suit: Card::suits){
            for(const auto& rank: Card::ranks){
                deck.emplace_back(suit, rank, Card::RankValue.at(rank));
            }
        }    
    }
    Shuffle();
}

void N_Deck::ShowDeck(){
    std::cout<< " " << std::endl;
    std::cout<< "deck card" << std::endl;
    for(const auto& card : deck){
        card.Show();
    }
}

Card N_Deck::GetCard(){
    assert(!deck.empty());
    Card tmp = deck.back();
    deck.pop_back();
    return tmp;
}

void N_Deck::GetCardBack(std::vector<Card>& cards){
    for(auto& card : cards){
        deck.push_back(card);
    }
    Shuffle();
}

void N_Deck::Shuffle(){
    std::random_device rd;
    std::default_random_engine rng(rd());
    std::shuffle(deck.begin(), deck.end(), rng);
}


namespace{
    Creator<N_Deck, Deck, std::string, std::vector<int>> c("n_deck");
}//&777как сконструкторить n
//СПРОСИТЬ КАК ЭТО ПОДАВАТЬ

