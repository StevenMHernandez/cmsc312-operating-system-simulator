cmake_minimum_required(VERSION 3.3)
project(cmsc312_operating_system_simulator)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

set(SOURCE_FILES main.cpp)
add_executable(cmsc312_operating_system_simulator ${SOURCE_FILES})