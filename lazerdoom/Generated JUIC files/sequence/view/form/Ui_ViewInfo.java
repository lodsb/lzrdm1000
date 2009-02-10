/********************************************************************************
** Form generated from reading ui file 'ViewInfo.jui'
**
** Created: Di Feb 10 19:20:36 2009
**      by: Qt User Interface Compiler version 4.4.2
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package sequence.view.form;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_ViewInfo
{
    public QHBoxLayout horizontalLayout_3;
    public QGroupBox cursorBox;
    public QHBoxLayout horizontalLayout_2;
    public QLineEdit cursorPositionP;
    public QGroupBox mousePositionBox;
    public QHBoxLayout horizontalLayout;
    public QLabel positionP;
    public QLabel positionT;

    public Ui_ViewInfo() { super(); }

    public void setupUi(QWidget ViewInfo)
    {
        ViewInfo.setObjectName("ViewInfo");
        ViewInfo.resize(new QSize(457, 96).expandedTo(ViewInfo.minimumSizeHint()));
        horizontalLayout_3 = new QHBoxLayout(ViewInfo);
        horizontalLayout_3.setObjectName("horizontalLayout_3");
        cursorBox = new QGroupBox(ViewInfo);
        cursorBox.setObjectName("cursorBox");
        horizontalLayout_2 = new QHBoxLayout(cursorBox);
        horizontalLayout_2.setObjectName("horizontalLayout_2");
        cursorPositionP = new QLineEdit(cursorBox);
        cursorPositionP.setObjectName("cursorPositionP");
        cursorPositionP.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);

        horizontalLayout_2.addWidget(cursorPositionP);


        horizontalLayout_3.addWidget(cursorBox);

        mousePositionBox = new QGroupBox(ViewInfo);
        mousePositionBox.setObjectName("mousePositionBox");
        horizontalLayout = new QHBoxLayout(mousePositionBox);
        horizontalLayout.setObjectName("horizontalLayout");
        positionP = new QLabel(mousePositionBox);
        positionP.setObjectName("positionP");

        horizontalLayout.addWidget(positionP);

        positionT = new QLabel(mousePositionBox);
        positionT.setObjectName("positionT");

        horizontalLayout.addWidget(positionT);


        horizontalLayout_3.addWidget(mousePositionBox);

        retranslateUi(ViewInfo);

        ViewInfo.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget ViewInfo)
    {
        ViewInfo.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("ViewInfo", "Form"));
        cursorBox.setTitle(com.trolltech.qt.core.QCoreApplication.translate("ViewInfo", "Cursor"));
        mousePositionBox.setTitle(com.trolltech.qt.core.QCoreApplication.translate("ViewInfo", "Position"));
        positionP.setText(com.trolltech.qt.core.QCoreApplication.translate("ViewInfo", "TextLabel"));
        positionT.setText(com.trolltech.qt.core.QCoreApplication.translate("ViewInfo", "TextLabel"));
    } // retranslateUi

}

