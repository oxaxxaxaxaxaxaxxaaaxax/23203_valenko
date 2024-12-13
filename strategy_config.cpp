#include "strategy_config.h"


namespace{
    constexpr int default_limit = 17;
    constexpr int default_last = 10;
    constexpr int default_hit_count = 4;
    constexpr int default_min = 3;
    constexpr int default_max = 10;
}

Strategy_Config::Strategy_Config(std::vector<int> data){
        !data.at(0) ? hit_limit = default_limit : hit_limit = data.at(0);
        !data.at(1) ? last_card=default_last : last_card=data.at(1);
        !data.at(2) ? hit_count_lim=default_hit_count : hit_count_lim = data.at(2);
        !data.at(3) ? min_opp_card =default_min : min_opp_card = data.at(3);
        !data.at(4) ? max_opp_card =default_max : max_opp_card = data.at(4);
    }

bool Strategy_Config::hit(Card card, Player & player, Card& opponent_card) {
        if (!stand_mode){
            hit_count++;
            if(opponent_card.GetValue() >= max_opp_card){
                hit_count++;
            }
            if(opponent_card.GetValue() <= min_opp_card){
                hit_count--;
            }
            player.GetHand().HitCard(card);
            if(card.GetValue() > last_card){
                hit_count++;
                last_card = card.GetValue();
            }
            if(player.GetHand().GetTotalSum() >= hit_limit){
                stand_mode = true; 
            }
            if(hit_count == hit_count_lim){
                stand_mode = true;
            }
        }
        return stand_mode;
    }

namespace {
static Creator<Strategy_Config, Strategy, std::string, std::vector<int>> c("strategy_config");
}