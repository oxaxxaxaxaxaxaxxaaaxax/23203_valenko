#include "player.h"


Player::Player(std::unique_ptr<Strategy> str_,const size_t& number_):number(number_){
    strategy(std::move(str_));
}

std::ostream& operator<<(std::ostream& os, const Player& player){
    os<< "Player" << player.number << "is winner!";
}