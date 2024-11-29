#include <iostream>
#include <memory>

#include "hand.h"
#include "strategy.h"

class Player {
public:
    Player(std::unique_ptr<Strategy> & str_,const size_t &number_);
    std::unique_ptr <Strategy> strategy;
    friend std::ostream& operator<<(std::ostream& os, const Player& player);
    Hand GetHand(){return hand;}
    size_t GetNumber(){return number;}
private:
    size_t number;
    Hand hand;
};