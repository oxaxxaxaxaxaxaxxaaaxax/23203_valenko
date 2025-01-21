#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <QApplication>
#include <QDebug>
#include <QTimer>


MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    ui->button_->setCheckable(true);
    ui->run_button->setCheckable(true);
    connect(ui->button_, &QPushButton::clicked, this, &MainWindow::SetInitialState);
    connect(ui->run_button, &QPushButton::clicked, this, &MainWindow::RunSlot);
    connect(ui->clear_button, &QPushButton::clicked, this, &MainWindow::Clear);

    setWindowTitle("Game Of Life");
    resize(950, 850);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::SetInitialState(bool checked)
{
    if(checked){
        ui->button_->setText("Quit");
        if(!gameOfLife){
            gameOfLife = new Engine(this);
            gameOfLife->show();
        }
    }else{
        ui->button_->setText("Play");
       gameOfLife->close();
       delete gameOfLife;
       gameOfLife=nullptr;
    }
}

void MainWindow::RunSlot(bool checked){
    if(checked){
        if(!gameOfLife){
            return;
        }
        ui->run_button->setText("Pause");
        gameOfLife->RunGame();
    }else{
        if(!gameOfLife){
            return;
        }
        ui->run_button->setText("Run");
        gameOfLife->StopGame();
    }
}

void MainWindow::Clear(){
    if(!gameOfLife){
        return;
    }
    gameOfLife->close();
    delete gameOfLife;
    gameOfLife=nullptr;
    gameOfLife = new Engine(this);
    gameOfLife->show();
}
