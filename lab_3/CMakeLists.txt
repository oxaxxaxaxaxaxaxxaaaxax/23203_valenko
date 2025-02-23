cmake_minimum_required(VERSION 3.14) # Минимальная версия CMake
project(MyProject VERSION 1.0 LANGUAGES CXX) # Название проекта и язык

# Включение автоматической генерации moc, uic и rcc
set(CMAKE_AUTOMOC ON) # Для автоматической генерации moc-файлов
set(CMAKE_AUTORCC ON) # Для автоматической обработки ресурсов (qrc)
set(CMAKE_AUTOUIC ON) # Для автоматической генерации ui-файлов

# Поиск необходимых модулей Qt
find_package(Qt6 REQUIRED COMPONENTS Core Gui Widgets)

set(SOURCES
    main.cpp
    mainwindow.cpp
    engine.cpp 
    window.cpp
    mainwindow.ui
)

set(HEADERS
    mainwindow.h
    engine.h
    window.h
)

# Добавляем файлы ресурсов (если есть)
set(RESOURCES
    resources.qrc
)

# Создаем исполняемый файл
add_executable(MyProject ${SOURCES} ${HEADERS} ${RESOURCES})

# Подключаем модули Qt к проекту
target_link_libraries(MyProject PRIVATE Qt6::Core Qt6::Gui Qt6::Widgets)