#pragma once
#include <map>
#include <random>
#include <string>
#include <vector>



class Card{ 
public:
    Card(std::string suit_, std::string rank_, size_t value_):suit(suit_),rank(rank_),value(value_){}
    Card(size_t value_):value(value_){
        std::random_device rd;
        std::default_random_engine rng(rd());
        std::uniform_int_distribution<> distrib (0,3);
        suit = suits[distrib(rng)];
        rank = ranks[value_ - 2];
    }
    size_t GetValue()const{
        return value;
    }
    void Show() const;
    inline static const std::vector<std::string> suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
    inline static const std::vector<std::string> ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    bool friend operator>=(Card& card_1, Card& card_2){
        return card_1.value >= card_2.value;
    }
    bool friend operator<=(Card& card_1, Card& card_2){
        return !(card_1>=card_2);
    }
    inline static const std::map<std::string, size_t> RankValue ={
        {"2", 2},
        {"3", 3},
        {"4", 4},
        {"5", 5},
        {"6", 6},
        {"7", 7},
        {"8", 8},
        {"9", 9},
        {"10", 10},
        {"Jack", 10},
        {"Queen", 10},
        {"King", 10},
        {"Ace", 11}
    };
    ~Card() = default;

private:
    std::string suit;
    std::string rank;
    size_t value;
};