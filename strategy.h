#pragma once
#include "card.h"
#include "player.h"

class Player;

class Strategy {
public: 
    virtual bool hit(Card card, Player & player, Card& opponent_card)=0;
    virtual bool GetStandMode()=0;
    virtual void End()=0;
    virtual ~Strategy() = default;
};
