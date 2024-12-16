#include "player.h"


Player::Player(std::unique_ptr<Strategy> str_,const size_t& number_):number(number_),strategy(std::move(str_)){}

std::ostream& operator<<(std::ostream& os, const Player& player){
    os<< "Player"<<" " << player.number << " " << "is winner!";
    return os;
}

bool Player::PlayerHit(Card card){
    hand.HitCard(card);
    return strategy->hit(card, hand.GetTotalSum());
}

void Player::ShowHand() const{
    hand.Show();
}