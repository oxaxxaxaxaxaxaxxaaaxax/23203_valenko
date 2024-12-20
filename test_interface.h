#pragma once
#include <gmock/gmock.h>
#include <gtest/gtest.h>
#include <iostream>
#include "hand.h"
#include "player.h"
#include "strategy.h"
#include "user_interface.h"



class Test_Interface: public User_Interface, public Strategy{
public:
    ~Test_Interface() override = default;
    // MOCK_METHOD1(ShowWiner, void(Player&));
    // MOCK_METHOD1(ShowMethod, void(bool&));
    // MOCK_METHOD1(ShowCardScore, void(int&));
    // MOCK_METHOD1(ShowScore, void(int&));
    // MOCK_METHOD1(ShowRound, void(size_t&));

    // MOCK_METHOD2(hit, bool(Card,int));
    // MOCK_METHOD0(GetStandMode, bool());
    MOCK_METHOD0(End, void());
};