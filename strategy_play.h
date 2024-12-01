#include "strategy.h"
#include "user_interface.h"

#include <memory>

class Strategy_Play : public Strategy{
public:
    //void ShowMethod(std::unique_ptr<User_Interface>, std::unique_ptr<Strategy>) override;
    virtual bool GetStandMode() override{
        return stand_mode;
    }
protected:
    bool stand_mode = false;
};