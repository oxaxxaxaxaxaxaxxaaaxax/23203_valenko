#pragma once
#include <iostream>
#include <string_view>

#include "player.h"



class User_Interface{
public:
    //virtual void ShowResult(std::string_view result)=0;
    virtual void ShowWiner(Player& player)=0;
    virtual void ShowMethod(bool stand_mode_)=0;
    virtual void ShowCardScore(int card_score_) =0;
    virtual void ShowScore(int score_) =0;
    virtual ~User_Interface() = default;
};