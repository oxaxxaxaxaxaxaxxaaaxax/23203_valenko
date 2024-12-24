#pragma once
#include <memory>
#include "strategy.h"
#include "user_interface.h"


class Strategy_Play : public Strategy{
public:
    bool GetStandMode() override{
        return stand_mode;
    }
    void End() override;
    virtual ~Strategy_Play()=default;
protected:
    bool stand_mode = false;
};