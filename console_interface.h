#pragma once
#include <iostream>
#include <string_view>
#include "player.h"
#include "user_interface.h"



class Console_Interface: public User_Interface{
public:
    void ShowWiner(Player& player) override;
    void ShowWiner(size_t player_number) override;
    void ShowMethod(bool stand_mode_) override;
    void ShowCardScore(int card_score_) override;
    void ShowScore(int score_) override;
    void ShowRound(size_t round) override;
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

