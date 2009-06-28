/********************************************************************************
** Form generated from reading ui file 'CustomWidget.jui'
**
** Created: Tue Mar 10 10:27:05 2009
**      by: Qt User Interface Compiler version 4.5.0
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package com.trolltech.examples;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;


public class Ui_CustomWidget implements com.trolltech.qt.QUiForm<QWidget>
{
    public QGridLayout gridLayout;
    public com.trolltech.examples.CustomWidget customWidget;
    public QSlider verticalSlider;
    public QSlider verticalSlider_2;

    public Ui_CustomWidget() { super(); }

    public void setupUi(QWidget CustomWidget)
    {
        CustomWidget.setObjectName("CustomWidget");
        CustomWidget.resize(new QSize(511, 805).expandedTo(CustomWidget.minimumSizeHint()));
        gridLayout = new QGridLayout(CustomWidget);
        gridLayout.setObjectName("gridLayout");
        customWidget = new com.trolltech.examples.CustomWidget(CustomWidget);
        customWidget.setObjectName("customWidget");
        customWidget.setProperty("needleColor", new QColor(255, 0, 0, 128));

        gridLayout.addWidget(customWidget, 0, 1, 1, 1);

        verticalSlider = new QSlider(CustomWidget);
        verticalSlider.setObjectName("verticalSlider");
        verticalSlider.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);
        verticalSlider.setMaximum(260);
        verticalSlider.setOrientation(com.trolltech.qt.core.Qt.Orientation.Vertical);

        gridLayout.addWidget(verticalSlider, 0, 2, 1, 1);

        verticalSlider_2 = new QSlider(CustomWidget);
        verticalSlider_2.setObjectName("verticalSlider_2");
        verticalSlider_2.setEnabled(false);
        verticalSlider_2.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);
        verticalSlider_2.setMaximum(260);
        verticalSlider_2.setOrientation(com.trolltech.qt.core.Qt.Orientation.Vertical);
        verticalSlider_2.setInvertedAppearance(true);

        gridLayout.addWidget(verticalSlider_2, 0, 0, 1, 1);

        retranslateUi(CustomWidget);
        verticalSlider.valueChanged.connect(customWidget, "setCurrentSpeed(int)");
        customWidget.speedChanged.connect(verticalSlider_2, "setValue(int)");

        CustomWidget.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget CustomWidget)
    {
        CustomWidget.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("CustomWidget", "Form", null));
    } // retranslateUi

}

