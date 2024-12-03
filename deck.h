#pragma once

#include "card.h"
#include "factory.h"

class Deck{
public:
    virtual Card GetCard()=0;  
    virtual ~Deck() = default;
};