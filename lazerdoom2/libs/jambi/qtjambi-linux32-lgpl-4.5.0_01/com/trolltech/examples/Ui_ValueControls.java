/********************************************************************************
** Form generated from reading ui file 'valuecontrols.jui'
**
** Created: Tue Mar 10 10:27:05 2009
**      by: Qt User Interface Compiler version 4.5.0
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package com.trolltech.examples;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_ValueControls implements com.trolltech.qt.QUiForm<QWidget>
{
    public QVBoxLayout verticalLayout;
    public QWidget ColorizeControlWidget;
    public QFormLayout formLayout;
    public QLabel label;
    public QSlider colorizeRedSlider;
    public QLabel label_2;
    public QSlider colorizeGreenSlider;
    public QLabel label_3;
    public QSlider colorizeBlueSlider;
    public QWidget DropShadowControlWidget;
    public QFormLayout formLayout_2;
    public QLabel label_4;
    public QSlider dropShadowRedSlider;
    public QLabel label_5;
    public QSlider dropShadowGreenSlider;
    public QLabel label_6;
    public QSlider dropShadowBlueSlider;
    public QLabel label_7;
    public QSlider dropShadowXSlider;
    public QLabel label_8;
    public QSlider dropShadowYSlider;
    public QLabel label_9;
    public QSlider dropShadowRadiusSlider;
    public QLabel label_10;
    public QSlider dropShadowAlphaSlider;
    public QWidget ConvolutionControlWidget;
    public QGridLayout gridLayout;
    public QLineEdit kernel_1x1;
    public QLineEdit kernel_2x1;
    public QLineEdit kernel_3x1;
    public QLineEdit kernel_1x2;
    public QLineEdit kernel_2x2;
    public QLineEdit kernel_3x2;
    public QLineEdit kernel_1x3;
    public QLineEdit kernel_2x3;
    public QLineEdit kernel_3x3;

    public Ui_ValueControls() { super(); }

    public void setupUi(QWidget ValueControls)
    {
        ValueControls.setObjectName("ValueControls");
        ValueControls.setEnabled(true);
        ValueControls.resize(new QSize(321, 420).expandedTo(ValueControls.minimumSizeHint()));
        QSizePolicy sizePolicy = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Preferred, com.trolltech.qt.gui.QSizePolicy.Policy.Preferred);
        sizePolicy.setHorizontalStretch((byte)0);
        sizePolicy.setVerticalStretch((byte)0);
        sizePolicy.setHeightForWidth(ValueControls.sizePolicy().hasHeightForWidth());
        ValueControls.setSizePolicy(sizePolicy);
        verticalLayout = new QVBoxLayout(ValueControls);
        verticalLayout.setObjectName("verticalLayout");
        ColorizeControlWidget = new QWidget(ValueControls);
        ColorizeControlWidget.setObjectName("ColorizeControlWidget");
        formLayout = new QFormLayout(ColorizeControlWidget);
        formLayout.setObjectName("formLayout");
        label = new QLabel(ColorizeControlWidget);
        label.setObjectName("label");

        formLayout.addWidget(label);

        colorizeRedSlider = new QSlider(ColorizeControlWidget);
        colorizeRedSlider.setObjectName("colorizeRedSlider");
        colorizeRedSlider.setValue(64);
        colorizeRedSlider.setMaximum(255);
        colorizeRedSlider.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);

        formLayout.addWidget(colorizeRedSlider);

        label_2 = new QLabel(ColorizeControlWidget);
        label_2.setObjectName("label_2");

        formLayout.addWidget(label_2);

        colorizeGreenSlider = new QSlider(ColorizeControlWidget);
        colorizeGreenSlider.setObjectName("colorizeGreenSlider");
        colorizeGreenSlider.setValue(32);
        colorizeGreenSlider.setMaximum(255);
        colorizeGreenSlider.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);

        formLayout.addWidget(colorizeGreenSlider);

        label_3 = new QLabel(ColorizeControlWidget);
        label_3.setObjectName("label_3");

        formLayout.addWidget(label_3);

        colorizeBlueSlider = new QSlider(ColorizeControlWidget);
        colorizeBlueSlider.setObjectName("colorizeBlueSlider");
        colorizeBlueSlider.setValue(99);
        colorizeBlueSlider.setMaximum(255);
        colorizeBlueSlider.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);

        formLayout.addWidget(colorizeBlueSlider);


        verticalLayout.addWidget(ColorizeControlWidget);

        DropShadowControlWidget = new QWidget(ValueControls);
        DropShadowControlWidget.setObjectName("DropShadowControlWidget");
        formLayout_2 = new QFormLayout(DropShadowControlWidget);
        formLayout_2.setObjectName("formLayout_2");
        formLayout_2.setFieldGrowthPolicy(com.trolltech.qt.gui.QFormLayout.FieldGrowthPolicy.ExpandingFieldsGrow);
        label_4 = new QLabel(DropShadowControlWidget);
        label_4.setObjectName("label_4");

        formLayout_2.addWidget(label_4);

        dropShadowRedSlider = new QSlider(DropShadowControlWidget);
        dropShadowRedSlider.setObjectName("dropShadowRedSlider");
        dropShadowRedSlider.setMaximum(255);
        dropShadowRedSlider.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);

        formLayout_2.addWidget(dropShadowRedSlider);

        label_5 = new QLabel(DropShadowControlWidget);
        label_5.setObjectName("label_5");

        formLayout_2.addWidget(label_5);

        dropShadowGreenSlider = new QSlider(DropShadowControlWidget);
        dropShadowGreenSlider.setObjectName("dropShadowGreenSlider");
        dropShadowGreenSlider.setMaximum(255);
        dropShadowGreenSlider.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);

        formLayout_2.addWidget(dropShadowGreenSlider);

        label_6 = new QLabel(DropShadowControlWidget);
        label_6.setObjectName("label_6");

        formLayout_2.addWidget(label_6);

        dropShadowBlueSlider = new QSlider(DropShadowControlWidget);
        dropShadowBlueSlider.setObjectName("dropShadowBlueSlider");
        dropShadowBlueSlider.setMaximum(255);
        dropShadowBlueSlider.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);

        formLayout_2.addWidget(dropShadowBlueSlider);

        label_7 = new QLabel(DropShadowControlWidget);
        label_7.setObjectName("label_7");

        formLayout_2.addWidget(label_7);

        dropShadowXSlider = new QSlider(DropShadowControlWidget);
        dropShadowXSlider.setObjectName("dropShadowXSlider");
        dropShadowXSlider.setMaximum(999);
        dropShadowXSlider.setValue(600);
        dropShadowXSlider.setMinimum(0);
        dropShadowXSlider.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);

        formLayout_2.addWidget(dropShadowXSlider);

        label_8 = new QLabel(DropShadowControlWidget);
        label_8.setObjectName("label_8");

        formLayout_2.addWidget(label_8);

        dropShadowYSlider = new QSlider(DropShadowControlWidget);
        dropShadowYSlider.setObjectName("dropShadowYSlider");
        dropShadowYSlider.setValue(99);
        dropShadowYSlider.setMaximum(999);
        dropShadowYSlider.setValue(600);
        dropShadowYSlider.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);

        formLayout_2.addWidget(dropShadowYSlider);

        label_9 = new QLabel(DropShadowControlWidget);
        label_9.setObjectName("label_9");

        formLayout_2.addWidget(label_9);

        dropShadowRadiusSlider = new QSlider(DropShadowControlWidget);
        dropShadowRadiusSlider.setObjectName("dropShadowRadiusSlider");
        dropShadowRadiusSlider.setMaximum(299);
        dropShadowRadiusSlider.setValue(30);
        dropShadowRadiusSlider.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);

        formLayout_2.addWidget(dropShadowRadiusSlider);

        label_10 = new QLabel(DropShadowControlWidget);
        label_10.setObjectName("label_10");

        formLayout_2.addWidget(label_10);

        dropShadowAlphaSlider = new QSlider(DropShadowControlWidget);
        dropShadowAlphaSlider.setObjectName("dropShadowAlphaSlider");
        dropShadowAlphaSlider.setValue(99);
        dropShadowAlphaSlider.setMaximum(255);
        dropShadowAlphaSlider.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);

        formLayout_2.addWidget(dropShadowAlphaSlider);


        verticalLayout.addWidget(DropShadowControlWidget);

        ConvolutionControlWidget = new QWidget(ValueControls);
        ConvolutionControlWidget.setObjectName("ConvolutionControlWidget");
        gridLayout = new QGridLayout(ConvolutionControlWidget);
        gridLayout.setObjectName("gridLayout");
        kernel_1x1 = new QLineEdit(ConvolutionControlWidget);
        kernel_1x1.setObjectName("kernel_1x1");

        gridLayout.addWidget(kernel_1x1, 0, 0, 1, 1);

        kernel_2x1 = new QLineEdit(ConvolutionControlWidget);
        kernel_2x1.setObjectName("kernel_2x1");

        gridLayout.addWidget(kernel_2x1, 0, 1, 1, 1);

        kernel_3x1 = new QLineEdit(ConvolutionControlWidget);
        kernel_3x1.setObjectName("kernel_3x1");

        gridLayout.addWidget(kernel_3x1, 0, 2, 1, 1);

        kernel_1x2 = new QLineEdit(ConvolutionControlWidget);
        kernel_1x2.setObjectName("kernel_1x2");

        gridLayout.addWidget(kernel_1x2, 1, 0, 1, 1);

        kernel_2x2 = new QLineEdit(ConvolutionControlWidget);
        kernel_2x2.setObjectName("kernel_2x2");

        gridLayout.addWidget(kernel_2x2, 1, 1, 1, 1);

        kernel_3x2 = new QLineEdit(ConvolutionControlWidget);
        kernel_3x2.setObjectName("kernel_3x2");

        gridLayout.addWidget(kernel_3x2, 1, 2, 1, 1);

        kernel_1x3 = new QLineEdit(ConvolutionControlWidget);
        kernel_1x3.setObjectName("kernel_1x3");

        gridLayout.addWidget(kernel_1x3, 2, 0, 1, 1);

        kernel_2x3 = new QLineEdit(ConvolutionControlWidget);
        kernel_2x3.setObjectName("kernel_2x3");

        gridLayout.addWidget(kernel_2x3, 2, 1, 1, 1);

        kernel_3x3 = new QLineEdit(ConvolutionControlWidget);
        kernel_3x3.setObjectName("kernel_3x3");

        gridLayout.addWidget(kernel_3x3, 2, 2, 1, 1);


        verticalLayout.addWidget(ConvolutionControlWidget);

        retranslateUi(ValueControls);

        ValueControls.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget ValueControls)
    {
        ValueControls.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("ValueControls", "Form", null));
        label.setText(com.trolltech.qt.core.QCoreApplication.translate("ValueControls", "Red", null));
        label_2.setText(com.trolltech.qt.core.QCoreApplication.translate("ValueControls", "Green", null));
        label_3.setText(com.trolltech.qt.core.QCoreApplication.translate("ValueControls", "Blue", null));
        label_4.setText(com.trolltech.qt.core.QCoreApplication.translate("ValueControls", "Red", null));
        label_5.setText(com.trolltech.qt.core.QCoreApplication.translate("ValueControls", "Green", null));
        label_6.setText(com.trolltech.qt.core.QCoreApplication.translate("ValueControls", "Blue", null));
        label_7.setText(com.trolltech.qt.core.QCoreApplication.translate("ValueControls", "X Distance", null));
        label_8.setText(com.trolltech.qt.core.QCoreApplication.translate("ValueControls", "Y Distance", null));
        label_9.setText(com.trolltech.qt.core.QCoreApplication.translate("ValueControls", "Blur Radius", null));
        label_10.setText(com.trolltech.qt.core.QCoreApplication.translate("ValueControls", "Alpha", null));
        kernel_1x1.setText(com.trolltech.qt.core.QCoreApplication.translate("ValueControls", "0.1", null));
        kernel_2x1.setText(com.trolltech.qt.core.QCoreApplication.translate("ValueControls", "0.1", null));
        kernel_3x1.setText(com.trolltech.qt.core.QCoreApplication.translate("ValueControls", "0.1", null));
        kernel_1x2.setText(com.trolltech.qt.core.QCoreApplication.translate("ValueControls", "0.1", null));
        kernel_2x2.setText(com.trolltech.qt.core.QCoreApplication.translate("ValueControls", "0.2", null));
        kernel_3x2.setText(com.trolltech.qt.core.QCoreApplication.translate("ValueControls", "0.1", null));
        kernel_1x3.setText(com.trolltech.qt.core.QCoreApplication.translate("ValueControls", "0.1", null));
        kernel_2x3.setText(com.trolltech.qt.core.QCoreApplication.translate("ValueControls", "0.1", null));
        kernel_3x3.setText(com.trolltech.qt.core.QCoreApplication.translate("ValueControls", "0.1", null));
    } // retranslateUi

}

