#pragma once
#include "player.h"
#include "user_interface.h"
#include <iostream>
#include <string_view>


class Console_Interface: public User_Interface{
public:
   // void ShowResult(std::string_view result) override;  ///???????????????????????? const &  / std::string_view
    void ShowWiner(Player& player) override;
    //std::ostream& operator<<(std::ostream& os,const Player& player);
};

