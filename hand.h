#include "card.h"
#include <vector>

class Hand{
public:
    Hand();
    void ShowHand() const;
    void HitCard();
    int GetTotalSum() const;
    void AddToSum(int value);
    void UpdateSum();
    ~Hand() = default;
private:
    std::vector<Card> hand;
    int total_summ=0;
};