#pragma once

#include "card.h"

class Deck{
public:
    virtual Card & GetCard()=0;  
    virtual ~Deck() = default;
};