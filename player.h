#pragma once
#include <iostream>
#include <memory>

#include "hand.h"
#include "strategy.h"

class Strategy;

class Player {
public:
    Player(std::unique_ptr<Strategy> str_ ,const size_t &number_);
    std::unique_ptr <Strategy> strategy;
    friend std::ostream& operator<<(std::ostream& os, const Player& player);
    friend bool operator==(Player& player_1, Player& player_2){
        return player_1.number== player_2.number;
    }
    friend bool operator>=(Player& player_1, Player& player_2){
        return player_1.number>= player_2.number;
    }
    Hand GetHand(){return hand;}
    ~Player();
    //size_t GetNumber(){return number;}
private:
    size_t number;
    Hand hand;
};