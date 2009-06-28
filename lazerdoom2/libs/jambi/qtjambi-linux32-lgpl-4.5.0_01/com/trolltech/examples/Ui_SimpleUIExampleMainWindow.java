/********************************************************************************
** Form generated from reading ui file 'SimpleUIExampleMainWindow.jui'
**
** Created: Tue Mar 10 10:27:05 2009
**      by: Qt User Interface Compiler version 4.5.0
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package com.trolltech.examples;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_SimpleUIExampleMainWindow implements com.trolltech.qt.QUiForm<QMainWindow>
{
    public QWidget centralwidget;
    public QGridLayout gridLayout;
    public QTextBrowser textBrowser;
    public QLabel label_Result;
    public QPushButton pushButton_OpenDialog;
    public QFrame line;
    public QSpacerItem spacerItem;
    public QMenuBar menubar;
    public QStatusBar statusbar;

    public Ui_SimpleUIExampleMainWindow() { super(); }

    public void setupUi(QMainWindow SimpleUIExampleMainWindow)
    {
        SimpleUIExampleMainWindow.setObjectName("SimpleUIExampleMainWindow");
        SimpleUIExampleMainWindow.resize(new QSize(459, 343).expandedTo(SimpleUIExampleMainWindow.minimumSizeHint()));
        centralwidget = new QWidget(SimpleUIExampleMainWindow);
        centralwidget.setObjectName("centralwidget");
        gridLayout = new QGridLayout(centralwidget);
        gridLayout.setSpacing(6);
        gridLayout.setMargin(9);
        gridLayout.setObjectName("gridLayout");
        textBrowser = new QTextBrowser(centralwidget);
        textBrowser.setObjectName("textBrowser");

        gridLayout.addWidget(textBrowser, 3, 0, 1, 2);

        label_Result = new QLabel(centralwidget);
        label_Result.setObjectName("label_Result");

        gridLayout.addWidget(label_Result, 2, 0, 1, 1);

        pushButton_OpenDialog = new QPushButton(centralwidget);
        pushButton_OpenDialog.setObjectName("pushButton_OpenDialog");

        gridLayout.addWidget(pushButton_OpenDialog, 0, 0, 1, 1);

        line = new QFrame(centralwidget);
        line.setObjectName("line");
        line.setFrameShape(QFrame.Shape.HLine);
        line.setFrameShadow(QFrame.Shadow.Sunken);

        gridLayout.addWidget(line, 1, 0, 1, 2);

        spacerItem = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        gridLayout.addItem(spacerItem, 0, 1, 1, 1);

        SimpleUIExampleMainWindow.setCentralWidget(centralwidget);
        menubar = new QMenuBar(SimpleUIExampleMainWindow);
        menubar.setObjectName("menubar");
        menubar.setGeometry(new QRect(0, 0, 459, 19));
        SimpleUIExampleMainWindow.setMenuBar(menubar);
        statusbar = new QStatusBar(SimpleUIExampleMainWindow);
        statusbar.setObjectName("statusbar");
        SimpleUIExampleMainWindow.setStatusBar(statusbar);
        retranslateUi(SimpleUIExampleMainWindow);
        pushButton_OpenDialog.clicked.connect(textBrowser, "clear()");

        SimpleUIExampleMainWindow.connectSlotsByName();
    } // setupUi

    void retranslateUi(QMainWindow SimpleUIExampleMainWindow)
    {
        SimpleUIExampleMainWindow.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("SimpleUIExampleMainWindow", "MainWindow", null));
        label_Result.setText(com.trolltech.qt.core.QCoreApplication.translate("SimpleUIExampleMainWindow", "Result:", null));
        pushButton_OpenDialog.setText(com.trolltech.qt.core.QCoreApplication.translate("SimpleUIExampleMainWindow", "Open Dialog", null));
    } // retranslateUi

}

