cmake_minimum_required(VERSION 3.10)
project("gtest")


#target_link_libraries(blackjack ${GTEST_LIBRARIES})

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED True)
set(CMAKE_BUILD_TYPE Debug)
set(CMAKE_EXPORT_COMPILE_COMMANDS ON)
find_package(Boost REQUIRED COMPONENTS program_options)
add_executable(blackjack main.cpp basic_deck.cpp card.cpp console_interface.cpp engine_1.cpp hand.cpp player.cpp simple_deck.cpp strategy_1.cpp strategy_2.cpp strategy_3.cpp engine_2.cpp engine_3.cpp n_deck.cpp strategy_play.cpp strategy_config.cpp)

target_link_libraries(blackjack Boost::program_options)