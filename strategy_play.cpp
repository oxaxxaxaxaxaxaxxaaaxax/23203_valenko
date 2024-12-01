#include "strategy_play.h"

#include <iostream>

std::ostream& operator<<(std::ostream& os, std::unique_ptr<Strategy> str){
    if(str->stand_mode){
        os << "stand" ;
    }
    os << "hit";
}