#pragma once
#include "card.h"
#include "player.h"

class Player;

class Strategy {
public: 
    virtual bool hit(Card card, Player & player)=0;
    //virtual void ShowMethod(std::unique_ptr<User_Interface>, std::unique_ptr<Strategy>) =0;
    virtual bool GetStandMode()=0;
    //virtual void stand()=0;
    //virtual int GetSumm()=0;
    //Hand hand;
    virtual ~Strategy() = default;
};

//