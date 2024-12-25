#pragma once
#include <any>
#include <vector>
#include "player.h"
#include "strategy.h"


class Engine{
public:
    virtual void BlackJack(std::vector<std::unique_ptr<Strategy>>& strategy_)=0;
    virtual ~Engine()=default;
};