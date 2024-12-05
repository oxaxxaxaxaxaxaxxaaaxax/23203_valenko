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
    std::cout << "Cost of hitted card:"<< " "<< card_score_ << std::endl;
}
void Console_Interface::ShowScore(int score_){
    std::cout << "Score:" <<" "<<score_ << std::endl;
}

void Console_Interface::ShowMethod(bool stand_mode_){

    //std::cout <<  std::boolalpha << stand_mode_ << std::endl;
    //std::cout <<  std::boolalpha << (stand_mode_ ? true : false) << std::endl;
    stand_mode_ ? std::cout<< "stand" : std::cout<< "hit";
    std::cout << std::endl;
    
}

void Console_Interface::ShowWiner(Player& player){
    std::cout << player << std::endl;
}

void Console_Interface::ShowWiner(size_t player_number){
    std::cout << "Player" <<" " << player_number << " " << "is winner!";
}

namespace{
    Creator<Console_Interface, User_Interface, std::string> c("console");
}

// namespace{
//     bool b = (Factory<string, User_Interface, User_Interface * (*)()>::GetInstance())->Register("ConsoleInterface", &CreateConsInt);
// }
