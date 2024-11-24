#include "card.h"
#include "deck.h"
#include "engine.h"
#include "factory.h"
#include "hand.h"
#include "interface.h"
#include "strategy.h"
#include <memory>
#include <vector>




using string = std::string;

void Engine::Game(string Strat_1, string Strat_2, string CurDeck, string CurInter){
    std::unique_ptr<Strategy> str_1 = (Factory<string, Strategy, Strategy* (*)()>::GetInstance())->CreateByName(Strat_1);
    std::unique_ptr<Strategy> str_2 = (Factory<string, Strategy, Strategy* (*)()>::GetInstance())->CreateByName(Strat_2);
    std::unique_ptr<Deck> deck = (Factory<string, Deck, Deck* (*)()>::GetInstance())->CreateByName(CurDeck);
    std::unique_ptr<Interface> interface = (Factory<string, Interface, Interface* (*)()>::GetInstance())->CreateByName(CurInter);
    while(1){
        // str_1 ->hit(deck->GetCard());
        // str_2 ->hit(deck->GetCard());
        // if(str_1->GetSum() > 21) && (str_2->GetSum() > 21){
        //     interface->print();
        // }
    }
}