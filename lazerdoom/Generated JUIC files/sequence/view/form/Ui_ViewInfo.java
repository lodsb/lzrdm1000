/********************************************************************************
** Form generated from reading ui file 'ViewInfo.jui'
**
** Created: Mo Feb 23 05:41:42 2009
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
    public QSpacerItem horizontalSpacer;
    public QGroupBox mousePositionBox;
    public QHBoxLayout horizontalLayout;
    public QLabel positionP;
    public QLabel positionT;

    public Ui_ViewInfo() { super(); }

    public void setupUi(QWidget ViewInfo)
    {
        ViewInfo.setObjectName("ViewInfo");
        ViewInfo.resize(new QSize(457, 96).expandedTo(ViewInfo.minimumSizeHint()));
        ViewInfo.setMinimumSize(new QSize(457, 75));
        ViewInfo.setMaximumSize(new QSize(16777215, 96));
        ViewInfo.setAutoFillBackground(false);
        horizontalLayout_3 = new QHBoxLayout(ViewInfo);
        horizontalLayout_3.setObjectName("horizontalLayout_3");
        horizontalSpacer = new QSpacerItem(0, 0, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        horizontalLayout_3.addItem(horizontalSpacer);

        mousePositionBox = new QGroupBox(ViewInfo);
        mousePositionBox.setObjectName("mousePositionBox");
        mousePositionBox.setMinimumSize(new QSize(150, 0));
        mousePositionBox.setMaximumSize(new QSize(150, 75));
        horizontalLayout = new QHBoxLayout(mousePositionBox);
        horizontalLayout.setObjectName("horizontalLayout");
        positionP = new QLabel(mousePositionBox);
        positionP.setObjectName("positionP");

        horizontalLayout.addWidget(positionP);

        positionT = new QLabel(mousePositionBox);
        positionT.setObjectName("positionT");
        QSizePolicy sizePolicy = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Preferred, com.trolltech.qt.gui.QSizePolicy.Policy.Preferred);
        sizePolicy.setHorizontalStretch((byte)0);
        sizePolicy.setVerticalStretch((byte)0);
        sizePolicy.setHeightForWidth(positionT.sizePolicy().hasHeightForWidth());
        positionT.setSizePolicy(sizePolicy);

        horizontalLayout.addWidget(positionT);


        horizontalLayout_3.addWidget(mousePositionBox);

        retranslateUi(ViewInfo);

        ViewInfo.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget ViewInfo)
    {
        ViewInfo.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("ViewInfo", "Form"));
        mousePositionBox.setTitle(com.trolltech.qt.core.QCoreApplication.translate("ViewInfo", "Position"));
        positionP.setText(com.trolltech.qt.core.QCoreApplication.translate("ViewInfo", ""));
        positionT.setText(com.trolltech.qt.core.QCoreApplication.translate("ViewInfo", ""));
    } // retranslateUi

}

