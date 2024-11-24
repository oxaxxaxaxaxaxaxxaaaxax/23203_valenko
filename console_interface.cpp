#include "console_interface.h"
#include "factory.h"
#include "interface.h"

Interface* CreateConsInt(){
    return new Console_Interface;
}

namespace{
    bool b = (Factory<string, Interface, Interface * (*)()>::GetInstance())->Register("ConsoleInterface", &CreateConsInt);
}
