#pragma once
#include "card.h"

class Strategy{
public: 
    virtual void hit(Card & card)=0;
    //virtual void stand()=0;
    virtual int GetSumm()=0;
};

//