/********************************************************************************
** Form generated from reading ui file 'chatdialog.jui'
**
** Created: Tue Mar 10 10:27:05 2009
**      by: Qt User Interface Compiler version 4.5.0
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package com.trolltech.examples;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_ChatDialog implements com.trolltech.qt.QUiForm<QDialog>
{
    public QVBoxLayout vboxLayout;
    public QHBoxLayout hboxLayout;
    public QTextEdit textEdit;
    public QListWidget listWidget;
    public QHBoxLayout hboxLayout1;
    public QLabel label;
    public QLineEdit lineEdit;

    public Ui_ChatDialog() { super(); }

    public void setupUi(QDialog ChatDialog)
    {
        ChatDialog.setObjectName("ChatDialog");
        ChatDialog.resize(new QSize(513, 349).expandedTo(ChatDialog.minimumSizeHint()));
        vboxLayout = new QVBoxLayout(ChatDialog);
        vboxLayout.setSpacing(6);
        vboxLayout.setMargin(9);
        vboxLayout.setObjectName("vboxLayout");
        hboxLayout = new QHBoxLayout();
        hboxLayout.setSpacing(6);
        hboxLayout.setMargin(0);
        hboxLayout.setObjectName("hboxLayout");
        textEdit = new QTextEdit(ChatDialog);
        textEdit.setObjectName("textEdit");
        textEdit.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.NoFocus);
        textEdit.setReadOnly(true);

        hboxLayout.addWidget(textEdit);

        listWidget = new QListWidget(ChatDialog);
        listWidget.setObjectName("listWidget");
        listWidget.setMaximumSize(new QSize(180, 16777215));
        listWidget.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.NoFocus);

        hboxLayout.addWidget(listWidget);


        vboxLayout.addLayout(hboxLayout);

        hboxLayout1 = new QHBoxLayout();
        hboxLayout1.setSpacing(6);
        hboxLayout1.setMargin(0);
        hboxLayout1.setObjectName("hboxLayout1");
        label = new QLabel(ChatDialog);
        label.setObjectName("label");

        hboxLayout1.addWidget(label);

        lineEdit = new QLineEdit(ChatDialog);
        lineEdit.setObjectName("lineEdit");

        hboxLayout1.addWidget(lineEdit);


        vboxLayout.addLayout(hboxLayout1);

        retranslateUi(ChatDialog);

        ChatDialog.connectSlotsByName();
    } // setupUi

    void retranslateUi(QDialog ChatDialog)
    {
        ChatDialog.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("ChatDialog", "Chat", null));
        label.setText(com.trolltech.qt.core.QCoreApplication.translate("ChatDialog", "Message:", null));
    } // retranslateUi

}

