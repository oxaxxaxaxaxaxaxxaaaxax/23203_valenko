cmake_minimum_required(VERSION 3.10)
project(MyProgram)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED True)

find_package(Boost REQUIRED COMPONENTS program_options)

add_executable(main main.cpp basic_deck.cpp card.cpp console_interface.cpp engine_1.cpp hand.cpp simple_deck.cpp strategy_1.cpp strategy_2.cpp)

target_link_libraries(main Boost::program_options)