/********************************************************************************
** Form generated from reading ui file 'CalculatorSimple.jui'
**
** Created: Di Apr 14 23:02:41 2009
**      by: Qt User Interface Compiler version 4.4.2
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package trolltech.examples;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_CalculatorSimple
{
    public QWidget centralwidget;
    public QGridLayout gridLayout;
    public QGridLayout gridLayout1;
    public QToolButton button_6;
    public QToolButton button_2;
    public QToolButton button_equal;
    public QToolButton button_9;
    public QToolButton button_comma;
    public QToolButton button_multiply;
    public QToolButton button_devide;
    public QToolButton button_3;
    public QToolButton button_subtract;
    public QToolButton button_add;
    public QToolButton button_7;
    public QToolButton button_4;
    public QToolButton button_1;
    public QToolButton button_right;
    public QToolButton button_0;
    public QToolButton button_5;
    public QToolButton button_left;
    public QToolButton button_8;
    public QTextBrowser textBrowser;
    public QHBoxLayout hboxLayout;
    public QLineEdit lineEdit;
    public QToolButton button_clear;
    public QMenuBar menubar;
    public QStatusBar statusbar;

    public Ui_CalculatorSimple() { super(); }

    public void setupUi(QMainWindow CalculatorSimple)
    {
        CalculatorSimple.setObjectName("CalculatorSimple");
        CalculatorSimple.resize(new QSize(327, 495).expandedTo(CalculatorSimple.minimumSizeHint()));
        centralwidget = new QWidget(CalculatorSimple);
        centralwidget.setObjectName("centralwidget");
        gridLayout = new QGridLayout(centralwidget);
        gridLayout.setSpacing(6);
        gridLayout.setMargin(9);
        gridLayout.setObjectName("gridLayout");
        gridLayout1 = new QGridLayout();
        gridLayout1.setSpacing(6);
        gridLayout1.setMargin(0);
        gridLayout1.setObjectName("gridLayout1");
        button_6 = new QToolButton(centralwidget);
        button_6.setObjectName("button_6");
        QSizePolicy sizePolicy = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy.setHorizontalStretch((byte)0);
        sizePolicy.setVerticalStretch((byte)0);
        sizePolicy.setHeightForWidth(button_6.sizePolicy().hasHeightForWidth());
        button_6.setSizePolicy(sizePolicy);
        QFont font = new QFont();
        font.setPointSize(20);
        button_6.setFont(font);

        gridLayout1.addWidget(button_6, 2, 2, 1, 1);

        button_2 = new QToolButton(centralwidget);
        button_2.setObjectName("button_2");
        QSizePolicy sizePolicy1 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy1.setHorizontalStretch((byte)0);
        sizePolicy1.setVerticalStretch((byte)0);
        sizePolicy1.setHeightForWidth(button_2.sizePolicy().hasHeightForWidth());
        button_2.setSizePolicy(sizePolicy1);
        QFont font1 = new QFont();
        font1.setPointSize(20);
        button_2.setFont(font1);

        gridLayout1.addWidget(button_2, 3, 1, 1, 1);

        button_equal = new QToolButton(centralwidget);
        button_equal.setObjectName("button_equal");
        QSizePolicy sizePolicy2 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy2.setHorizontalStretch((byte)0);
        sizePolicy2.setVerticalStretch((byte)0);
        sizePolicy2.setHeightForWidth(button_equal.sizePolicy().hasHeightForWidth());
        button_equal.setSizePolicy(sizePolicy2);
        QFont font2 = new QFont();
        font2.setPointSize(20);
        button_equal.setFont(font2);

        gridLayout1.addWidget(button_equal, 3, 3, 2, 1);

        button_9 = new QToolButton(centralwidget);
        button_9.setObjectName("button_9");
        QSizePolicy sizePolicy3 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy3.setHorizontalStretch((byte)0);
        sizePolicy3.setVerticalStretch((byte)0);
        sizePolicy3.setHeightForWidth(button_9.sizePolicy().hasHeightForWidth());
        button_9.setSizePolicy(sizePolicy3);
        QFont font3 = new QFont();
        font3.setPointSize(20);
        button_9.setFont(font3);

        gridLayout1.addWidget(button_9, 1, 2, 1, 1);

        button_comma = new QToolButton(centralwidget);
        button_comma.setObjectName("button_comma");
        QSizePolicy sizePolicy4 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(5), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(5));
        sizePolicy4.setHorizontalStretch((byte)0);
        sizePolicy4.setVerticalStretch((byte)0);
        sizePolicy4.setHeightForWidth(button_comma.sizePolicy().hasHeightForWidth());
        button_comma.setSizePolicy(sizePolicy4);
        QFont font4 = new QFont();
        font4.setPointSize(20);
        button_comma.setFont(font4);

        gridLayout1.addWidget(button_comma, 4, 2, 1, 1);

        button_multiply = new QToolButton(centralwidget);
        button_multiply.setObjectName("button_multiply");
        QSizePolicy sizePolicy5 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy5.setHorizontalStretch((byte)0);
        sizePolicy5.setVerticalStretch((byte)0);
        sizePolicy5.setHeightForWidth(button_multiply.sizePolicy().hasHeightForWidth());
        button_multiply.setSizePolicy(sizePolicy5);
        QFont font5 = new QFont();
        font5.setPointSize(20);
        button_multiply.setFont(font5);

        gridLayout1.addWidget(button_multiply, 0, 3, 1, 1);

        button_devide = new QToolButton(centralwidget);
        button_devide.setObjectName("button_devide");
        QSizePolicy sizePolicy6 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy6.setHorizontalStretch((byte)0);
        sizePolicy6.setVerticalStretch((byte)0);
        sizePolicy6.setHeightForWidth(button_devide.sizePolicy().hasHeightForWidth());
        button_devide.setSizePolicy(sizePolicy6);
        QFont font6 = new QFont();
        font6.setPointSize(20);
        button_devide.setFont(font6);

        gridLayout1.addWidget(button_devide, 0, 2, 1, 1);

        button_3 = new QToolButton(centralwidget);
        button_3.setObjectName("button_3");
        QSizePolicy sizePolicy7 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy7.setHorizontalStretch((byte)0);
        sizePolicy7.setVerticalStretch((byte)0);
        sizePolicy7.setHeightForWidth(button_3.sizePolicy().hasHeightForWidth());
        button_3.setSizePolicy(sizePolicy7);
        QFont font7 = new QFont();
        font7.setPointSize(20);
        button_3.setFont(font7);

        gridLayout1.addWidget(button_3, 3, 2, 1, 1);

        button_subtract = new QToolButton(centralwidget);
        button_subtract.setObjectName("button_subtract");
        QSizePolicy sizePolicy8 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy8.setHorizontalStretch((byte)0);
        sizePolicy8.setVerticalStretch((byte)0);
        sizePolicy8.setHeightForWidth(button_subtract.sizePolicy().hasHeightForWidth());
        button_subtract.setSizePolicy(sizePolicy8);
        QFont font8 = new QFont();
        font8.setPointSize(20);
        button_subtract.setFont(font8);

        gridLayout1.addWidget(button_subtract, 1, 3, 1, 1);

        button_add = new QToolButton(centralwidget);
        button_add.setObjectName("button_add");
        QSizePolicy sizePolicy9 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy9.setHorizontalStretch((byte)0);
        sizePolicy9.setVerticalStretch((byte)0);
        sizePolicy9.setHeightForWidth(button_add.sizePolicy().hasHeightForWidth());
        button_add.setSizePolicy(sizePolicy9);
        QFont font9 = new QFont();
        font9.setPointSize(20);
        button_add.setFont(font9);

        gridLayout1.addWidget(button_add, 2, 3, 1, 1);

        button_7 = new QToolButton(centralwidget);
        button_7.setObjectName("button_7");
        QSizePolicy sizePolicy10 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy10.setHorizontalStretch((byte)0);
        sizePolicy10.setVerticalStretch((byte)0);
        sizePolicy10.setHeightForWidth(button_7.sizePolicy().hasHeightForWidth());
        button_7.setSizePolicy(sizePolicy10);
        QFont font10 = new QFont();
        font10.setPointSize(20);
        button_7.setFont(font10);

        gridLayout1.addWidget(button_7, 1, 0, 1, 1);

        button_4 = new QToolButton(centralwidget);
        button_4.setObjectName("button_4");
        QSizePolicy sizePolicy11 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy11.setHorizontalStretch((byte)0);
        sizePolicy11.setVerticalStretch((byte)0);
        sizePolicy11.setHeightForWidth(button_4.sizePolicy().hasHeightForWidth());
        button_4.setSizePolicy(sizePolicy11);
        QFont font11 = new QFont();
        font11.setPointSize(20);
        button_4.setFont(font11);

        gridLayout1.addWidget(button_4, 2, 0, 1, 1);

        button_1 = new QToolButton(centralwidget);
        button_1.setObjectName("button_1");
        QSizePolicy sizePolicy12 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy12.setHorizontalStretch((byte)0);
        sizePolicy12.setVerticalStretch((byte)0);
        sizePolicy12.setHeightForWidth(button_1.sizePolicy().hasHeightForWidth());
        button_1.setSizePolicy(sizePolicy12);
        QFont font12 = new QFont();
        font12.setPointSize(20);
        button_1.setFont(font12);

        gridLayout1.addWidget(button_1, 3, 0, 1, 1);

        button_right = new QToolButton(centralwidget);
        button_right.setObjectName("button_right");
        QSizePolicy sizePolicy13 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy13.setHorizontalStretch((byte)0);
        sizePolicy13.setVerticalStretch((byte)0);
        sizePolicy13.setHeightForWidth(button_right.sizePolicy().hasHeightForWidth());
        button_right.setSizePolicy(sizePolicy13);
        QFont font13 = new QFont();
        font13.setPointSize(20);
        button_right.setFont(font13);

        gridLayout1.addWidget(button_right, 0, 1, 1, 1);

        button_0 = new QToolButton(centralwidget);
        button_0.setObjectName("button_0");
        QSizePolicy sizePolicy14 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy14.setHorizontalStretch((byte)0);
        sizePolicy14.setVerticalStretch((byte)0);
        sizePolicy14.setHeightForWidth(button_0.sizePolicy().hasHeightForWidth());
        button_0.setSizePolicy(sizePolicy14);
        QFont font14 = new QFont();
        font14.setPointSize(20);
        button_0.setFont(font14);

        gridLayout1.addWidget(button_0, 4, 0, 1, 2);

        button_5 = new QToolButton(centralwidget);
        button_5.setObjectName("button_5");
        QSizePolicy sizePolicy15 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy15.setHorizontalStretch((byte)0);
        sizePolicy15.setVerticalStretch((byte)0);
        sizePolicy15.setHeightForWidth(button_5.sizePolicy().hasHeightForWidth());
        button_5.setSizePolicy(sizePolicy15);
        QFont font15 = new QFont();
        font15.setPointSize(20);
        button_5.setFont(font15);

        gridLayout1.addWidget(button_5, 2, 1, 1, 1);

        button_left = new QToolButton(centralwidget);
        button_left.setObjectName("button_left");
        QSizePolicy sizePolicy16 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy16.setHorizontalStretch((byte)0);
        sizePolicy16.setVerticalStretch((byte)0);
        sizePolicy16.setHeightForWidth(button_left.sizePolicy().hasHeightForWidth());
        button_left.setSizePolicy(sizePolicy16);
        QFont font16 = new QFont();
        font16.setPointSize(20);
        button_left.setFont(font16);

        gridLayout1.addWidget(button_left, 0, 0, 1, 1);

        button_8 = new QToolButton(centralwidget);
        button_8.setObjectName("button_8");
        QSizePolicy sizePolicy17 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1));
        sizePolicy17.setHorizontalStretch((byte)0);
        sizePolicy17.setVerticalStretch((byte)0);
        sizePolicy17.setHeightForWidth(button_8.sizePolicy().hasHeightForWidth());
        button_8.setSizePolicy(sizePolicy17);
        QFont font17 = new QFont();
        font17.setPointSize(20);
        button_8.setFont(font17);

        gridLayout1.addWidget(button_8, 1, 1, 1, 1);

        textBrowser = new QTextBrowser(centralwidget);
        textBrowser.setObjectName("textBrowser");
        textBrowser.setEnabled(true);
        textBrowser.setLineWrapMode(com.trolltech.qt.gui.QTextEdit.LineWrapMode.NoWrap);

        gridLayout1.addWidget(textBrowser, 5, 0, 1, 4);


        gridLayout.addLayout(gridLayout1, 0, 0, 1, 1);

        hboxLayout = new QHBoxLayout();
        hboxLayout.setSpacing(6);
        hboxLayout.setMargin(0);
        hboxLayout.setObjectName("hboxLayout");
        lineEdit = new QLineEdit(centralwidget);
        lineEdit.setObjectName("lineEdit");
        lineEdit.setEnabled(false);
        QSizePolicy sizePolicy18 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(7), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(0));
        sizePolicy18.setHorizontalStretch((byte)5);
        sizePolicy18.setVerticalStretch((byte)0);
        sizePolicy18.setHeightForWidth(lineEdit.sizePolicy().hasHeightForWidth());
        lineEdit.setSizePolicy(sizePolicy18);
        QFont font18 = new QFont();
        font18.setPointSize(20);
        lineEdit.setFont(font18);
        lineEdit.setReadOnly(true);

        hboxLayout.addWidget(lineEdit);

        button_clear = new QToolButton(centralwidget);
        button_clear.setObjectName("button_clear");
        QFont font19 = new QFont();
        font19.setPointSize(20);
        button_clear.setFont(font19);

        hboxLayout.addWidget(button_clear);


        gridLayout.addLayout(hboxLayout, 1, 0, 1, 1);

        CalculatorSimple.setCentralWidget(centralwidget);
        menubar = new QMenuBar(CalculatorSimple);
        menubar.setObjectName("menubar");
        menubar.setGeometry(new QRect(0, 0, 327, 19));
        CalculatorSimple.setMenuBar(menubar);
        statusbar = new QStatusBar(CalculatorSimple);
        statusbar.setObjectName("statusbar");
        CalculatorSimple.setStatusBar(statusbar);
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
        QWidget.setTabOrder(button_comma, button_equal);
        QWidget.setTabOrder(button_equal, textBrowser);
        QWidget.setTabOrder(textBrowser, lineEdit);
        QWidget.setTabOrder(lineEdit, button_clear);
        retranslateUi(CalculatorSimple);
        lineEdit.returnPressed.connect(button_equal, "click()");
        button_clear.pressed.connect(lineEdit, "clear()");

        CalculatorSimple.connectSlotsByName();
    } // setupUi

    void retranslateUi(QMainWindow CalculatorSimple)
    {
        CalculatorSimple.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("CalculatorSimple", "MainWindow"));
        button_6.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorSimple", "6"));
        button_2.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorSimple", "2"));
        button_equal.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorSimple", "="));
        button_9.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorSimple", "9"));
        button_comma.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorSimple", "."));
        button_multiply.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorSimple", "*"));
        button_devide.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorSimple", "/"));
        button_3.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorSimple", "3"));
        button_subtract.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorSimple", "-"));
        button_add.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorSimple", "+"));
        button_7.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorSimple", "7"));
        button_4.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorSimple", "4"));
        button_1.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorSimple", "1"));
        button_right.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorSimple", ")"));
        button_0.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorSimple", "0"));
        button_5.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorSimple", "5"));
        button_left.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorSimple", "("));
        button_8.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorSimple", "8"));
        button_clear.setText(com.trolltech.qt.core.QCoreApplication.translate("CalculatorSimple", "c"));
    } // retranslateUi

}

