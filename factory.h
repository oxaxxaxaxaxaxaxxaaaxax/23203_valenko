#pragma once
#include <any>
#include <assert.h>
#include <functional>
#include <map>
#include <memory>
#include <string>


template<class Key,class T>

class Factory{
public:
    Factory(const Factory &b) = delete;
    Factory& operator=(Factory &b) = delete;

    static Factory * GetInstance(){
        static Factory f;
        return &f; 
    }

    template<class... Types>
    bool Register(const Key &name, std::any creator){
        creators_[name] = creator;
        return true;
    }
    template<class... Types>
    std::unique_ptr<T> CreateByName(const Key &name, Types...args){
        assert(creators_.contains(name));
        auto creator = std::any_cast<std::function<T*(Types ...)>>(creators_.at(name));
        auto* u = creator(args...);
        std::unique_ptr<T> u_ptr{u};
        return std::move(u_ptr);
    }
    //std::function<int(int, int)> 

private:
    Factory() = default;
    std::map <Key, std::any> creators_;

};