#include <memory>
#include "creator.h"
#include "strategy_play.h"

class Strategy_Config : public Strategy_Play{
public:
    Strategy_Config(std::unique_ptr <Strategy> str_, size_t hit_limit_, size_t special_first_card_):strategy(std::move(str_)), hit_limit(hit_limit_), 
        special_first_card(special_first_card_){}
    bool hit(Card card, Player & player) override{
        strategy->hit(card, player);
    }
private:
    size_t hit_limit;
    size_t special_first_card;
    size_t opponent_first_min_card_for2st;
    size_t opponent_first_max_card_for2st;
    size_t opponent_first_min_card_for3st;
    size_t mode_2st = false;
    size_t mode_3st = false;
    std::unique_ptr<Strategy> strategy;
};

namespace {
static Creator<Strategy_Config, Strategy, std::string> c("strategy_config");
}