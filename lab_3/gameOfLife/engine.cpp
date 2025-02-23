#include "engine.h"

#include <QColor>
#include <QDebug>
#include <QGraphicsRectItem>
#include <QPoint>
#include <QPointF>
#include <QTimer>

namespace{
    constexpr size_t paintFieldSize   = 700;
    constexpr size_t cellSize = 10;
}

Engine::Engine(QWidget* parent)
    : QGraphicsView(parent)
{
    resize(750,750);
    scene = new QGraphicsScene();
    scene->setSceneRect(0, 0, paintFieldSize  , paintFieldSize );
    for(size_t i=0;i<paintFieldSize ;i+=cellSize){
        for(size_t j=0;j<paintFieldSize ;j+=cellSize){
            QGraphicsRectItem *cell = new QGraphicsRectItem();
            cell->setRect(i,j,cellSize,cellSize);
            QBrush brush(Qt::white);
            cell->setBrush(brush);
            QPen pen(Qt::black,1);
            cell->setPen(pen);
            scene->addItem(cell);
        }
    }
    setScene(scene);
    game = new Game_Of_Life();


    connect(game->universe.get(), &Field::ChangeState, this, &Engine::SetCellColor);
    connect(game->universe.get(), &Field::ChangeField, this, &Engine::RepaintField);
    timer = new QTimer(this);
    connect(timer, &QTimer::timeout, game->universe.get(), &Field::UpdateFieldState);
}

Engine::~Engine(){
    delete timer;
    timer = nullptr;
    delete game;
    game = nullptr;
    delete scene;
    scene = nullptr;

}

void Engine::SetCellColor(int x, int y){
    QGraphicsRectItem *cell = static_cast<QGraphicsRectItem *>(scene->itemAt(x,y,QTransform()));
    if(game->universe->GetCellState((x/cellSize), (y/cellSize)) == State::alive){
        QBrush brush(Qt::black);
        cell->setBrush(brush);
        QPen pen(Qt::white,1);
        cell->setPen(pen);
    }
    else{
        QBrush brush(Qt::white);
        cell->setBrush(brush);
        QPen pen(Qt::black,1);
        cell->setPen(pen);
    }
}

void Engine::mousePressEvent(QMouseEvent *event){
    if(event->button()==Qt::LeftButton){
        int x = event->x();
        int y = event->y();
//        QPoint globalPos = event->globalPos();
//        QPoint widgetPosFromGlobal = mapFromGlobal(globalPos);
        QPointF scenePos(0, 0);
        QPoint widgetPos = mapFromScene(scenePos);
        if(x<widgetPos.x()|| y<widgetPos.y()|| x>=paintFieldSize || y >= paintFieldSize){
            return;
        }
        QPointF coordPoint = mapToScene(x,y);
        QPoint point = coordPoint.toPoint();
        lastStateX =(point.x()/cellSize);
        lastStateY = (point.y()/cellSize);
        game->universe->UpdateCellState((point.x()/cellSize),(point.y()/cellSize));
    }
}

void Engine::mouseMoveEvent(QMouseEvent *event){
    //if(event->button()&Qt::LeftButton){
        int x = event->x();
        int y = event->y();
        QPointF scenePos(0, 0);
        QPoint widgetPos = mapFromScene(scenePos);
        if(x<=widgetPos.x()|| y<=widgetPos.y() || x >= paintFieldSize || y >= paintFieldSize){
            return;
        }
        QPointF coordPoint = mapToScene(x,y);
        QPoint point = coordPoint.toPoint();

        int curStateX = (point.x()/cellSize);
        int curStateY = (point.y()/cellSize);
        if(lastStateX == curStateX && lastStateY == curStateY){
            return;
        }
        lastStateX = curStateX;
        lastStateY = curStateY;
        game->universe->UpdateCellState((point.x()/cellSize),(point.y()/cellSize));
    //}

}

void Engine::wheelEvent(QWheelEvent *event){
    int baseScale = transform().m11();
    if(event->angleDelta().y() > 0){
        scale(1.1,1.1);
    }else{
        if (baseScale > 0.1){
            scale(0.9,0.9);
        }
    }
}

void Engine::RepaintField(){
    for(size_t i=0;i<paintFieldSize ;i+=cellSize){
        for(size_t j=0;j<paintFieldSize ;j+=cellSize){
            QGraphicsRectItem *cell = static_cast<QGraphicsRectItem *>(scene->itemAt(i,j,QTransform()));
            if(game->universe->GetCellState((i/cellSize), (j/cellSize)) == State::alive){
                QBrush brush(Qt::black);
                cell->setBrush(brush);
                QPen pen(Qt::white,1);
                cell->setPen(pen);
            }else{
                QBrush brush(Qt::white);
                cell->setBrush(brush);
                QPen pen(Qt::black,1);
                cell->setPen(pen);
            }
        }
    }
    scene->update();
}
void Engine::RunGame(){
    timer->start(100);
}

void Engine::StopGame(){
    timer->stop();
}
