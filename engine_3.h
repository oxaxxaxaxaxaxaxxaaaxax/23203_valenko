#pragma once
#include "engine.h"
#include <vector>


class Engine_3 : public Engine{
public:
    void BlackJack(std::vector<std::unique_ptr<Strategy>>& strategy_, std::string& CurDeck,std::string& CurInter) override;
    void Game(std::shared_ptr<Player> player_1, std::shared_ptr<Player> player_2, std::string& CurDeck, std::string& CurInter) override;
    std::shared_ptr<Player> ChooseWinner(std::shared_ptr<Player> pl_1, std::shared_ptr<Player> pl_2) override;
    void EndGame(std::shared_ptr<Player> pl_1, std::shared_ptr<Player> pl_2) override;
    bool IsQuit(std::istream& is);

private:
    std::string quit_mode;
}; 

