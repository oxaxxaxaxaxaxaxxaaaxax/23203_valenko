#pragma once
#include <any>
#include <functional>
#include <map>
#include <memory>
#include <string>
//#include "strategy.h"


template<class Key,class T>

class Factory{
public:
    static Factory * GetInstance(){
        static Factory f;
        return &f; 
    }
    //void RegisterStrategy(Key &name, T * (creator)()){
    template<class... Types>
    bool Register(const Key &name, std::any creator){
        creators_[name] = creator;
        return true;
    }
    template<class... Types>
    std::unique_ptr<T> CreateByName(const Key &name, Types...args){
        auto creator = std::any_cast<std::function<T*(Types ...)>>(creators_.at(name));
        auto* u = creator(args...);  //creator() не найдено
        std::unique_ptr<T> u_ptr{u};
        return std::move(u_ptr);
    }
    //std::function<int(int, int)> 

private:
    //std::map <Key, T * (*)()> creators_; 
    std::map <Key, std::any> creators_;
};