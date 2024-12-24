#pragma once
//#include "player_interface.h"
#include <iostream>
#include <memory>
#include <vector>

#include "hand.h"
#include "strategy.h"

class Strategy;

class Player{
public:
    Player(std::unique_ptr<Strategy> str_ ,const size_t &number_);
    Player(const Player &player) = delete;
    Player& operator=(const Player &other) = delete;
    Player(Player &&player) = default;
    Player& operator=(Player &&player) = default;
    std::unique_ptr <Strategy> strategy;
    friend std::ostream& operator<<(std::ostream& os, const Player& player);
    friend bool operator==(Player& player_1, Player& player_2){
        return player_1.number== player_2.number;
    }
    friend bool operator>=(Player& player_1, Player& player_2){
        return player_1.number>= player_2.number;
    }
    bool PlayerHit(Card card);
    size_t GetNumber() const {
        return number;
    }
    Hand& GetHand(){
        return hand;
    }
    void ShowHand() const;
    ~Player() = default;
private:
    size_t number;
    Hand hand;
};