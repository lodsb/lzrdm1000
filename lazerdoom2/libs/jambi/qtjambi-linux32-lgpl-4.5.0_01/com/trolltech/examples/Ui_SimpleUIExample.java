/********************************************************************************
** Form generated from reading ui file 'SimpleUIExample.jui'
**
** Created: Tue Mar 10 10:27:05 2009
**      by: Qt User Interface Compiler version 4.5.0
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package com.trolltech.examples;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_SimpleUIExample implements com.trolltech.qt.QUiForm<QDialog>
{
    public QGridLayout gridLayout;
    public QDialogButtonBox buttonBox;
    public QFrame line;
    public QGroupBox groupBox_Required;
    public QGridLayout gridLayout1;
    public QSpacerItem spacerItem;
    public QCheckBox checkBox_Spam;
    public QLabel label_Email;
    public QLabel label_Name;
    public QLineEdit lineEdit_Email;
    public QLineEdit lineEdit_Name;
    public QGroupBox groupBox_Optional;
    public QGridLayout gridLayout2;
    public QSpacerItem spacerItem1;
    public QLineEdit lineEdit_What;
    public QLabel label_Born;
    public QLabel label_Profession;
    public QLabel label_What;
    public QDateEdit dateEdit_Born;
    public QComboBox comboBox_Profession;
    public QLabel label_Comments;
    public QTextEdit textEdit_Comments;
    public QPushButton pushButton_Clear;
    public QSpacerItem spacerItem2;

    public Ui_SimpleUIExample() { super(); }

    public void setupUi(QDialog SimpleUIExample)
    {
        SimpleUIExample.setObjectName("SimpleUIExample");
        SimpleUIExample.resize(new QSize(460, 351).expandedTo(SimpleUIExample.minimumSizeHint()));
        SimpleUIExample.setModal(true);
        gridLayout = new QGridLayout(SimpleUIExample);
        gridLayout.setSpacing(6);
        gridLayout.setMargin(9);
        gridLayout.setObjectName("gridLayout");
        buttonBox = new QDialogButtonBox(SimpleUIExample);
        buttonBox.setObjectName("buttonBox");
        buttonBox.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);
        buttonBox.setStandardButtons(com.trolltech.qt.gui.QDialogButtonBox.StandardButton.createQFlags(com.trolltech.qt.gui.QDialogButtonBox.StandardButton.Cancel,com.trolltech.qt.gui.QDialogButtonBox.StandardButton.NoButton,com.trolltech.qt.gui.QDialogButtonBox.StandardButton.Ok));

        gridLayout.addWidget(buttonBox, 5, 0, 1, 3);

        line = new QFrame(SimpleUIExample);
        line.setObjectName("line");
        line.setFrameShape(QFrame.Shape.HLine);
        line.setFrameShadow(QFrame.Shadow.Sunken);

        gridLayout.addWidget(line, 4, 0, 1, 3);

        groupBox_Required = new QGroupBox(SimpleUIExample);
        groupBox_Required.setObjectName("groupBox_Required");
        gridLayout1 = new QGridLayout(groupBox_Required);
        gridLayout1.setSpacing(6);
        gridLayout1.setMargin(9);
        gridLayout1.setObjectName("gridLayout1");
        spacerItem = new QSpacerItem(20, 40, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        gridLayout1.addItem(spacerItem, 3, 1, 1, 1);

        checkBox_Spam = new QCheckBox(groupBox_Required);
        checkBox_Spam.setObjectName("checkBox_Spam");
        checkBox_Spam.setChecked(true);

        gridLayout1.addWidget(checkBox_Spam, 2, 1, 1, 1);

        label_Email = new QLabel(groupBox_Required);
        label_Email.setObjectName("label_Email");

        gridLayout1.addWidget(label_Email, 1, 0, 1, 1);

        label_Name = new QLabel(groupBox_Required);
        label_Name.setObjectName("label_Name");

        gridLayout1.addWidget(label_Name, 0, 0, 1, 1);

        lineEdit_Email = new QLineEdit(groupBox_Required);
        lineEdit_Email.setObjectName("lineEdit_Email");

        gridLayout1.addWidget(lineEdit_Email, 1, 1, 1, 1);

        lineEdit_Name = new QLineEdit(groupBox_Required);
        lineEdit_Name.setObjectName("lineEdit_Name");

        gridLayout1.addWidget(lineEdit_Name, 0, 1, 1, 1);


        gridLayout.addWidget(groupBox_Required, 0, 0, 2, 1);

        groupBox_Optional = new QGroupBox(SimpleUIExample);
        groupBox_Optional.setObjectName("groupBox_Optional");
        groupBox_Optional.setEnabled(true);
        groupBox_Optional.setCheckable(true);
        groupBox_Optional.setChecked(false);
        gridLayout2 = new QGridLayout(groupBox_Optional);
        gridLayout2.setSpacing(6);
        gridLayout2.setMargin(9);
        gridLayout2.setObjectName("gridLayout2");
        spacerItem1 = new QSpacerItem(20, 40, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        gridLayout2.addItem(spacerItem1, 3, 1, 1, 1);

        lineEdit_What = new QLineEdit(groupBox_Optional);
        lineEdit_What.setObjectName("lineEdit_What");

        gridLayout2.addWidget(lineEdit_What, 0, 1, 1, 2);

        label_Born = new QLabel(groupBox_Optional);
        label_Born.setObjectName("label_Born");

        gridLayout2.addWidget(label_Born, 2, 0, 1, 1);

        label_Profession = new QLabel(groupBox_Optional);
        label_Profession.setObjectName("label_Profession");

        gridLayout2.addWidget(label_Profession, 1, 0, 1, 1);

        label_What = new QLabel(groupBox_Optional);
        label_What.setObjectName("label_What");

        gridLayout2.addWidget(label_What, 0, 0, 1, 1);

        dateEdit_Born = new QDateEdit(groupBox_Optional);
        dateEdit_Born.setObjectName("dateEdit_Born");

        gridLayout2.addWidget(dateEdit_Born, 2, 1, 1, 2);

        comboBox_Profession = new QComboBox(groupBox_Optional);
        comboBox_Profession.setObjectName("comboBox_Profession");

        gridLayout2.addWidget(comboBox_Profession, 1, 1, 1, 2);


        gridLayout.addWidget(groupBox_Optional, 2, 0, 2, 1);

        label_Comments = new QLabel(SimpleUIExample);
        label_Comments.setObjectName("label_Comments");
        QSizePolicy sizePolicy = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(5), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(4));
        sizePolicy.setHorizontalStretch((byte)0);
        sizePolicy.setVerticalStretch((byte)0);
        sizePolicy.setHeightForWidth(label_Comments.sizePolicy().hasHeightForWidth());
        label_Comments.setSizePolicy(sizePolicy);

        gridLayout.addWidget(label_Comments, 0, 1, 1, 2);

        textEdit_Comments = new QTextEdit(SimpleUIExample);
        textEdit_Comments.setObjectName("textEdit_Comments");

        gridLayout.addWidget(textEdit_Comments, 1, 1, 2, 2);

        pushButton_Clear = new QPushButton(SimpleUIExample);
        pushButton_Clear.setObjectName("pushButton_Clear");

        gridLayout.addWidget(pushButton_Clear, 3, 2, 1, 1);

        spacerItem2 = new QSpacerItem(231, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        gridLayout.addItem(spacerItem2, 3, 1, 1, 1);

        QWidget.setTabOrder(lineEdit_Name, lineEdit_Email);
        QWidget.setTabOrder(lineEdit_Email, checkBox_Spam);
        QWidget.setTabOrder(checkBox_Spam, lineEdit_What);
        QWidget.setTabOrder(lineEdit_What, comboBox_Profession);
        QWidget.setTabOrder(comboBox_Profession, dateEdit_Born);
        QWidget.setTabOrder(dateEdit_Born, textEdit_Comments);
        QWidget.setTabOrder(textEdit_Comments, pushButton_Clear);
        retranslateUi(SimpleUIExample);
        pushButton_Clear.clicked.connect(textEdit_Comments, "clear()");
        buttonBox.accepted.connect(SimpleUIExample, "accept()");
        buttonBox.rejected.connect(SimpleUIExample, "reject()");

        SimpleUIExample.connectSlotsByName();
    } // setupUi

    void retranslateUi(QDialog SimpleUIExample)
    {
        SimpleUIExample.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("SimpleUIExample", "Dialog", null));
        groupBox_Required.setTitle(com.trolltech.qt.core.QCoreApplication.translate("SimpleUIExample", "Required", null));
        checkBox_Spam.setText(com.trolltech.qt.core.QCoreApplication.translate("SimpleUIExample", "Send me SPAM", null));
        label_Email.setText(com.trolltech.qt.core.QCoreApplication.translate("SimpleUIExample", "E-Mail", null));
        label_Name.setText(com.trolltech.qt.core.QCoreApplication.translate("SimpleUIExample", "Full Name", null));
        groupBox_Optional.setTitle(com.trolltech.qt.core.QCoreApplication.translate("SimpleUIExample", "Optional", null));
        label_Born.setText(com.trolltech.qt.core.QCoreApplication.translate("SimpleUIExample", "Born", null));
        label_Profession.setText(com.trolltech.qt.core.QCoreApplication.translate("SimpleUIExample", "Profession", null));
        label_What.setText(com.trolltech.qt.core.QCoreApplication.translate("SimpleUIExample", "What", null));
        comboBox_Profession.clear();
        comboBox_Profession.addItem("");
        comboBox_Profession.addItem(com.trolltech.qt.core.QCoreApplication.translate("SimpleUIExample", "Superstar", null));
        comboBox_Profession.addItem(com.trolltech.qt.core.QCoreApplication.translate("SimpleUIExample", "Programmer", null));
        comboBox_Profession.addItem(com.trolltech.qt.core.QCoreApplication.translate("SimpleUIExample", "Teacher", null));
        label_Comments.setText(com.trolltech.qt.core.QCoreApplication.translate("SimpleUIExample", "Comments:", null));
        textEdit_Comments.setHtml(com.trolltech.qt.core.QCoreApplication.translate("SimpleUIExample", "<html><head><meta name=\"qrichtext\" content=\"1\" /><style type=\"text/css\">\n"+
"p, li { white-space: pre-wrap; }\n"+
"</style></head><body style=\" font-family:'Sans Serif'; font-size:9pt; font-weight:400; font-style:normal; text-decoration:none;\">\n"+
"<p style=\" margin-top:0px; margin-bottom:0px; margin-left:0px; margin-right:0px; -qt-block-indent:0; text-indent:0px; font-family:'MS'; font-size:8pt;\">You can write some <span style=\" text-decoration: underline;\">comments</span> here.</p></body></html>", null));
        pushButton_Clear.setText(com.trolltech.qt.core.QCoreApplication.translate("SimpleUIExample", "Clear comment", null));
    } // retranslateUi

}

