#pragma once
#include "card.h"
  
class Hand_Interface{
public:
    virtual void Show() const = 0;
    virtual void HitCard(Card card)= 0;
    virtual int GetTotalSum() const = 0;
    virtual void AddToSum(const int& value)= 0;
    virtual void CheckBust()= 0;
    virtual void CheckVictory()= 0;
    virtual void UpdateSum()= 0;
    virtual Card& ShowLastCard()= 0;
    virtual size_t GetFirstCard() const= 0;
    virtual std::vector<Card>& ReturnCards()= 0;
    virtual void FreeHand()= 0;
    virtual bool GetBustMode()const = 0;
    virtual bool GetVicMode() const = 0;
    virtual ~Hand_Interface() = default;
};