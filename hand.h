#pragma once
//#include "hand_interface.h"
#include <vector>
#include "card.h"


class Hand{
public:
    void Show() const;
    void HitCard(Card card);
    int GetTotalSum() const;
    void AddToSum(const int& value);
    void CheckBust();
    void CheckVictory();
    void UpdateSum();
    Card& ShowLastCard(){
        return hand_.back();
    }
    size_t GetFirstCard() const{
       return hand_.front().GetValue();
    }
    std::vector<Card>& ReturnCards(){return hand_;}
    void FreeHand();
    bool GetBustMode()const {
        return bust_mode;
    }
    bool GetVicMode() const {
        return victory_mode;
    }
    ~Hand() = default;
private:
    std::vector<Card> hand_;
    int total_summ = 0;
    bool bust_mode = false;
    bool victory_mode = false;
};