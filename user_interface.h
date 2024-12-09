#pragma once
#include "player.h"

class User_Interface{
public:
    virtual void ShowWiner(Player& player)=0;
    virtual void ShowWiner(size_t player_number)=0;
    virtual void ShowMethod(bool stand_mode_)=0;
    virtual void ShowCardScore(int card_score_) =0;
    virtual void ShowRound(size_t round) =0;
    virtual void ShowScore(int score_) =0;
    virtual ~User_Interface() = default;
};