#include <memory>
#include "creator.h"
#include "strategy_play.h"

class Strategy_Config : public Strategy_Play{
public:
    Strategy_Config(std::vector<int> strategy_data);
    bool hit(Card card, int total_summ) override;

private:
    int hit_limit;
    int last_card;
    int hit_count_lim;
    int min_opp_card;
    int max_opp_card;
    int hit_count=0;
};

