#pragma once

#include <cstddef>
#include <memory>
#include <QObject>
#include <QTimer>
#include <vector>

enum class State{
    dead = 0,
    alive = 1,
};

class Cell{
public:
    Cell() = default;
    Cell(State s) : state(s) {}
    
    State GetState(){
        return state;
    }
    void SetAlive(){
        state = State::alive;
    }
    void SetState(State newState){
        state = newState;
    }
    Cell CalculateCellState(int neigbors) const;
private:
    State state = State::dead;
};

class Field : public QObject {
    Q_OBJECT
public:
    Field();
    int GetCountNeighbors(int x, int y);
    void UpdateCellState(int x,int y);
    void UpdateFieldState();
    void PrintField();
    State GetCellState(int x, int y);

signals:
    void ChangeState(int x, int y);
    void ChangeField();
private:
    std::vector<Cell> field;
    std::vector<Cell> next_field;
};

class Game_Of_Life {
public:
    Game_Of_Life();
    ~Game_Of_Life();
    //Field* universe;
    std::unique_ptr<Field> universe;
};
