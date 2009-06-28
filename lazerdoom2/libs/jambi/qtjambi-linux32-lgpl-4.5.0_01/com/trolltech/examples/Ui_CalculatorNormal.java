/********************************************************************************
** Form generated from reading ui file 'CalculatorNormal.jui'
**
** Created: Tue Mar 10 10:27:05 2009
**      by: Qt User Interface Compiler version 4.5.0
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package com.trolltech.examples;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_CalculatorNormal implements com.trolltech.qt.QUiForm<QMainWindow>
{
    public QWidget centralwidget;
    public QGridLayout gridLayout;
    public QHBoxLayout hboxLayout;
    public QLineEdit lineEdit;
    public QToolButton button_equal;
    public QToolButton button_functions;
    public QToolButton button_clear;
    public QTextBrowser textBrowser;
    public QTabWidget tabWidget;
    public QWidget tab;
    public QGridLayout gridLayout1;
    public QSpacerItem spacerItem;
    public QVBoxLayout vboxLayout;
    public QToolButton button_cos;
    public QToolButton button_sin;
    public QSpacerItem spacerItem1;
    public QWidget tab_2;
    public QGridLayout gridLayout2;
    public QVBoxLayout vboxLayout1;
    public QToolButton button_random;
    public QSpacerItem spacerItem2;
    public QSpacerItem spacerItem3;
    public QGroupBox groupBox_simple;
    public QGridLayout gridLayout3;
    public QToolButton button_0;
    public QToolButton button_8;
    public QToolButton button_7;
    public QToolButton button_comma;
    public QToolButton button_1;
    public QToolButton button_2;
    public QToolButton button_3;
    public QToolButton button_subtract;
    public QToolButton button_9;
    public QToolButton button_4;
    public QToolButton button_5;
    public QToolButton button_6;
    public QToolButton button_right;
    public QToolButton button_devide;
    public QToolButton button_multiply;
    public QToolButton button_left;
    public QToolButton button_add;
    public QMenuBar menubar;
    public QStatusBar statusbar;

    public Ui_CalculatorNormal() { super(); }

    public void setupUi(QMainWindow CalculatorNormal)
    {
        CalculatorNormal.setObjectName("CalculatorNormal");
        CalculatorNormal.resize(new QSize(452, 463).expandedTo(CalculatorNormal.minimumSizeHint()));
        centralwidget = new QWidget(CalculatorNormal);
        centralwidget.setObjectName("centralwidget");
        gridLayout = new QGridLayout(centralwidget);
        gridLayout.setSpacing(6);
        gridLayout.setMargin(9);
        gridLayout.setObjectName("gridLayout");
        hboxLayout = new QHBoxLayout();
        hboxLayout.setSpacing(6);
        hboxLayout.setMargin(0);
        hboxLayout.setObjectName("hboxLayout");
        lineEdit = new QLineEdit(centralwidget);
        lineEdit.setObjectName("lineEdit");
        QSizePolicy sizePolicy = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(7), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(0));
        sizePolicy.setHorizontalStretch((byte)5);
        sizePolicy.setVerticalStretch((byte)0);
        sizePolicy.setHeightForWidth(lineEdit.sizePolicy().hasHeightForWidth());
        lineEdit.setSizePolicy(sizePolicy);

        hboxLayout.addWidget(lineEdit);

        button_equal = new QToolButton(centralwidget);
        button_equal.setObjectName("button_equal");
        QSizePolicy sizePolicy1 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(7), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(0));
        sizePolicy1.setHorizontalStretch((byte)1);
        sizePolicy1.setVerticalStretch((byte)0);
        sizePolicy1.setHeightForWidth(button_equal.sizePolicy().hasHeightForWidth());
        button_equal.setSizePolicy(sizePolicy1);

        hboxLayout.addWidget(button_equal);

        button_functions = new QToolButton(centralwidget);
        button_functions.setObjectName("button_functions");

        hboxLayout.addWidget(button_functions);

        button_clear = new QToolButton(centralwidget);
        button_clear.setObjectName("button_clear");

        hboxLayout.addWidget(button_clear);


        gridLayout.addLayout(hboxLayout, 2, 0, 1, 2);

        textBrowser = new QTextBrowser(centralwidget);
        textBrowser.setObjectName("textBrowser");
        textBrowser.setLineWrapMode(com.trolltech.qt.gui.QTextEdit.LineWrapMode.NoWrap);

        gridLayout.addWidget(textBrowser, 0, 1, 2, 1);

        tabWidget = new QTabWidget(centralwidget);
        tabWidget.setObjectName("tabWidget");
        QSizePolicy sizePolicy2 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(5), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(7));
        sizePolicy2.setHorizontalStretch((byte)0);
        sizePolicy2.setVerticalStretch((byte)0);
        sizePolicy2.setHeightForWidth(tabWidget.sizePolicy().hasHeightForWidth());
        tabWidget.setSizePolicy(sizePolicy2);
        tab = new QWidget();
        tab.setObjectName("tab");
        gridLayout1 = new QGridLayout(tab);
        gridLayout1.setSpacing(6);
        gridLayout1.setMargin(9);
        gridLayout1.setObjectName("gridLayout1");
        spacerItem = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        gridLayout1.addItem(spacerItem, 0, 1, 1, 1);

        vboxLayout = new QVBoxLayout();
        vboxLayout.setSpacing(6);
        vboxLayout.setMargin(0);
        vboxLayout.setObjectName("vboxLayout");
        button_cos = new QToolButton(tab);
        button_cos.setObjectName("button_cos");
        QSizePolicy sizePolicy3 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(5), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(0));
        sizePolicy3.setHorizontalStretch((byte)0);
        sizePolicy3.setVerticalStretch((byte)0);
        sizePolicy3.setHeightForWidth(button_cos.sizePolicy().hasHeightForWidth());
        button_cos.setSizePolicy(sizePolicy3);

        vboxLayout.addWidget(button_cos);

        button_sin = new QToolButton(tab);
        button_sin.setObjectName("button_sin");
        QSizePolicy sizePolicy4 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(5), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(0));
        sizePolicy4.setHorizontalStretch((byte)0);
        sizePolicy4.setVerticalStretch((byte)0);
        sizePolicy4.setHeightForWidth(button_sin.sizePolicy().hasHeightForWidth());
        button_sin.setSizePolicy(sizePolicy4);

        vboxLayout.addWidget(button_sin);

        spacerItem1 = new QSpacerItem(20, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        vboxLayout.addItem(spacerItem1);


        gridLayout1.addLayout(vboxLayout, 0, 0, 1, 1);

        tabWidget.addTab(tab, com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", "Trigometry", null));
        tab_2 = new QWidget();
        tab_2.setObjectName("tab_2");
        gridLayout2 = new QGridLayout(tab_2);
        gridLayout2.setSpacing(6);
        gridLayout2.setMargin(9);
        gridLayout2.setObjectName("gridLayout2");
        vboxLayout1 = new QVBoxLayout();
        vboxLayout1.setSpacing(6);
        vboxLayout1.setMargin(0);
        vboxLayout1.setObjectName("vboxLayout1");
        button_random = new QToolButton(tab_2);
        button_random.setObjectName("button_random");
        QSizePolicy sizePolicy5 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(5), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(0));
        sizePolicy5.setHorizontalStretch((byte)0);
        sizePolicy5.setVerticalStretch((byte)0);
        sizePolicy5.setHeightForWidth(button_random.sizePolicy().hasHeightForWidth());
        button_random.setSizePolicy(sizePolicy5);

        vboxLayout1.addWidget(button_random);

        spacerItem2 = new QSpacerItem(20, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        vboxLayout1.addItem(spacerItem2);


        gridLayout2.addLayout(vboxLayout1, 0, 0, 1, 1);

        spacerItem3 = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        gridLayout2.addItem(spacerItem3, 0, 1, 1, 1);

        tabWidget.addTab(tab_2, com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", "Statistics", null));

        gridLayout.addWidget(tabWidget, 1, 0, 1, 1);

        groupBox_simple = new QGroupBox(centralwidget);
        groupBox_simple.setObjectName("groupBox_simple");
        QSizePolicy sizePolicy6 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(0), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(0));
        sizePolicy6.setHorizontalStretch((byte)0);
        sizePolicy6.setVerticalStretch((byte)0);
        sizePolicy6.setHeightForWidth(groupBox_simple.sizePolicy().hasHeightForWidth());
        groupBox_simple.setSizePolicy(sizePolicy6);
        groupBox_simple.setMinimumSize(new QSize(171, 221));
        gridLayout3 = new QGridLayout(groupBox_simple);
        gridLayout3.setSpacing(0);
        gridLayout3.setMargin(0);
        gridLayout3.setObjectName("gridLayout3");
        button_0 = new QToolButton(groupBox_simple);
        button_0.setObjectName("button_0");
        QSizePolicy sizePolicy7 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy7.setHorizontalStretch((byte)0);
        sizePolicy7.setVerticalStretch((byte)0);
        sizePolicy7.setHeightForWidth(button_0.sizePolicy().hasHeightForWidth());
        button_0.setSizePolicy(sizePolicy7);

        gridLayout3.addWidget(button_0, 4, 0, 1, 2);

        button_8 = new QToolButton(groupBox_simple);
        button_8.setObjectName("button_8");
        QSizePolicy sizePolicy8 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy8.setHorizontalStretch((byte)0);
        sizePolicy8.setVerticalStretch((byte)0);
        sizePolicy8.setHeightForWidth(button_8.sizePolicy().hasHeightForWidth());
        button_8.setSizePolicy(sizePolicy8);

        gridLayout3.addWidget(button_8, 1, 1, 1, 1);

        button_7 = new QToolButton(groupBox_simple);
        button_7.setObjectName("button_7");
        QSizePolicy sizePolicy9 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy9.setHorizontalStretch((byte)0);
        sizePolicy9.setVerticalStretch((byte)0);
        sizePolicy9.setHeightForWidth(button_7.sizePolicy().hasHeightForWidth());
        button_7.setSizePolicy(sizePolicy9);

        gridLayout3.addWidget(button_7, 1, 0, 1, 1);

        button_comma = new QToolButton(groupBox_simple);
        button_comma.setObjectName("button_comma");
        QSizePolicy sizePolicy10 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(5), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(5));
        sizePolicy10.setHorizontalStretch((byte)0);
        sizePolicy10.setVerticalStretch((byte)0);
        sizePolicy10.setHeightForWidth(button_comma.sizePolicy().hasHeightForWidth());
        button_comma.setSizePolicy(sizePolicy10);

        gridLayout3.addWidget(button_comma, 4, 2, 1, 1);

        button_1 = new QToolButton(groupBox_simple);
        button_1.setObjectName("button_1");
        QSizePolicy sizePolicy11 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy11.setHorizontalStretch((byte)0);
        sizePolicy11.setVerticalStretch((byte)0);
        sizePolicy11.setHeightForWidth(button_1.sizePolicy().hasHeightForWidth());
        button_1.setSizePolicy(sizePolicy11);

        gridLayout3.addWidget(button_1, 3, 0, 1, 1);

        button_2 = new QToolButton(groupBox_simple);
        button_2.setObjectName("button_2");
        QSizePolicy sizePolicy12 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy12.setHorizontalStretch((byte)0);
        sizePolicy12.setVerticalStretch((byte)0);
        sizePolicy12.setHeightForWidth(button_2.sizePolicy().hasHeightForWidth());
        button_2.setSizePolicy(sizePolicy12);

        gridLayout3.addWidget(button_2, 3, 1, 1, 1);

        button_3 = new QToolButton(groupBox_simple);
        button_3.setObjectName("button_3");
        QSizePolicy sizePolicy13 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy13.setHorizontalStretch((byte)0);
        sizePolicy13.setVerticalStretch((byte)0);
        sizePolicy13.setHeightForWidth(button_3.sizePolicy().hasHeightForWidth());
        button_3.setSizePolicy(sizePolicy13);

        gridLayout3.addWidget(button_3, 3, 2, 1, 1);

        button_subtract = new QToolButton(groupBox_simple);
        button_subtract.setObjectName("button_subtract");
        QSizePolicy sizePolicy14 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy14.setHorizontalStretch((byte)0);
        sizePolicy14.setVerticalStretch((byte)0);
        sizePolicy14.setHeightForWidth(button_subtract.sizePolicy().hasHeightForWidth());
        button_subtract.setSizePolicy(sizePolicy14);

        gridLayout3.addWidget(button_subtract, 1, 3, 1, 1);

        button_9 = new QToolButton(groupBox_simple);
        button_9.setObjectName("button_9");
        QSizePolicy sizePolicy15 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy15.setHorizontalStretch((byte)0);
        sizePolicy15.setVerticalStretch((byte)0);
        sizePolicy15.setHeightForWidth(button_9.sizePolicy().hasHeightForWidth());
        button_9.setSizePolicy(sizePolicy15);

        gridLayout3.addWidget(button_9, 1, 2, 1, 1);

        button_4 = new QToolButton(groupBox_simple);
        button_4.setObjectName("button_4");
        QSizePolicy sizePolicy16 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy16.setHorizontalStretch((byte)0);
        sizePolicy16.setVerticalStretch((byte)0);
        sizePolicy16.setHeightForWidth(button_4.sizePolicy().hasHeightForWidth());
        button_4.setSizePolicy(sizePolicy16);

        gridLayout3.addWidget(button_4, 2, 0, 1, 1);

        button_5 = new QToolButton(groupBox_simple);
        button_5.setObjectName("button_5");
        QSizePolicy sizePolicy17 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy17.setHorizontalStretch((byte)0);
        sizePolicy17.setVerticalStretch((byte)0);
        sizePolicy17.setHeightForWidth(button_5.sizePolicy().hasHeightForWidth());
        button_5.setSizePolicy(sizePolicy17);

        gridLayout3.addWidget(button_5, 2, 1, 1, 1);

        button_6 = new QToolButton(groupBox_simple);
        button_6.setObjectName("button_6");
        QSizePolicy sizePolicy18 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy18.setHorizontalStretch((byte)0);
        sizePolicy18.setVerticalStretch((byte)0);
        sizePolicy18.setHeightForWidth(button_6.sizePolicy().hasHeightForWidth());
        button_6.setSizePolicy(sizePolicy18);

        gridLayout3.addWidget(button_6, 2, 2, 1, 1);

        button_right = new QToolButton(groupBox_simple);
        button_right.setObjectName("button_right");
        QSizePolicy sizePolicy19 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy19.setHorizontalStretch((byte)0);
        sizePolicy19.setVerticalStretch((byte)0);
        sizePolicy19.setHeightForWidth(button_right.sizePolicy().hasHeightForWidth());
        button_right.setSizePolicy(sizePolicy19);

        gridLayout3.addWidget(button_right, 0, 1, 1, 1);

        button_devide = new QToolButton(groupBox_simple);
        button_devide.setObjectName("button_devide");
        QSizePolicy sizePolicy20 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy20.setHorizontalStretch((byte)0);
        sizePolicy20.setVerticalStretch((byte)0);
        sizePolicy20.setHeightForWidth(button_devide.sizePolicy().hasHeightForWidth());
        button_devide.setSizePolicy(sizePolicy20);

        gridLayout3.addWidget(button_devide, 0, 2, 1, 1);

        button_multiply = new QToolButton(groupBox_simple);
        button_multiply.setObjectName("button_multiply");
        QSizePolicy sizePolicy21 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy21.setHorizontalStretch((byte)0);
        sizePolicy21.setVerticalStretch((byte)0);
        sizePolicy21.setHeightForWidth(button_multiply.sizePolicy().hasHeightForWidth());
        button_multiply.setSizePolicy(sizePolicy21);

        gridLayout3.addWidget(button_multiply, 0, 3, 1, 1);

        button_left = new QToolButton(groupBox_simple);
        button_left.setObjectName("button_left");
        QSizePolicy sizePolicy22 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy22.setHorizontalStretch((byte)0);
        sizePolicy22.setVerticalStretch((byte)0);
        sizePolicy22.setHeightForWidth(button_left.sizePolicy().hasHeightForWidth());
        button_left.setSizePolicy(sizePolicy22);

        gridLayout3.addWidget(button_left, 0, 0, 1, 1);

        button_add = new QToolButton(groupBox_simple);
        button_add.setObjectName("button_add");
        QSizePolicy sizePolicy23 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy23.setHorizontalStretch((byte)0);
        sizePolicy23.setVerticalStretch((byte)0);
        sizePolicy23.setHeightForWidth(button_add.sizePolicy().hasHeightForWidth());
        button_add.setSizePolicy(sizePolicy23);

        gridLayout3.addWidget(button_add, 2, 3, 1, 1);


        gridLayout.addWidget(groupBox_simple, 0, 0, 1, 1);

        CalculatorNormal.setCentralWidget(centralwidget);
        menubar = new QMenuBar(CalculatorNormal);
        menubar.setObjectName("menubar");
        menubar.setGeometry(new QRect(0, 0, 452, 29));
        CalculatorNormal.setMenuBar(menubar);
        statusbar = new QStatusBar(CalculatorNormal);
        statusbar.setObjectName("statusbar");
        CalculatorNormal.setStatusBar(statusbar);
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
        QWidget.setTabOrder(button_comma, tabWidget);
        QWidget.setTabOrder(tabWidget, button_cos);
        QWidget.setTabOrder(button_cos, button_functions);
        QWidget.setTabOrder(button_functions, button_sin);
        QWidget.setTabOrder(button_sin, textBrowser);
        retranslateUi(CalculatorNormal);
        lineEdit.returnPressed.connect(button_equal, "click()");
        button_clear.clicked.connect(lineEdit, "clear()");

        tabWidget.setCurrentIndex(0);


        CalculatorNormal.connectSlotsByName();
    } // setupUi

    void retranslateUi(QMainWindow CalculatorNormal)
    {
        CalculatorNormal.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", "MainWindow", null));
        button_equal.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", "=", null));
        button_functions.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", "fun", null));
        button_clear.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", "c", null));
        button_cos.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", "Cos", null));
        button_sin.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", "Sin", null));
        tabWidget.setTabText(tabWidget.indexOf(tab), com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", "Trigometry", null));
        button_random.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", "Random", null));
        tabWidget.setTabText(tabWidget.indexOf(tab_2), com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", "Statistics", null));
        groupBox_simple.setTitle(com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", "Simple", null));
        button_0.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", "0", null));
        button_8.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", "8", null));
        button_7.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", "7", null));
        button_comma.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", ".", null));
        button_1.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", "1", null));
        button_2.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", "2", null));
        button_3.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", "3", null));
        button_subtract.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", "-", null));
        button_9.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", "9", null));
        button_4.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", "4", null));
        button_5.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", "5", null));
        button_6.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", "6", null));
        button_right.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", ")", null));
        button_devide.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", "/", null));
        button_multiply.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", "*", null));
        button_left.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", "(", null));
        button_add.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorNormal", "+", null));
    } // retranslateUi

}

