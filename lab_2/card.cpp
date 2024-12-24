#include "card.h"
#include "user_interface.h"
#include <iostream>

void Card::Show() const {
    std::cout<< rank << " " << suit << std::endl;
}

