#include "console_interface.h"

#include <iostream>

#include "creator.h"
#include "factory.h"
#include "user_interface.h"


void Console_Interface::ShowCardScore(const int& card_score_) const{
    std::cout << "Cost of hitted card:"<< " "<< card_score_ << std::endl;
}
void Console_Interface::ShowScore(const int& score_) const{
    std::cout << "Score:" <<" "<<score_ << std::endl;
}

void Console_Interface::ShowMethod(const bool& stand_mode_) const {
    std::cout << "Current action: ";
    stand_mode_ ? std::cout<< "stand" : std::cout<< "hit";
    std::cout << std::endl;
    
}

void Console_Interface::ShowWiner(const Player& player) const {
    std::cout << player << std::endl;
}

void Console_Interface::ShowRound(const size_t& round) const {
    std::cout << " " << std::endl;
    std::cout << "Round: "<< round << std::endl;
}

void Console_Interface::ShowWiner(const size_t& player_number) const {
    std::cout << "Player" <<" " << player_number << " " << "is winner!";
}

namespace{
    Creator<Console_Interface, User_Interface, std::string> c("console");
}

