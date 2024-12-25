#pragma once
#include "engine_play.h"
#include <vector>


class Engine_3 : public Engine_Play{
public:
    Engine_3(std::string CurDeck,int deck_data,std::string CurInter):Engine_Play(CurDeck,deck_data,CurInter){}
    Engine_3(const Engine_3& b) = delete;
    void BlackJack(std::vector<std::unique_ptr<Strategy>>& strategy_) override;
    void Game(const std::unique_ptr<Player>& player_1,const std::unique_ptr<Player>& player_2) override;
    const std::unique_ptr<Player>& ChooseWinner(const std::unique_ptr<Player>& pl_1,const std::unique_ptr<Player>& pl_2) override;
    void EndGame(const std::unique_ptr<Player>& pl_1,const std::unique_ptr<Player>& pl_2) override;
    bool IsQuit(std::istream& is);

private:
    std::string quit_mode;
}; 

