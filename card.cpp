#include "card.h"
#include "user_interface.h"
#include <iostream>

void Card::Show() const {//по хорошему сделать что бы интерфейс это печатал
    //std::cout<< "start BlackJack" << std::endl;
    std::cout<< rank << " " << suit << std::endl;
}

