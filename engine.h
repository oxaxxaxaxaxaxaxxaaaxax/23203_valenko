#pragma once

#include <QGraphicsView>
#include <QGraphicsScene>
#include <QGraphicsRectItem>
#include <QMouseEvent>
#include <QWidget>


#include "game_of_life.h"


class Engine : public QGraphicsView
{
public:
    Engine(QWidget* parent);
    ~Engine();
    void RunGame();
    void StopGame();
    void RepaintField();
    void UpdateCellState(int x,int y);
    Game_Of_Life *game = nullptr;
public slots:
    void SetCellColor(int x,int y);
protected:
    void mousePressEvent(QMouseEvent *event) override;
    void mouseMoveEvent(QMouseEvent *event) override;
    void wheelEvent(QWheelEvent *event) override;
private:
    QGraphicsScene *scene= nullptr;
    QTimer *timer = nullptr;
    bool isScaling = false;
    int lastStateX;
    int lastStateY;
};

