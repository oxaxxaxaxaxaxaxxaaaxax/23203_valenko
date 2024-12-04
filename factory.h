#pragma once

#include "strategy.h"
#include <map>
#include <memory>
#include <string>

template<class Key,class T, class ProductCreator>

class Factory{
public:
    static Factory * GetInstance(){
        static Factory f;
        return &f; 
    }
    //void RegisterStrategy(Key &name, T * (creator)()){
    bool Register(const Key &name, ProductCreator creator){
        creators_[name] = creator;
        return true;
    }
    std::unique_ptr<T> CreateByName(const Key &name){
        //std::cout << name <<std::endl;
        auto creator = creators_.at(name);
        //std::cout << name <<std::endl;
        auto* u = creator();  //creator() не найдено
        std::unique_ptr<T> u_ptr{u};
        //std::cout << name <<std::endl;
        return std::move(u_ptr);
    }

private:
    //std::map <Key, T * (*)()> creators_; 
    std::map <Key, ProductCreator> creators_;
};