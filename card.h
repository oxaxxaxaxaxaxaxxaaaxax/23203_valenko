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
    size_t GetValue()const{return value;}
    //void ChangeAceScore(Card card){card.value -= ace_bust;}
    void Show() const;
    inline static const std::vector<std::string> suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
    inline static const std::vector<std::string> ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    static void FillRankValue();
    inline static std::map<std::string, size_t> RankValue;
    ~Card() = default;

private:
//??????????????????????????????????
// enum enum enum
    std::string suit;
    std::string rank;
    size_t value;
};