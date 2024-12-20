#pragma once
#include "card.h"
#include "hand.h"

class Player_Interface{
    virtual bool PlayerHit(Card card)=0;
    virtual size_t GetNumber() const=0;
    virtual Hand& GetHand()=0;
    virtual void ShowHand() const=0;
    virtual ~Player_Interface() = default;
};