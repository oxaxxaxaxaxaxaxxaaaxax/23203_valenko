#pragma once
#include <iostream>
#include "player.h"
#include "user_interface.h"



class Console_Interface: public User_Interface{
public:
    Console_Interface(){}
    void ShowWiner(const Player& player) const override;
    void ShowWiner(const size_t& player_number) const override;
    void ShowMethod(const bool& stand_mode_) const override;
    void ShowCardScore(const int& card_score_) const override;
    void ShowScore(const int& score_) const override;
    void ShowRound(const size_t& round) const override;
    friend std::ostream& operator<<(std::ostream& os,bool stand_mode_){
        if (stand_mode_) {
            os << "stand";
        } else {
            os << "hit";
        }
        return os;
    }
    ~Console_Interface()= default;
};

