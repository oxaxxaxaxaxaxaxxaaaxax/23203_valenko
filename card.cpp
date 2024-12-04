#include "card.h"
#include <iostream>

void Card::Show() const {
    std::cout<< rank << " " << suit << std::endl;
}

