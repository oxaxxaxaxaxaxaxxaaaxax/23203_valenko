#include "console_interface.h"

#include <iostream>
#include <string_view>

#include "creator.h"
#include "factory.h"
#include "user_interface.h"

// User_Interface* CreateConsInt(){
//     return new Console_Interface;
// }

// void User_Interface::ShowResult(std::string_view result){
//         std::cout << result <<std::endl;
// }
void Console_Interface::ShowCardScore(int card_score_){
    std::cout << card_score_;
}
void Console_Interface::ShowScore(int score_){
    std::cout << score_;
}

void Console_Interface::ShowMethod(bool stand_mode_){
    std::cout << stand_mode_;
}

void Console_Interface::ShowWiner(Player& player){
    std::cout << player << std::endl;
}


namespace{
    Creator<Console_Interface, User_Interface, std::string> c("console");
}

// namespace{
//     bool b = (Factory<string, User_Interface, User_Interface * (*)()>::GetInstance())->Register("ConsoleInterface", &CreateConsInt);
// }
