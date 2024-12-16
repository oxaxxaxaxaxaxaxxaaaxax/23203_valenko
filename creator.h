#pragma once
#include <functional>
#include "factory.h"


template<class T, class TParent, class Key, class... Types>
struct Creator {
    Creator(Key k) {
        Factory<Key, TParent>::GetInstance()->Register(k, std::function<TParent*(Types ...)>([](Types... args) -> TParent* {return new T(args ...);}));    }
};