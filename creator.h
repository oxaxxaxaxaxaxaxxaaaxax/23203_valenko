#pragma once

#include <functional>
#include "factory.h"

template<class T, class TParent, class Key>
struct Creator {
    Creator(Key k) {
         //()- не захватывает аргументов
        Factory<std::string, TParent, std::function<TParent*()>>::GetInstance()->Register(k, []() -> TParent*{return new T();});
        //Factory<std::string, Strategy, Strategy * (*)()>::GetInstance()->Register("Strategy_1", []() -> auto {return new T();});
    }
};