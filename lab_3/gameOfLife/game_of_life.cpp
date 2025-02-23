#include "game_of_life.h"

#include <algorithm>
#include <iostream>
#include <memory>
#include <utility>
#include <QColor>
#include <QDebug>
#include <QGraphicsRectItem>

namespace{
    constexpr size_t field_size = 70;
    constexpr size_t cellSize = 10;
}
Game_Of_Life::Game_Of_Life():universe(std::make_unique<Field>()){}

Field::Field():field(field_size*field_size, Cell()), next_field(field_size*field_size, Cell()){}

int Field::GetCountNeighbors(int x, int y){
    int count=0;
    for(int i=-1;i<=1;i++){
        for(int j=-1;j<=1;j++){
            if(i==0 && j==0){
                continue;
            }
            int neigbourX = (x+i+field_size)%field_size;
            int neigbourY = (y+j+field_size)%field_size;
            if(field[neigbourX*field_size + neigbourY].GetState() == State::alive){
                count++;
            }
        }

    }
    return count;
}

State Field::GetCellState(int x, int y){
    return field[x*field_size+y].GetState();
}

void Field::UpdateCellState(int x,int y){
    int newState = (static_cast<int>(field[x*field_size+y].GetState()) + 1)%2;
    field[x*field_size+y].SetState(static_cast<State>(newState));
    emit ChangeState((x)*cellSize,(y)*cellSize);
}

Cell Cell::CalculateCellState(int neigbors,size_t customVal) const {
    if (state == State::alive) {
        if((neigbors < customVal-1) || (neigbors > customVal)) {
            return Cell(State::dead);
        }
    }
    else {
        if(neigbors == customVal) {
            return Cell(State::alive);
        }
    }
    return Cell(state);
}

void Field::UpdateFieldState(){
    for(int i=0;i<field_size;i++){
        for(int j=0;j<field_size;j++){
            int count_neighbors = GetCountNeighbors(i,j);
            next_field[i*field_size+j] = field[i*field_size+j].CalculateCellState(count_neighbors,customVal);
        }
    }

    std::swap(field, next_field);
    emit ChangeField();
}

