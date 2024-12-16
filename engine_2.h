#pragma once
#include <map>
#include <vector>
#include "engine.h"
#include "player.h"



class Engine_2 : public Engine{
public:
    Engine_2(){}
    Engine_2(const Engine_2& b) = delete;
    void BlackJack(std::vector<std::unique_ptr<Strategy>>& strategy_,const std::string& CurDeck,const int& deck_data,const std::string& CurInter) override;
    void Game(std::shared_ptr<Player> player_1, std::shared_ptr<Player> player_2,const std::string& CurDeck,const int& deck_data, const std::string& CurInter) override;
    std::shared_ptr<Player> ChooseWinner(std::shared_ptr<Player> pl_1, std::shared_ptr<Player> pl_2) override;
    size_t ChooseTournamentWinner();
    void EndGame(std::shared_ptr<Player> pl_1, std::shared_ptr<Player> pl_2) override;
private:
    std::map<std::shared_ptr<Player>, size_t> tournament_table;
    std::vector<std::shared_ptr<Player>> players_;
    size_t round = 0;
}; 