#pragma once
#include "card.h"
#include <vector>

class Hand{
public:
    //Hand();
    void ShowHand() const;
    void HitCard(Card card);
    int GetTotalSum() const;
    void AddToSum(int value);
    void CheckBust();
    void CheckVictory();
    void UpdateSum();
    Card& ShowLastCard(){
        return hand_.back();
    }
    std::vector<Card>& ReturnCards(){return hand_;}
    void FreeHand();
    //std::vector<Card>& GetHand(){return hand_;}
    bool GetBustMode(){return bust_mode;}
    bool GetVicMode(){return victory_mode;}
    ~Hand() = default;
private:
    std::vector<Card> hand_;
    int total_summ = 0;
    bool bust_mode = false;
    bool victory_mode = false;
};