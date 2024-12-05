#pragma once

#include "strategy.h"
#include "user_interface.h"

#include <memory>

class Strategy_Play : public Strategy{
public:
    //void ShowMethod(std::unique_ptr<User_Interface>, std::unique_ptr<Strategy>) override;
    bool GetStandMode() override{
        return stand_mode;
    }
    void End() override;
protected:
    bool stand_mode = false;
};