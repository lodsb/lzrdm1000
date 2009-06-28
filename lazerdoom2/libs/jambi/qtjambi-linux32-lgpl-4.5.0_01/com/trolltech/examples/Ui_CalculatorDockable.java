/********************************************************************************
** Form generated from reading ui file 'CalculatorDockable.jui'
**
** Created: Tue Mar 10 10:27:05 2009
**      by: Qt User Interface Compiler version 4.5.0
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package com.trolltech.examples;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_CalculatorDockable implements com.trolltech.qt.QUiForm<QMainWindow>
{
    public QWidget centralwidget;
    public QGridLayout gridLayout;
    public QTextBrowser textBrowser;
    public QMenuBar menubar;
    public QStatusBar statusbar;
    public QDockWidget dockWidget_2;
    public QWidget dockWidgetContents_2;
    public QGridLayout gridLayout1;
    public QVBoxLayout vboxLayout;
    public QToolButton button_cos;
    public QToolButton button_sin;
    public QSpacerItem spacerItem;
    public QSpacerItem spacerItem1;
    public QDockWidget dockWidget;
    public QWidget dockWidgetContents;
    public QGridLayout gridLayout2;
    public QToolButton button_comma;
    public QToolButton button_3;
    public QToolButton button_devide;
    public QToolButton button_9;
    public QToolButton button_6;
    public QToolButton button_0;
    public QToolButton button_1;
    public QToolButton button_2;
    public QToolButton button_5;
    public QToolButton button_right;
    public QToolButton button_8;
    public QToolButton button_multiply;
    public QToolButton button_subtract;
    public QToolButton button_left;
    public QToolButton button_7;
    public QToolButton button_add;
    public QToolButton button_4;
    public QDockWidget dockWidget_4;
    public QWidget dockWidgetContents_4;
    public QGridLayout gridLayout3;
    public QHBoxLayout hboxLayout;
    public QLineEdit lineEdit;
    public QToolButton button_equal;
    public QToolButton button_functions;
    public QToolButton button_clear;

    public Ui_CalculatorDockable() { super(); }

    public void setupUi(QMainWindow CalculatorDockable)
    {
        CalculatorDockable.setObjectName("CalculatorDockable");
        CalculatorDockable.resize(new QSize(455, 398).expandedTo(CalculatorDockable.minimumSizeHint()));
        CalculatorDockable.setDockNestingEnabled(true);
        centralwidget = new QWidget(CalculatorDockable);
        centralwidget.setObjectName("centralwidget");
        gridLayout = new QGridLayout(centralwidget);
        gridLayout.setSpacing(6);
        gridLayout.setMargin(9);
        gridLayout.setObjectName("gridLayout");
        textBrowser = new QTextBrowser(centralwidget);
        textBrowser.setObjectName("textBrowser");
        QSizePolicy sizePolicy = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(7), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(7));
        sizePolicy.setHorizontalStretch((byte)1);
        sizePolicy.setVerticalStretch((byte)0);
        sizePolicy.setHeightForWidth(textBrowser.sizePolicy().hasHeightForWidth());
        textBrowser.setSizePolicy(sizePolicy);
        textBrowser.setLineWrapMode(com.trolltech.qt.gui.QTextEdit.LineWrapMode.NoWrap);

        gridLayout.addWidget(textBrowser, 0, 0, 1, 1);

        CalculatorDockable.setCentralWidget(centralwidget);
        menubar = new QMenuBar(CalculatorDockable);
        menubar.setObjectName("menubar");
        menubar.setGeometry(new QRect(0, 0, 455, 19));
        CalculatorDockable.setMenuBar(menubar);
        statusbar = new QStatusBar(CalculatorDockable);
        statusbar.setObjectName("statusbar");
        CalculatorDockable.setStatusBar(statusbar);
        dockWidget_2 = new QDockWidget(CalculatorDockable);
        dockWidget_2.setObjectName("dockWidget_2");
        dockWidgetContents_2 = new QWidget();
        dockWidgetContents_2.setObjectName("dockWidgetContents_2");
        gridLayout1 = new QGridLayout(dockWidgetContents_2);
        gridLayout1.setSpacing(6);
        gridLayout1.setMargin(9);
        gridLayout1.setObjectName("gridLayout1");
        vboxLayout = new QVBoxLayout();
        vboxLayout.setSpacing(6);
        vboxLayout.setMargin(0);
        vboxLayout.setObjectName("vboxLayout");
        button_cos = new QToolButton(dockWidgetContents_2);
        button_cos.setObjectName("button_cos");
        QSizePolicy sizePolicy1 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(5), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(0));
        sizePolicy1.setHorizontalStretch((byte)0);
        sizePolicy1.setVerticalStretch((byte)0);
        sizePolicy1.setHeightForWidth(button_cos.sizePolicy().hasHeightForWidth());
        button_cos.setSizePolicy(sizePolicy1);

        vboxLayout.addWidget(button_cos);

        button_sin = new QToolButton(dockWidgetContents_2);
        button_sin.setObjectName("button_sin");
        QSizePolicy sizePolicy2 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(5), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(0));
        sizePolicy2.setHorizontalStretch((byte)0);
        sizePolicy2.setVerticalStretch((byte)0);
        sizePolicy2.setHeightForWidth(button_sin.sizePolicy().hasHeightForWidth());
        button_sin.setSizePolicy(sizePolicy2);

        vboxLayout.addWidget(button_sin);

        spacerItem = new QSpacerItem(20, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        vboxLayout.addItem(spacerItem);


        gridLayout1.addLayout(vboxLayout, 0, 0, 1, 1);

        spacerItem1 = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        gridLayout1.addItem(spacerItem1, 0, 1, 1, 1);

        dockWidget_2.setWidget(dockWidgetContents_2);
        CalculatorDockable.addDockWidget(com.trolltech.qt.core.Qt.DockWidgetArea.resolve(2), dockWidget_2);
        dockWidget = new QDockWidget(CalculatorDockable);
        dockWidget.setObjectName("dockWidget");
        QSizePolicy sizePolicy3 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(4), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(4));
        sizePolicy3.setHorizontalStretch((byte)0);
        sizePolicy3.setVerticalStretch((byte)0);
        sizePolicy3.setHeightForWidth(dockWidget.sizePolicy().hasHeightForWidth());
        dockWidget.setSizePolicy(sizePolicy3);
        dockWidgetContents = new QWidget();
        dockWidgetContents.setObjectName("dockWidgetContents");
        gridLayout2 = new QGridLayout(dockWidgetContents);
        gridLayout2.setSpacing(6);
        gridLayout2.setMargin(9);
        gridLayout2.setObjectName("gridLayout2");
        button_comma = new QToolButton(dockWidgetContents);
        button_comma.setObjectName("button_comma");
        QSizePolicy sizePolicy4 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy4.setHorizontalStretch((byte)0);
        sizePolicy4.setVerticalStretch((byte)0);
        sizePolicy4.setHeightForWidth(button_comma.sizePolicy().hasHeightForWidth());
        button_comma.setSizePolicy(sizePolicy4);

        gridLayout2.addWidget(button_comma, 4, 2, 1, 1);

        button_3 = new QToolButton(dockWidgetContents);
        button_3.setObjectName("button_3");
        QSizePolicy sizePolicy5 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy5.setHorizontalStretch((byte)0);
        sizePolicy5.setVerticalStretch((byte)0);
        sizePolicy5.setHeightForWidth(button_3.sizePolicy().hasHeightForWidth());
        button_3.setSizePolicy(sizePolicy5);

        gridLayout2.addWidget(button_3, 3, 2, 1, 1);

        button_devide = new QToolButton(dockWidgetContents);
        button_devide.setObjectName("button_devide");
        QSizePolicy sizePolicy6 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy6.setHorizontalStretch((byte)0);
        sizePolicy6.setVerticalStretch((byte)0);
        sizePolicy6.setHeightForWidth(button_devide.sizePolicy().hasHeightForWidth());
        button_devide.setSizePolicy(sizePolicy6);

        gridLayout2.addWidget(button_devide, 0, 2, 1, 1);

        button_9 = new QToolButton(dockWidgetContents);
        button_9.setObjectName("button_9");
        QSizePolicy sizePolicy7 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy7.setHorizontalStretch((byte)0);
        sizePolicy7.setVerticalStretch((byte)0);
        sizePolicy7.setHeightForWidth(button_9.sizePolicy().hasHeightForWidth());
        button_9.setSizePolicy(sizePolicy7);

        gridLayout2.addWidget(button_9, 1, 2, 1, 1);

        button_6 = new QToolButton(dockWidgetContents);
        button_6.setObjectName("button_6");
        QSizePolicy sizePolicy8 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy8.setHorizontalStretch((byte)0);
        sizePolicy8.setVerticalStretch((byte)0);
        sizePolicy8.setHeightForWidth(button_6.sizePolicy().hasHeightForWidth());
        button_6.setSizePolicy(sizePolicy8);

        gridLayout2.addWidget(button_6, 2, 2, 1, 1);

        button_0 = new QToolButton(dockWidgetContents);
        button_0.setObjectName("button_0");
        QSizePolicy sizePolicy9 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy9.setHorizontalStretch((byte)0);
        sizePolicy9.setVerticalStretch((byte)0);
        sizePolicy9.setHeightForWidth(button_0.sizePolicy().hasHeightForWidth());
        button_0.setSizePolicy(sizePolicy9);

        gridLayout2.addWidget(button_0, 4, 0, 1, 2);

        button_1 = new QToolButton(dockWidgetContents);
        button_1.setObjectName("button_1");
        QSizePolicy sizePolicy10 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy10.setHorizontalStretch((byte)0);
        sizePolicy10.setVerticalStretch((byte)0);
        sizePolicy10.setHeightForWidth(button_1.sizePolicy().hasHeightForWidth());
        button_1.setSizePolicy(sizePolicy10);

        gridLayout2.addWidget(button_1, 3, 0, 1, 1);

        button_2 = new QToolButton(dockWidgetContents);
        button_2.setObjectName("button_2");
        QSizePolicy sizePolicy11 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy11.setHorizontalStretch((byte)0);
        sizePolicy11.setVerticalStretch((byte)0);
        sizePolicy11.setHeightForWidth(button_2.sizePolicy().hasHeightForWidth());
        button_2.setSizePolicy(sizePolicy11);

        gridLayout2.addWidget(button_2, 3, 1, 1, 1);

        button_5 = new QToolButton(dockWidgetContents);
        button_5.setObjectName("button_5");
        QSizePolicy sizePolicy12 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy12.setHorizontalStretch((byte)0);
        sizePolicy12.setVerticalStretch((byte)0);
        sizePolicy12.setHeightForWidth(button_5.sizePolicy().hasHeightForWidth());
        button_5.setSizePolicy(sizePolicy12);

        gridLayout2.addWidget(button_5, 2, 1, 1, 1);

        button_right = new QToolButton(dockWidgetContents);
        button_right.setObjectName("button_right");
        QSizePolicy sizePolicy13 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy13.setHorizontalStretch((byte)0);
        sizePolicy13.setVerticalStretch((byte)0);
        sizePolicy13.setHeightForWidth(button_right.sizePolicy().hasHeightForWidth());
        button_right.setSizePolicy(sizePolicy13);

        gridLayout2.addWidget(button_right, 0, 1, 1, 1);

        button_8 = new QToolButton(dockWidgetContents);
        button_8.setObjectName("button_8");
        QSizePolicy sizePolicy14 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy14.setHorizontalStretch((byte)0);
        sizePolicy14.setVerticalStretch((byte)0);
        sizePolicy14.setHeightForWidth(button_8.sizePolicy().hasHeightForWidth());
        button_8.setSizePolicy(sizePolicy14);

        gridLayout2.addWidget(button_8, 1, 1, 1, 1);

        button_multiply = new QToolButton(dockWidgetContents);
        button_multiply.setObjectName("button_multiply");
        QSizePolicy sizePolicy15 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy15.setHorizontalStretch((byte)0);
        sizePolicy15.setVerticalStretch((byte)0);
        sizePolicy15.setHeightForWidth(button_multiply.sizePolicy().hasHeightForWidth());
        button_multiply.setSizePolicy(sizePolicy15);

        gridLayout2.addWidget(button_multiply, 0, 3, 1, 1);

        button_subtract = new QToolButton(dockWidgetContents);
        button_subtract.setObjectName("button_subtract");
        QSizePolicy sizePolicy16 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy16.setHorizontalStretch((byte)0);
        sizePolicy16.setVerticalStretch((byte)0);
        sizePolicy16.setHeightForWidth(button_subtract.sizePolicy().hasHeightForWidth());
        button_subtract.setSizePolicy(sizePolicy16);

        gridLayout2.addWidget(button_subtract, 1, 3, 1, 1);

        button_left = new QToolButton(dockWidgetContents);
        button_left.setObjectName("button_left");
        QSizePolicy sizePolicy17 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy17.setHorizontalStretch((byte)0);
        sizePolicy17.setVerticalStretch((byte)0);
        sizePolicy17.setHeightForWidth(button_left.sizePolicy().hasHeightForWidth());
        button_left.setSizePolicy(sizePolicy17);

        gridLayout2.addWidget(button_left, 0, 0, 1, 1);

        button_7 = new QToolButton(dockWidgetContents);
        button_7.setObjectName("button_7");
        QSizePolicy sizePolicy18 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy18.setHorizontalStretch((byte)0);
        sizePolicy18.setVerticalStretch((byte)0);
        sizePolicy18.setHeightForWidth(button_7.sizePolicy().hasHeightForWidth());
        button_7.setSizePolicy(sizePolicy18);

        gridLayout2.addWidget(button_7, 1, 0, 1, 1);

        button_add = new QToolButton(dockWidgetContents);
        button_add.setObjectName("button_add");
        QSizePolicy sizePolicy19 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy19.setHorizontalStretch((byte)0);
        sizePolicy19.setVerticalStretch((byte)0);
        sizePolicy19.setHeightForWidth(button_add.sizePolicy().hasHeightForWidth());
        button_add.setSizePolicy(sizePolicy19);

        gridLayout2.addWidget(button_add, 2, 3, 1, 1);

        button_4 = new QToolButton(dockWidgetContents);
        button_4.setObjectName("button_4");
        QSizePolicy sizePolicy20 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy20.setHorizontalStretch((byte)0);
        sizePolicy20.setVerticalStretch((byte)0);
        sizePolicy20.setHeightForWidth(button_4.sizePolicy().hasHeightForWidth());
        button_4.setSizePolicy(sizePolicy20);

        gridLayout2.addWidget(button_4, 2, 0, 1, 1);

        dockWidget.setWidget(dockWidgetContents);
        CalculatorDockable.addDockWidget(com.trolltech.qt.core.Qt.DockWidgetArea.resolve(2), dockWidget);
        dockWidget_4 = new QDockWidget(CalculatorDockable);
        dockWidget_4.setObjectName("dockWidget_4");
        QSizePolicy sizePolicy21 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(5), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(5));
        sizePolicy21.setHorizontalStretch((byte)0);
        sizePolicy21.setVerticalStretch((byte)0);
        sizePolicy21.setHeightForWidth(dockWidget_4.sizePolicy().hasHeightForWidth());
        dockWidget_4.setSizePolicy(sizePolicy21);
        dockWidgetContents_4 = new QWidget();
        dockWidgetContents_4.setObjectName("dockWidgetContents_4");
        gridLayout3 = new QGridLayout(dockWidgetContents_4);
        gridLayout3.setSpacing(6);
        gridLayout3.setMargin(9);
        gridLayout3.setObjectName("gridLayout3");
        hboxLayout = new QHBoxLayout();
        hboxLayout.setSpacing(6);
        hboxLayout.setMargin(0);
        hboxLayout.setObjectName("hboxLayout");
        lineEdit = new QLineEdit(dockWidgetContents_4);
        lineEdit.setObjectName("lineEdit");
        QSizePolicy sizePolicy22 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(7), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(0));
        sizePolicy22.setHorizontalStretch((byte)6);
        sizePolicy22.setVerticalStretch((byte)0);
        sizePolicy22.setHeightForWidth(lineEdit.sizePolicy().hasHeightForWidth());
        lineEdit.setSizePolicy(sizePolicy22);

        hboxLayout.addWidget(lineEdit);

        button_equal = new QToolButton(dockWidgetContents_4);
        button_equal.setObjectName("button_equal");
        QSizePolicy sizePolicy23 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(5), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(4));
        sizePolicy23.setHorizontalStretch((byte)1);
        sizePolicy23.setVerticalStretch((byte)0);
        sizePolicy23.setHeightForWidth(button_equal.sizePolicy().hasHeightForWidth());
        button_equal.setSizePolicy(sizePolicy23);

        hboxLayout.addWidget(button_equal);

        button_functions = new QToolButton(dockWidgetContents_4);
        button_functions.setObjectName("button_functions");

        hboxLayout.addWidget(button_functions);

        button_clear = new QToolButton(dockWidgetContents_4);
        button_clear.setObjectName("button_clear");
        QSizePolicy sizePolicy24 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(5), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(5));
        sizePolicy24.setHorizontalStretch((byte)0);
        sizePolicy24.setVerticalStretch((byte)0);
        sizePolicy24.setHeightForWidth(button_clear.sizePolicy().hasHeightForWidth());
        button_clear.setSizePolicy(sizePolicy24);

        hboxLayout.addWidget(button_clear);


        gridLayout3.addLayout(hboxLayout, 0, 0, 1, 1);

        dockWidget_4.setWidget(dockWidgetContents_4);
        CalculatorDockable.addDockWidget(com.trolltech.qt.core.Qt.DockWidgetArea.resolve(8), dockWidget_4);
        QWidget.setTabOrder(lineEdit, button_equal);
        QWidget.setTabOrder(button_equal, button_clear);
        QWidget.setTabOrder(button_clear, button_left);
        QWidget.setTabOrder(button_left, button_right);
        QWidget.setTabOrder(button_right, button_devide);
        QWidget.setTabOrder(button_devide, button_multiply);
        QWidget.setTabOrder(button_multiply, button_7);
        QWidget.setTabOrder(button_7, button_8);
        QWidget.setTabOrder(button_8, button_9);
        QWidget.setTabOrder(button_9, button_subtract);
        QWidget.setTabOrder(button_subtract, button_4);
        QWidget.setTabOrder(button_4, button_5);
        QWidget.setTabOrder(button_5, button_6);
        QWidget.setTabOrder(button_6, button_add);
        QWidget.setTabOrder(button_add, button_1);
        QWidget.setTabOrder(button_1, button_2);
        QWidget.setTabOrder(button_2, button_3);
        QWidget.setTabOrder(button_3, button_0);
        QWidget.setTabOrder(button_0, button_comma);
        QWidget.setTabOrder(button_comma, button_cos);
        QWidget.setTabOrder(button_cos, button_sin);
        QWidget.setTabOrder(button_sin, textBrowser);
        QWidget.setTabOrder(textBrowser, button_functions);
        retranslateUi(CalculatorDockable);
        lineEdit.returnPressed.connect(button_equal, "click()");
        button_clear.pressed.connect(lineEdit, "clear()");

        CalculatorDockable.connectSlotsByName();
    } // setupUi

    void retranslateUi(QMainWindow CalculatorDockable)
    {
        CalculatorDockable.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("CalculatorDockable", "MainWindow", null));
        dockWidget_2.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("CalculatorDockable", "Trigometry", null));
        button_cos.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorDockable", "Cos", null));
        button_sin.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorDockable", "Sin", null));
        dockWidget.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("CalculatorDockable", "Keypad", null));
        button_comma.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorDockable", ".", null));
        button_3.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorDockable", "3", null));
        button_devide.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorDockable", "/", null));
        button_9.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorDockable", "9", null));
        button_6.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorDockable", "6", null));
        button_0.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorDockable", "0", null));
        button_1.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorDockable", "1", null));
        button_2.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorDockable", "2", null));
        button_5.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorDockable", "5", null));
        button_right.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorDockable", ")", null));
        button_8.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorDockable", "8", null));
        button_multiply.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorDockable", "*", null));
        button_subtract.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorDockable", "-", null));
        button_left.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorDockable", "(", null));
        button_7.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorDockable", "7", null));
        button_add.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorDockable", "+", null));
        button_4.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorDockable", "4", null));
        dockWidget_4.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("CalculatorDockable", "Input", null));
        button_equal.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorDockable", "=", null));
        button_functions.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorDockable", "fun", null));
        button_clear.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorDockable", "c", null));
    } // retranslateUi

}

