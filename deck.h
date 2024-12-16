#pragma once

#include <vector>

#include "card.h"
#include "factory.h"


class Deck{
public:
    virtual Card GetCard()=0;
    virtual void GetCardBack(const std::vector<Card>& cards) =0;
    virtual void ShowDeck() const =0;
    virtual ~Deck() = default;
};