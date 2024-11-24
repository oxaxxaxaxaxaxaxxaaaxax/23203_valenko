#pragma once
#include "card.h"
#include <vector>



class Deck{
public:
    //virtual void DeckInit() = 0;
    //virtual int GetCard()=0;
    virtual Card GetCard()=0;
    virtual ~Deck() = default;
    //динамический массив карт
    //наследуем три дека отсюда
};