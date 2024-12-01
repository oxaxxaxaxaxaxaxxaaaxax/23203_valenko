#pragma once
#include "engine.h"
#include <vector>


class Engine_3 : public Engine{
public:
    void BlackJack(std::vector<std::unique_ptr<Strategy>>& strategy_, std::string& CurDeck,std::string& CurInter) override;
    void Game(Player& player_1, Player& player_2, std::string& CurDeck, std::string& CurInter) override;
    Player& ChooseWinner(Player& pl_1, Player& pl_2);
    friend std::ostream& operator<<(std::ostream& os, bool stand_mode_){
        stand_mode_ ? os << "stand" : os << "hit";
        return os;
    }
    bool IsQuit(std::istream& is);

private:
    std::string quit_mode;
}; 