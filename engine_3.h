#pragma once
#include "engine.h"
#include <vector>


class Engine_3 : public Engine{
public:
    Engine_3(){}
    Engine_3(const Engine_3& b) = delete;
    void BlackJack(std::vector<std::unique_ptr<Strategy>>& strategy_,const std::string& CurDeck,const int& deck_data,const std::string& CurInter) override;
    void Game(const std::unique_ptr<Player>& player_1,const std::unique_ptr<Player>& player_2,const std::string& CurDeck,const int& deck_data, const std::string& CurInter) override;
    const std::unique_ptr<Player>& ChooseWinner(const std::unique_ptr<Player>& pl_1,const std::unique_ptr<Player>& pl_2) override;
    void EndGame(const std::unique_ptr<Player>& pl_1,const std::unique_ptr<Player>& pl_2) override;
    bool IsQuit(std::istream& is);

private:
    std::string quit_mode;
}; 

