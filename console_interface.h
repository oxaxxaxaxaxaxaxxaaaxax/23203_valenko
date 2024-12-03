#pragma once
#include "player.h"
#include "user_interface.h"
#include <iostream>
#include <string_view>


class Console_Interface: public User_Interface{
public:
    //Console_Interface();
   // void ShowResult(std::string_view result) override;  ///???????????????????????? const &  / std::string_view
    void ShowWiner(Player& player) override;
    void ShowMethod(bool stand_mode_) override;
    void ShowCardScore(int card_score_) override;
    void ShowScore(int score_) override;
    friend std::ostream& operator<<(std::ostream& os,const bool& stand_mode_){
        stand_mode_ ? os << "stand" : os << "hit";
        return os;
    }
    ~Console_Interface()= default;

    //std::ostream& operator<<(std::ostream& os,const Player& player);
};

