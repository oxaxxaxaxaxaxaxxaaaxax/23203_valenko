#pragma once
#include "interface.h"
#include <iostream>

using string = std::string;

class Console_Interface: public Interface{
public:
    void ShowResult(string result) override{
        std::cout << result <<std::endl;
    }
};

