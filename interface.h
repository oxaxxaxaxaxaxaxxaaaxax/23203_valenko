#pragma once
#include <iostream>

using string = std::string;

class Interface{
    virtual void print(string output)=0;
};