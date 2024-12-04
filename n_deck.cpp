#include "n_deck.h"

#include <assert.h>
#include "card.h"
#include "creator.h"
#include "factory.h"
#include <algorithm>
#include <cstdlib>
#include <random>
#include <vector>

// N_Deck::N_Deck(size_t& n){
//     for(int i =0;i<n; i++){
//         for(const auto& suit: Card::suits){
//             for(const auto& rank: Card::ranks){
//                 deck.emplace_back(suit, rank, Card::RankValue.at(rank));
//             }
//         }    
//     }
//}

N_Deck::N_Deck(){
    for(int i =0;i<4; i++){
        for(const auto& suit: Card::suits){
            for(const auto& rank: Card::ranks){
                deck.emplace_back(suit, rank, Card::RankValue.at(rank));
            }
        }    
    }
    Shuffle();
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

// namespace{
//     Creator<N_Deck> c;
// }

namespace{
    Creator<N_Deck, Deck, std::string> c("n_deck");
}//&777как сконструкторить n