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




void User_Interface::ShowWiner(Player& player){
    std::cout << player << std::endl;
}


namespace{
    Creator<Console_Interface> c;
}

// namespace{
//     bool b = (Factory<string, User_Interface, User_Interface * (*)()>::GetInstance())->Register("ConsoleInterface", &CreateConsInt);
// }
