#include "simple_deck.h"

#include <algorithm>
#include <cstdlib>
#include <ctime>
#include <iostream>
#include <random>
#include <vector>

#include "card.h"
#include "creator.h"
#include "factory.h"

Simple_Deck::Simple_Deck(std::vector<int>){
    std::random_device rd;
    std::default_random_engine rng(rd());
    std::uniform_int_distribution<> distrib(2,11);
    for(int i=0;i<1000;i++){
        deck.emplace_back(distrib(rng));
    }
}

void Simple_Deck::ShowDeck(){
    std::cout<< " " << std::endl;
    std::cout<< "deck card" << std::endl;
    for(const auto& card : deck){
        card.Show();
    }
}

Card Simple_Deck::GetCard(){
    Card tmp = deck.back();
    deck.pop_back();
    return tmp;
}

void Simple_Deck::GetCardBack(std::vector<Card>& cards){
    for(auto& card : cards){
        deck.push_back(card);
    }
    Shuffle();
}

void Simple_Deck::Shuffle(){
    std::random_device rd;
    std::default_random_engine rng(rd());
    std::shuffle(deck.begin(), deck.end(), rng);
}

namespace{
    Creator<Simple_Deck, Deck, std::string, std::vector<int>> c("simple_deck");
}

