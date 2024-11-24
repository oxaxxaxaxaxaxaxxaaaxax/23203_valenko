#pragma once
#include "interface.h"
#include <iostream>

using string = std::string;

class Console_Interface: public Interface{
public:
    void print(string output) override{
        std::cout << output <<std::endl;
    }
};