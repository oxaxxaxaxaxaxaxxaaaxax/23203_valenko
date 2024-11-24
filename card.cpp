#include <iostream>
#include "card.h"

void Card::Show(){
    std::cout<< rank << suit << std::endl;
}

void Card::FillRankValue(){
    RankValue["2"] = 2;
    RankValue["3"] = 3;
    RankValue["4"] = 4;
    RankValue["5"] = 5;
    RankValue["6"] = 6;
    RankValue["7"] = 7;
    RankValue["8"] = 8;
    RankValue["9"] = 9;
    RankValue["10"] = 10;
    RankValue["Jack"] = 10;
    RankValue["Queen"] = 10;
    RankValue["King"] = 10;
    RankValue["Ace"] = 11;
}