#pragma once
#include "engine.h"
#include <QMainWindow>

QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();
private slots:
    void SetInitialState(bool checked);
    void RunSlot(bool checked);
    void Clear();
    void IncreaseNeighbours();
    void ReduceNeighbours();

private:
    Ui::MainWindow *ui;
    Engine *gameOfLife = nullptr;
};

