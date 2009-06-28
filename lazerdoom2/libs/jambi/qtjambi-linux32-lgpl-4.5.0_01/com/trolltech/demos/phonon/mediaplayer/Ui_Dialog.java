/********************************************************************************
** Form generated from reading ui file 'settings.jui'
**
** Created: Tue Mar 10 10:27:05 2009
**      by: Qt User Interface Compiler version 4.5.0
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package com.trolltech.demos.phonon.mediaplayer;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_Dialog implements com.trolltech.qt.QUiForm<QDialog>
{
    public QDialogButtonBox buttonBox;
    public QWidget widget;
    public QVBoxLayout vboxLayout;
    public QHBoxLayout hboxLayout;
    public QLabel label;
    public QComboBox deviceCombo;
    public QHBoxLayout hboxLayout1;
    public QLabel label_2;
    public QVBoxLayout vboxLayout1;
    public QSlider crossFadeSlider;
    public QHBoxLayout hboxLayout2;
    public QLabel label_3;
    public QSpacerItem spacerItem;
    public QLabel label_5;
    public QSpacerItem spacerItem1;
    public QLabel label_4;
    public QHBoxLayout hboxLayout3;
    public QLabel label_6;
    public QComboBox audioEffectsCombo;

    public Ui_Dialog() { super(); }

    public void setupUi(QDialog Dialog)
    {
        Dialog.setObjectName("Dialog");
        Dialog.resize(new QSize(400, 300).expandedTo(Dialog.minimumSizeHint()));
        buttonBox = new QDialogButtonBox(Dialog);
        buttonBox.setObjectName("buttonBox");
        buttonBox.setGeometry(new QRect(30, 240, 341, 32));
        buttonBox.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);
        buttonBox.setStandardButtons(com.trolltech.qt.gui.QDialogButtonBox.StandardButton.createQFlags(com.trolltech.qt.gui.QDialogButtonBox.StandardButton.Cancel,com.trolltech.qt.gui.QDialogButtonBox.StandardButton.NoButton,com.trolltech.qt.gui.QDialogButtonBox.StandardButton.Ok));
        widget = new QWidget(Dialog);
        widget.setObjectName("widget");
        widget.setGeometry(new QRect(20, 21, 361, 136));
        vboxLayout = new QVBoxLayout(widget);
        vboxLayout.setObjectName("vboxLayout");
        hboxLayout = new QHBoxLayout();
        hboxLayout.setObjectName("hboxLayout");
        label = new QLabel(widget);
        label.setObjectName("label");
        QSizePolicy sizePolicy = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Maximum, com.trolltech.qt.gui.QSizePolicy.Policy.Preferred);
        sizePolicy.setHorizontalStretch((byte)0);
        sizePolicy.setVerticalStretch((byte)0);
        sizePolicy.setHeightForWidth(label.sizePolicy().hasHeightForWidth());
        label.setSizePolicy(sizePolicy);

        hboxLayout.addWidget(label);

        deviceCombo = new QComboBox(widget);
        deviceCombo.setObjectName("deviceCombo");
        QSizePolicy sizePolicy1 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Fixed);
        sizePolicy1.setHorizontalStretch((byte)0);
        sizePolicy1.setVerticalStretch((byte)0);
        sizePolicy1.setHeightForWidth(deviceCombo.sizePolicy().hasHeightForWidth());
        deviceCombo.setSizePolicy(sizePolicy1);

        hboxLayout.addWidget(deviceCombo);


        vboxLayout.addLayout(hboxLayout);

        hboxLayout1 = new QHBoxLayout();
        hboxLayout1.setObjectName("hboxLayout1");
        label_2 = new QLabel(widget);
        label_2.setObjectName("label_2");
        QSizePolicy sizePolicy2 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Maximum, com.trolltech.qt.gui.QSizePolicy.Policy.Preferred);
        sizePolicy2.setHorizontalStretch((byte)0);
        sizePolicy2.setVerticalStretch((byte)0);
        sizePolicy2.setHeightForWidth(label_2.sizePolicy().hasHeightForWidth());
        label_2.setSizePolicy(sizePolicy2);

        hboxLayout1.addWidget(label_2);

        vboxLayout1 = new QVBoxLayout();
        vboxLayout1.setObjectName("vboxLayout1");
        crossFadeSlider = new QSlider(widget);
        crossFadeSlider.setObjectName("crossFadeSlider");
        QSizePolicy sizePolicy3 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Fixed);
        sizePolicy3.setHorizontalStretch((byte)0);
        sizePolicy3.setVerticalStretch((byte)0);
        sizePolicy3.setHeightForWidth(crossFadeSlider.sizePolicy().hasHeightForWidth());
        crossFadeSlider.setSizePolicy(sizePolicy3);
        crossFadeSlider.setMinimum(-20);
        crossFadeSlider.setMaximum(20);
        crossFadeSlider.setSingleStep(1);
        crossFadeSlider.setPageStep(2);
        crossFadeSlider.setValue(0);
        crossFadeSlider.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);
        crossFadeSlider.setTickPosition(com.trolltech.qt.gui.QSlider.TickPosition.TicksBelow);

        vboxLayout1.addWidget(crossFadeSlider);

        hboxLayout2 = new QHBoxLayout();
        hboxLayout2.setObjectName("hboxLayout2");
        label_3 = new QLabel(widget);
        label_3.setObjectName("label_3");
        QFont font = new QFont();
        font.setPointSize(9);
        label_3.setFont(font);

        hboxLayout2.addWidget(label_3);

        spacerItem = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        hboxLayout2.addItem(spacerItem);

        label_5 = new QLabel(widget);
        label_5.setObjectName("label_5");
        QFont font1 = new QFont();
        font1.setPointSize(9);
        label_5.setFont(font1);

        hboxLayout2.addWidget(label_5);

        spacerItem1 = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        hboxLayout2.addItem(spacerItem1);

        label_4 = new QLabel(widget);
        label_4.setObjectName("label_4");
        QFont font2 = new QFont();
        font2.setPointSize(9);
        label_4.setFont(font2);

        hboxLayout2.addWidget(label_4);


        vboxLayout1.addLayout(hboxLayout2);


        hboxLayout1.addLayout(vboxLayout1);


        vboxLayout.addLayout(hboxLayout1);

        hboxLayout3 = new QHBoxLayout();
        hboxLayout3.setObjectName("hboxLayout3");
        label_6 = new QLabel(widget);
        label_6.setObjectName("label_6");
        QSizePolicy sizePolicy4 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Maximum, com.trolltech.qt.gui.QSizePolicy.Policy.Preferred);
        sizePolicy4.setHorizontalStretch((byte)0);
        sizePolicy4.setVerticalStretch((byte)0);
        sizePolicy4.setHeightForWidth(label_6.sizePolicy().hasHeightForWidth());
        label_6.setSizePolicy(sizePolicy4);

        hboxLayout3.addWidget(label_6);

        audioEffectsCombo = new QComboBox(widget);
        audioEffectsCombo.setObjectName("audioEffectsCombo");
        QSizePolicy sizePolicy5 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Fixed);
        sizePolicy5.setHorizontalStretch((byte)0);
        sizePolicy5.setVerticalStretch((byte)0);
        sizePolicy5.setHeightForWidth(audioEffectsCombo.sizePolicy().hasHeightForWidth());
        audioEffectsCombo.setSizePolicy(sizePolicy5);

        hboxLayout3.addWidget(audioEffectsCombo);


        vboxLayout.addLayout(hboxLayout3);

        retranslateUi(Dialog);
        buttonBox.accepted.connect(Dialog, "accept()");
        buttonBox.rejected.connect(Dialog, "reject()");

        Dialog.connectSlotsByName();
    } // setupUi

    void retranslateUi(QDialog Dialog)
    {
        Dialog.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "Dialog", null));
        label.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "Audio device:", null));
        label_2.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "Cross fade:", null));
        label_3.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "-10 Sec", null));
        label_5.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "0", null));
        label_4.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "10 Sec", null));
        label_6.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "Audio effect:", null));
    } // retranslateUi

}

