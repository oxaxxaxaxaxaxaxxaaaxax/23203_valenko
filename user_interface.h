#pragma once
#include "player.h"

class User_Interface{
public:
    virtual void ShowWiner(const Player& player) const =0;
    virtual void ShowWiner(const size_t& player_number) const =0;
    virtual void ShowMethod(const bool& stand_mode_) const =0;
    virtual void ShowCardScore(const int& card_score_) const =0;
    virtual void ShowRound(const size_t& round) const =0;
    virtual void ShowScore(const int& score_) const =0;
    virtual ~User_Interface() = default;
};