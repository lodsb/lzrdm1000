/********************************************************************************
** Form generated from reading ui file 'stylesheeteditor.jui'
**
** Created: Tue Mar 10 10:27:05 2009
**      by: Qt User Interface Compiler version 4.5.0
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package com.trolltech.examples.stylesheet;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_StyleSheetEditor implements com.trolltech.qt.QUiForm<QWidget>
{
    public QGridLayout gridLayout;
    public QSpacerItem spacerItem;
    public QSpacerItem spacerItem1;
    public QComboBox styleSheetCombo;
    public QSpacerItem spacerItem2;
    public QComboBox styleCombo;
    public QLabel label_7;
    public QHBoxLayout hboxLayout;
    public QSpacerItem spacerItem3;
    public QPushButton applyButton;
    public QTextEdit styleTextEdit;
    public QLabel label_8;

    public Ui_StyleSheetEditor() { super(); }

    public void setupUi(QWidget StyleSheetEditor)
    {
        StyleSheetEditor.setObjectName("StyleSheetEditor");
        StyleSheetEditor.resize(new QSize(445, 289).expandedTo(StyleSheetEditor.minimumSizeHint()));
        gridLayout = new QGridLayout(StyleSheetEditor);
        gridLayout.setSpacing(6);
        gridLayout.setMargin(9);
        gridLayout.setObjectName("gridLayout");
        spacerItem = new QSpacerItem(32, 20, com.trolltech.qt.gui.QSizePolicy.Policy.MinimumExpanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        gridLayout.addItem(spacerItem, 0, 6, 1, 1);

        spacerItem1 = new QSpacerItem(32, 20, com.trolltech.qt.gui.QSizePolicy.Policy.MinimumExpanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        gridLayout.addItem(spacerItem1, 0, 0, 1, 1);

        styleSheetCombo = new QComboBox(StyleSheetEditor);
        styleSheetCombo.setObjectName("styleSheetCombo");

        gridLayout.addWidget(styleSheetCombo, 0, 5, 1, 1);

        spacerItem2 = new QSpacerItem(10, 16, com.trolltech.qt.gui.QSizePolicy.Policy.Fixed, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        gridLayout.addItem(spacerItem2, 0, 3, 1, 1);

        styleCombo = new QComboBox(StyleSheetEditor);
        styleCombo.setObjectName("styleCombo");
        QSizePolicy sizePolicy = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(5), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(0));
        sizePolicy.setHorizontalStretch((byte)0);
        sizePolicy.setVerticalStretch((byte)0);
        sizePolicy.setHeightForWidth(styleCombo.sizePolicy().hasHeightForWidth());
        styleCombo.setSizePolicy(sizePolicy);

        gridLayout.addWidget(styleCombo, 0, 2, 1, 1);

        label_7 = new QLabel(StyleSheetEditor);
        label_7.setObjectName("label_7");
        QSizePolicy sizePolicy1 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(0), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(5));
        sizePolicy1.setHorizontalStretch((byte)0);
        sizePolicy1.setVerticalStretch((byte)0);
        sizePolicy1.setHeightForWidth(label_7.sizePolicy().hasHeightForWidth());
        label_7.setSizePolicy(sizePolicy1);

        gridLayout.addWidget(label_7, 0, 1, 1, 1);

        hboxLayout = new QHBoxLayout();
        hboxLayout.setSpacing(6);
        hboxLayout.setMargin(0);
        hboxLayout.setObjectName("hboxLayout");
        spacerItem3 = new QSpacerItem(321, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        hboxLayout.addItem(spacerItem3);

        applyButton = new QPushButton(StyleSheetEditor);
        applyButton.setObjectName("applyButton");
        applyButton.setEnabled(false);

        hboxLayout.addWidget(applyButton);


        gridLayout.addLayout(hboxLayout, 2, 0, 1, 7);

        styleTextEdit = new QTextEdit(StyleSheetEditor);
        styleTextEdit.setObjectName("styleTextEdit");

        gridLayout.addWidget(styleTextEdit, 1, 0, 1, 7);

        label_8 = new QLabel(StyleSheetEditor);
        label_8.setObjectName("label_8");
        QSizePolicy sizePolicy2 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(0), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(5));
        sizePolicy2.setHorizontalStretch((byte)0);
        sizePolicy2.setVerticalStretch((byte)0);
        sizePolicy2.setHeightForWidth(label_8.sizePolicy().hasHeightForWidth());
        label_8.setSizePolicy(sizePolicy2);

        gridLayout.addWidget(label_8, 0, 4, 1, 1);

        retranslateUi(StyleSheetEditor);

        StyleSheetEditor.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget StyleSheetEditor)
    {
        StyleSheetEditor.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("StyleSheetEditor", "Style Editor", null));
        styleSheetCombo.clear();
        styleSheetCombo.addItem(com.trolltech.qt.core.QCoreApplication.translate("StyleSheetEditor", "Default", null));
        styleSheetCombo.addItem(com.trolltech.qt.core.QCoreApplication.translate("StyleSheetEditor", "Coffee", null));
        styleSheetCombo.addItem(com.trolltech.qt.core.QCoreApplication.translate("StyleSheetEditor", "Pagefold", null));
        label_7.setText(com.trolltech.qt.core.QCoreApplication.translate("StyleSheetEditor", "Style:", null));
        applyButton.setText(com.trolltech.qt.core.QCoreApplication.translate("StyleSheetEditor", "&Apply", null));
        label_8.setText(com.trolltech.qt.core.QCoreApplication.translate("StyleSheetEditor", "Style Sheet:", null));
    } // retranslateUi

}

