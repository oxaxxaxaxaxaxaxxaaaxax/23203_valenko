#include "strategy.h"

class Engine{
public:
    virtual void Game(string Strat_1, string Strat_2, string CurDeck,string CurInter)=0;
};