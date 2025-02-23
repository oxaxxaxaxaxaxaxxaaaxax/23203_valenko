/********************************************************************************
** Form generated from reading UI file 'mainwindow.ui'
**
** Created by: Qt User Interface Compiler version 5.15.3
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_MAINWINDOW_H
#define UI_MAINWINDOW_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_MainWindow
{
public:
    QWidget *centralwidget;
    QPushButton *button_;
    QPushButton *run_button;
    QPushButton *clear_button;
    QPushButton *min_button;
    QPushButton *plus_button;
    QMenuBar *menubar;
    QStatusBar *statusbar;

    void setupUi(QMainWindow *MainWindow)
    {
        if (MainWindow->objectName().isEmpty())
            MainWindow->setObjectName(QString::fromUtf8("MainWindow"));
        MainWindow->resize(856, 875);
        centralwidget = new QWidget(MainWindow);
        centralwidget->setObjectName(QString::fromUtf8("centralwidget"));
        button_ = new QPushButton(centralwidget);
        button_->setObjectName(QString::fromUtf8("button_"));
        button_->setGeometry(QRect(30, 760, 161, 51));
        run_button = new QPushButton(centralwidget);
        run_button->setObjectName(QString::fromUtf8("run_button"));
        run_button->setGeometry(QRect(210, 760, 161, 51));
        run_button->setCheckable(true);
        clear_button = new QPushButton(centralwidget);
        clear_button->setObjectName(QString::fromUtf8("clear_button"));
        clear_button->setGeometry(QRect(390, 760, 161, 51));
        min_button = new QPushButton(centralwidget);
        min_button->setObjectName(QString::fromUtf8("min_button"));
        min_button->setGeometry(QRect(570, 760, 91, 51));
        plus_button = new QPushButton(centralwidget);
        plus_button->setObjectName(QString::fromUtf8("plus_button"));
        plus_button->setGeometry(QRect(680, 760, 81, 51));
        MainWindow->setCentralWidget(centralwidget);
        menubar = new QMenuBar(MainWindow);
        menubar->setObjectName(QString::fromUtf8("menubar"));
        menubar->setGeometry(QRect(0, 0, 856, 22));
        MainWindow->setMenuBar(menubar);
        statusbar = new QStatusBar(MainWindow);
        statusbar->setObjectName(QString::fromUtf8("statusbar"));
        MainWindow->setStatusBar(statusbar);

        retranslateUi(MainWindow);

        QMetaObject::connectSlotsByName(MainWindow);
    } // setupUi

    void retranslateUi(QMainWindow *MainWindow)
    {
        MainWindow->setWindowTitle(QCoreApplication::translate("MainWindow", "MainWindow", nullptr));
        button_->setText(QCoreApplication::translate("MainWindow", "Play", nullptr));
        run_button->setText(QCoreApplication::translate("MainWindow", "Run", nullptr));
        clear_button->setText(QCoreApplication::translate("MainWindow", "Clear", nullptr));
        min_button->setText(QCoreApplication::translate("MainWindow", "Minus", nullptr));
        plus_button->setText(QCoreApplication::translate("MainWindow", "Plus", nullptr));
    } // retranslateUi

};

namespace Ui {
    class MainWindow: public Ui_MainWindow {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_MAINWINDOW_H
