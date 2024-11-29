#pragma once

#include <functional>

template<class T>
struct Creator {
    Creator() {
         //()- не захватывает аргументов
        Factory<std::string, Strategy, std::function<Strategy*()>>::GetInstance()->Register("Strategy_1", []() -> auto {return new T();});
        //Factory<std::string, Strategy, Strategy * (*)()>::GetInstance()->Register("Strategy_1", []() -> auto {return new T();});
    }
};