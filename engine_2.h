#pragma once
#include "engine.h"
#include "player.h"
#include <map>
#include <vector>


class Engine_2 : public Engine{
public:
    void BlackJack(std::vector<std::unique_ptr<Strategy>>& strategy_, std::string& CurDeck,std::string& CurInter) override;
    void Game(std::shared_ptr<Player> player_1, std::shared_ptr<Player> player_2, std::string& CurDeck, std::string& CurInter) override;
    std::shared_ptr<Player> ChooseWinner(std::shared_ptr<Player> pl_1, std::shared_ptr<Player> pl_2) override;
    size_t ChooseTournamentWinner();
private:
    std::map<std::shared_ptr<Player>, size_t> tournament_table;
    std::vector<std::shared_ptr<Player>> players_;
}; 