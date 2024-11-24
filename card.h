#pragma once
#include <map>
#include <string>
#include <vector>

using string = std::string;

namespace{
    constexpr size_t ace_bust = 10;
}

class Card{
public:
    Card(string suit_, string rank_, size_t value_):suit(suit_),rank(rank_),value(value_){}
    Card(size_t value_):value(value_), suit("Hearts"){
        rank = ranks[value_ - 2];
    }
    size_t GetValue()const{return value;}
    void ChangeAceScore(Card card){card.value -= ace_bust;}
    void Show() const;
    inline static std::vector<string> suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
    inline static std::vector<string> ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    static void FillRankValue();
    static std::map<string, size_t> RankValue;
private:
    string suit;
    string rank;
    size_t value;
};