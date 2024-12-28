#include <cstddef>
#include <vector>

namespace{
    constexpr size_t field_size = 10000;
}

enum class State{
    dead =0,
    alive =1,
};

class Cell{
public:
    Cell() = default;
    
    State GetState(){
        return state;
    }
    void SetAlive(){
        state = State::alive;
    }
    void UpdateCellState(int neigbors){
        if(state == State::alive){
            if(neigbors<2 || neigbors>3){
                state = State::dead;
            }
        }
        else{
            if(neigbors == 3){
                state = State::alive;
            }
        }
    }
private:
    State state =State::dead;
};

class Field{
public:
    Field():field(field_size*field_size, Cell()){}
    int GetCountNeighbors(int x, int y);
    void UpdateFieldState();
    void SetInitialState();
    void PrintField();

private:
    std::vector<Cell> field;
    std::vector<Cell> next_field;
};

class Game_Of_Life {
public:
    Game_Of_Life() = default;
    void RunGameOfLive(){
        universe.SetInitialState();
        while(true){
            universe.PrintField();
            universe.UpdateFieldState();
        }
    }
private:
    Field universe;
    // Game_Of_Life(const Game_Of_Life& a)= delete;
    // Game_Of_Life& operator=(Game_Of_Life& a) = delete;
    // Game_Of_Life(const Game_Of_Life&& a) = delete;
    // Game_Of_Life& operator=(Game_Of_Life&& a) = delete;
};

