/********************************************************************************
** Form generated from reading ui file 'EditWidget.jui'
**
** Created: Do Feb 19 19:38:56 2009
**      by: Qt User Interface Compiler version 4.4.2
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package sequence.view.form;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_EditWidget
{
    public QHBoxLayout horizontalLayout;
    public QGroupBox groupBox;
    public QVBoxLayout verticalLayout;
    public QLineEdit lineEdit;

    public Ui_EditWidget() { super(); }

    public void setupUi(QWidget EditWidget)
    {
        EditWidget.setObjectName("EditWidget");
        EditWidget.resize(new QSize(400, 96).expandedTo(EditWidget.minimumSizeHint()));
        horizontalLayout = new QHBoxLayout(EditWidget);
        horizontalLayout.setObjectName("horizontalLayout");
        groupBox = new QGroupBox(EditWidget);
        groupBox.setObjectName("groupBox");
        groupBox.setMaximumSize(new QSize(16777215, 75));
        verticalLayout = new QVBoxLayout(groupBox);
        verticalLayout.setObjectName("verticalLayout");
        lineEdit = new QLineEdit(groupBox);
        lineEdit.setObjectName("lineEdit");
        lineEdit.setMaximumSize(new QSize(16777215, 50));
        lineEdit.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);

        verticalLayout.addWidget(lineEdit);


        horizontalLayout.addWidget(groupBox);

        retranslateUi(EditWidget);

        EditWidget.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget EditWidget)
    {
        EditWidget.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("EditWidget", "Form"));
        groupBox.setTitle(com.trolltech.qt.core.QCoreApplication.translate("EditWidget", "Value"));
    } // retranslateUi

}

