#pragma once
#include <map>
#include <memory>
#include <string>
#include "strategy.h"


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
        auto creator = creators_.at(name);
        auto* u = creator();  //creator() не найдено
        std::unique_ptr<T> u_ptr{u};
        return std::move(u_ptr);
    }

private:
    //std::map <Key, T * (*)()> creators_; 
    std::map <Key, ProductCreator> creators_;
};