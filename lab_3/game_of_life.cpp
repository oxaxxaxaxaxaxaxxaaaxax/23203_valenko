#include <iostream>
#include <utility>
#include "game_of_life.h"

int Field::GetCountNeighbors(int x, int y){
    int count=0;
    for(int i=-1;i<=1;i++){
        for(int j=-1;j<=1;j++){
            if(i==0 && j==0){
                continue;
            }
            if(((x+i)*field_size + y+j) < 0 || ((x+i)*field_size + y+j) > field_size){
               continue; 
            }
            if(field[(x+i)*field_size + y+j].GetState() == State::alive){
                count++;
            }
        }
    }
    return count;
}

void Field::UpdateFieldState(){
    for(int i=0;i<field_size;i++){
        for(int j=0;j<field_size;j++){
            int count_neighbors = GetCountNeighbors(i,j);
            next_field[i*field_size+j].UpdateCellState(count_neighbors);
        }
    }
    std::swap(field, next_field);
}

void Field::PrintField(){
    for(int i=0;i<field_size;i++){
        for(int j=0;j<field_size;j++){
            field[i*field_size+j].GetState() == State::alive ? std::cout<< "O": std::cout<< ".";
        }
        std::cout << std::endl;
    }
}

void Field::SetInitialState(){
    field[2*field_size+1].SetAlive();
    field[2*field_size+2].SetAlive();
    field[3*field_size+1].SetAlive();
    field[3*field_size+2].SetAlive();
}