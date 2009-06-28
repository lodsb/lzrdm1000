/********************************************************************************
** Form generated from reading ui file 'backside.jui'
**
** Created: Tue Mar 10 10:27:05 2009
**      by: Qt User Interface Compiler version 4.5.0
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package com.trolltech.examples;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_BackSide implements com.trolltech.qt.QUiForm<QWidget>
{
    public QVBoxLayout verticalLayout_2;
    public QGroupBox groupBox;
    public QGridLayout gridLayout;
    public QLabel label;
    public QLineEdit hostName;
    public QLabel label_2;
    public QLabel label_3;
    public QHBoxLayout horizontalLayout;
    public QSlider horizontalSlider;
    public QSpinBox spinBox;
    public QDateTimeEdit dateTimeEdit;
    public QGroupBox groupBox_2;
    public QHBoxLayout horizontalLayout_2;
    public QTreeWidget treeWidget;

    public Ui_BackSide() { super(); }

    public void setupUi(QWidget BackSide)
    {
        BackSide.setObjectName("BackSide");
        BackSide.resize(new QSize(378, 385).expandedTo(BackSide.minimumSizeHint()));
        verticalLayout_2 = new QVBoxLayout(BackSide);
        verticalLayout_2.setObjectName("verticalLayout_2");
        groupBox = new QGroupBox(BackSide);
        groupBox.setObjectName("groupBox");
        groupBox.setFlat(true);
        groupBox.setCheckable(true);
        gridLayout = new QGridLayout(groupBox);
        gridLayout.setObjectName("gridLayout");
        label = new QLabel(groupBox);
        label.setObjectName("label");

        gridLayout.addWidget(label, 0, 0, 1, 1);

        hostName = new QLineEdit(groupBox);
        hostName.setObjectName("hostName");

        gridLayout.addWidget(hostName, 0, 1, 1, 1);

        label_2 = new QLabel(groupBox);
        label_2.setObjectName("label_2");

        gridLayout.addWidget(label_2, 1, 0, 1, 1);

        label_3 = new QLabel(groupBox);
        label_3.setObjectName("label_3");

        gridLayout.addWidget(label_3, 2, 0, 1, 1);

        horizontalLayout = new QHBoxLayout();
        horizontalLayout.setObjectName("horizontalLayout");
        horizontalSlider = new QSlider(groupBox);
        horizontalSlider.setObjectName("horizontalSlider");
        horizontalSlider.setValue(42);
        horizontalSlider.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);

        horizontalLayout.addWidget(horizontalSlider);

        spinBox = new QSpinBox(groupBox);
        spinBox.setObjectName("spinBox");
        spinBox.setValue(42);

        horizontalLayout.addWidget(spinBox);


        gridLayout.addLayout(horizontalLayout, 2, 1, 1, 1);

        dateTimeEdit = new QDateTimeEdit(groupBox);
        dateTimeEdit.setObjectName("dateTimeEdit");

        gridLayout.addWidget(dateTimeEdit, 1, 1, 1, 1);


        verticalLayout_2.addWidget(groupBox);

        groupBox_2 = new QGroupBox(BackSide);
        groupBox_2.setObjectName("groupBox_2");
        groupBox_2.setFlat(true);
        groupBox_2.setCheckable(true);
        horizontalLayout_2 = new QHBoxLayout(groupBox_2);
        horizontalLayout_2.setObjectName("horizontalLayout_2");
        treeWidget = new QTreeWidget(groupBox_2);
        treeWidget.setObjectName("treeWidget");

        horizontalLayout_2.addWidget(treeWidget);


        verticalLayout_2.addWidget(groupBox_2);

        QWidget.setTabOrder(groupBox, hostName);
        QWidget.setTabOrder(hostName, dateTimeEdit);
        QWidget.setTabOrder(dateTimeEdit, horizontalSlider);
        QWidget.setTabOrder(horizontalSlider, spinBox);
        QWidget.setTabOrder(spinBox, groupBox_2);
        QWidget.setTabOrder(groupBox_2, treeWidget);
        retranslateUi(BackSide);
        horizontalSlider.valueChanged.connect(spinBox, "setValue(int)");
        spinBox.valueChanged.connect(horizontalSlider, "setValue(int)");

        BackSide.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget BackSide)
    {
        BackSide.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("BackSide", "BackSide", null));
        groupBox.setTitle(com.trolltech.qt.core.QCoreApplication.translate("BackSide", "Settings", null));
        label.setText(com.trolltech.qt.core.QCoreApplication.translate("BackSide", "Title:", null));
        hostName.setText(com.trolltech.qt.core.QCoreApplication.translate("BackSide", "Pad Navigator Example", null));
        label_2.setText(com.trolltech.qt.core.QCoreApplication.translate("BackSide", "Modified:", null));
        label_3.setText(com.trolltech.qt.core.QCoreApplication.translate("BackSide", "Extent", null));
        groupBox_2.setTitle(com.trolltech.qt.core.QCoreApplication.translate("BackSide", "Other input", null));
        treeWidget.headerItem().setText(0, com.trolltech.qt.core.QCoreApplication.translate("BackSide", "Widgets On Graphics View", null));
        treeWidget.clear();

        QTreeWidgetItem __item = new QTreeWidgetItem(treeWidget);
        __item.setText(0, com.trolltech.qt.core.QCoreApplication.translate("BackSide", "QGraphicsProxyWidget", null));

        QTreeWidgetItem __item1 = new QTreeWidgetItem(__item);
        __item1.setText(0, com.trolltech.qt.core.QCoreApplication.translate("BackSide", "QGraphicsWidget", null));

        QTreeWidgetItem __item2 = new QTreeWidgetItem(__item1);
        __item2.setText(0, com.trolltech.qt.core.QCoreApplication.translate("BackSide", "QObject", null));

        QTreeWidgetItem __item3 = new QTreeWidgetItem(__item1);
        __item3.setText(0, com.trolltech.qt.core.QCoreApplication.translate("BackSide", "QGraphicsItem", null));

        QTreeWidgetItem __item4 = new QTreeWidgetItem(__item1);
        __item4.setText(0, com.trolltech.qt.core.QCoreApplication.translate("BackSide", "QGraphicsLayoutItem", null));

        QTreeWidgetItem __item5 = new QTreeWidgetItem(treeWidget);
        __item5.setText(0, com.trolltech.qt.core.QCoreApplication.translate("BackSide", "QGraphicsGridLayout", null));

        QTreeWidgetItem __item6 = new QTreeWidgetItem(__item5);
        __item6.setText(0, com.trolltech.qt.core.QCoreApplication.translate("BackSide", "QGraphicsLayout", null));

        QTreeWidgetItem __item7 = new QTreeWidgetItem(__item6);
        __item7.setText(0, com.trolltech.qt.core.QCoreApplication.translate("BackSide", "QGraphicsLayoutItem", null));

        QTreeWidgetItem __item8 = new QTreeWidgetItem(treeWidget);
        __item8.setText(0, com.trolltech.qt.core.QCoreApplication.translate("BackSide", "QGraphicsLinearLayout", null));

        QTreeWidgetItem __item9 = new QTreeWidgetItem(__item8);
        __item9.setText(0, com.trolltech.qt.core.QCoreApplication.translate("BackSide", "QGraphicsLayout", null));

        QTreeWidgetItem __item10 = new QTreeWidgetItem(__item9);
        __item10.setText(0, com.trolltech.qt.core.QCoreApplication.translate("BackSide", "QGraphicsLayoutItem", null));
    } // retranslateUi

}

