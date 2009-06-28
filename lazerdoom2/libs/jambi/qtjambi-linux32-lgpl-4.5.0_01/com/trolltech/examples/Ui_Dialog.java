/********************************************************************************
** Form generated from reading ui file 'authenticationdialog.jui'
**
** Created: Tue Mar 10 10:27:05 2009
**      by: Qt User Interface Compiler version 4.5.0
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package com.trolltech.examples;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_Dialog implements com.trolltech.qt.QUiForm<QDialog>
{
    public QGridLayout gridLayout;
    public QLabel label;
    public QLabel label_2;
    public QLabel siteDescription;
    public QLabel label_3;
    public QLineEdit userEdit;
    public QLabel label_4;
    public QLineEdit passwordEdit;
    public QSpacerItem spacerItem;
    public QDialogButtonBox buttonBox;

    public Ui_Dialog() { super(); }

    public void setupUi(QDialog Dialog)
    {
        Dialog.setObjectName("Dialog");
        Dialog.resize(new QSize(382, 223).expandedTo(Dialog.minimumSizeHint()));
        gridLayout = new QGridLayout(Dialog);
        gridLayout.setObjectName("gridLayout");
        label = new QLabel(Dialog);
        label.setObjectName("label");

        gridLayout.addWidget(label, 0, 0, 1, 2);

        label_2 = new QLabel(Dialog);
        label_2.setObjectName("label_2");

        gridLayout.addWidget(label_2, 1, 0, 1, 1);

        siteDescription = new QLabel(Dialog);
        siteDescription.setObjectName("siteDescription");
        QSizePolicy sizePolicy = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Preferred);
        sizePolicy.setHorizontalStretch((byte)0);
        sizePolicy.setVerticalStretch((byte)0);
        sizePolicy.setHeightForWidth(siteDescription.sizePolicy().hasHeightForWidth());
        siteDescription.setSizePolicy(sizePolicy);

        gridLayout.addWidget(siteDescription, 1, 1, 1, 1);

        label_3 = new QLabel(Dialog);
        label_3.setObjectName("label_3");

        gridLayout.addWidget(label_3, 2, 0, 1, 1);

        userEdit = new QLineEdit(Dialog);
        userEdit.setObjectName("userEdit");
        userEdit.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);

        gridLayout.addWidget(userEdit, 2, 1, 1, 1);

        label_4 = new QLabel(Dialog);
        label_4.setObjectName("label_4");

        gridLayout.addWidget(label_4, 3, 0, 1, 1);

        passwordEdit = new QLineEdit(Dialog);
        passwordEdit.setObjectName("passwordEdit");
        passwordEdit.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);

        gridLayout.addWidget(passwordEdit, 3, 1, 1, 1);

        spacerItem = new QSpacerItem(20, 40, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        gridLayout.addItem(spacerItem, 4, 0, 1, 1);

        buttonBox = new QDialogButtonBox(Dialog);
        buttonBox.setObjectName("buttonBox");
        buttonBox.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);
        buttonBox.setStandardButtons(com.trolltech.qt.gui.QDialogButtonBox.StandardButton.createQFlags(com.trolltech.qt.gui.QDialogButtonBox.StandardButton.Cancel,com.trolltech.qt.gui.QDialogButtonBox.StandardButton.NoButton,com.trolltech.qt.gui.QDialogButtonBox.StandardButton.Ok));

        gridLayout.addWidget(buttonBox, 5, 0, 1, 2);

        retranslateUi(Dialog);
        buttonBox.accepted.connect(Dialog, "accept()");
        buttonBox.rejected.connect(Dialog, "reject()");

        Dialog.connectSlotsByName();
    } // setupUi

    void retranslateUi(QDialog Dialog)
    {
        Dialog.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "Dialog", null));
        label.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "You need to supply a Username and a Password to access this site", null));
        label_2.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "Site:", null));
        siteDescription.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "1% in 2%", null));
        label_3.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "UserName:", null));
        label_4.setText(com.trolltech.qt.core.QCoreApplication.translate("Dialog", "Password:", null));
    } // retranslateUi

}

