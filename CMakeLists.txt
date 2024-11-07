cmake_minimum_required(VERSION 3.0)

set(CMAKE_EXPORT_COMPILE_COMMANDS ON)

project("gtest")

enable_testing()
add_subdirectory(googletest)
add_executable(hashtable_test hashtable.cpp hashtable_test.cpp)
target_compile_options(hashtable_test PRIVATE --coverage -fsanitize=address -g)
target_link_options(hashtable_test PRIVATE -lgcov --coverage -fsanitize=address)
target_link_libraries(hashtable_test gtest_main gmock_main)