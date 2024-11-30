#pragma once
#include "card.h"
#include "hand.h"
//#include "player.h"

class Strategy {
public: 
    virtual bool hit(Card & card, Player & player)=0;
    //virtual void stand()=0;
    //virtual int GetSumm()=0;
    //Hand hand;
    virtual ~Strategy() = default;
};

//