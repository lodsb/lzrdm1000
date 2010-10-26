/********************************************************************************
** Form generated from reading ui file 'GenericSequenceEditor.jui'
**
** Created: Mo Feb 2 16:00:27 2009
**      by: Qt User Interface Compiler version 4.4.2
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package sequence.view.form;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_GenericSequenceEditor
{
    public QWidget horizontalLayout_3;
    public QHBoxLayout editorLayout;
    public QFrame viewFrame;
    public QToolBox toolBox;
    public QWidget sequencePage;
    public QWidget horizontalLayout_2;
    public QHBoxLayout _4;
    public QGroupBox groupBox_2;
    public QWidget verticalLayout_2;
    public QVBoxLayout _2;
    public QLabel label_3;
    public QComboBox comboBox_2;
    public QLabel label_4;
    public QListView listView_2;
    public QWidget routingPage;
    public QWidget editPage;

    public Ui_GenericSequenceEditor() { super(); }

    public void setupUi(QWidget GenericSequenceEditor)
    {
        GenericSequenceEditor.setObjectName("GenericSequenceEditor");
        GenericSequenceEditor.resize(new QSize(618, 465).expandedTo(GenericSequenceEditor.minimumSizeHint()));
        GenericSequenceEditor.setMaximumSize(new QSize(618, 16777215));
        horizontalLayout_3 = new QWidget(GenericSequenceEditor);
        horizontalLayout_3.setObjectName("horizontalLayout_3");
        horizontalLayout_3.setGeometry(new QRect(6, 10, 611, 441));
        editorLayout = new QHBoxLayout(horizontalLayout_3);
        editorLayout.setObjectName("editorLayout");
        viewFrame = new QFrame(horizontalLayout_3);
        viewFrame.setObjectName("viewFrame");
        QSizePolicy sizePolicy = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.MinimumExpanding, com.trolltech.qt.gui.QSizePolicy.Policy.Preferred);
        sizePolicy.setHorizontalStretch((byte)0);
        sizePolicy.setVerticalStretch((byte)0);
        sizePolicy.setHeightForWidth(viewFrame.sizePolicy().hasHeightForWidth());
        viewFrame.setSizePolicy(sizePolicy);
        viewFrame.setFrameShape(com.trolltech.qt.gui.QFrame.Shape.StyledPanel);
        viewFrame.setFrameShadow(com.trolltech.qt.gui.QFrame.Shadow.Raised);

        editorLayout.addWidget(viewFrame);

        toolBox = new QToolBox(horizontalLayout_3);
        toolBox.setObjectName("toolBox");
        toolBox.setMinimumSize(new QSize(100, 0));
        toolBox.setMaximumSize(new QSize(16777215, 241));
        sequencePage = new QWidget();
        sequencePage.setObjectName("sequencePage");
        sequencePage.setGeometry(new QRect(0, 0, 301, 145));
        horizontalLayout_2 = new QWidget(sequencePage);
        horizontalLayout_2.setObjectName("horizontalLayout_2");
        horizontalLayout_2.setGeometry(new QRect(-1, 9, 591, 161));
        _4 = new QHBoxLayout(horizontalLayout_2);
        _4.setObjectName("_4");
        groupBox_2 = new QGroupBox(horizontalLayout_2);
        groupBox_2.setObjectName("groupBox_2");
        QSizePolicy sizePolicy1 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.Ignored, com.trolltech.qt.gui.QSizePolicy.Policy.Preferred);
        sizePolicy1.setHorizontalStretch((byte)0);
        sizePolicy1.setVerticalStretch((byte)0);
        sizePolicy1.setHeightForWidth(groupBox_2.sizePolicy().hasHeightForWidth());
        groupBox_2.setSizePolicy(sizePolicy1);
        groupBox_2.setMinimumSize(new QSize(100, 0));
        groupBox_2.setMaximumSize(new QSize(589, 159));
        verticalLayout_2 = new QWidget(groupBox_2);
        verticalLayout_2.setObjectName("verticalLayout_2");
        verticalLayout_2.setGeometry(new QRect(10, 20, 571, 101));
        _2 = new QVBoxLayout(verticalLayout_2);
        _2.setObjectName("_2");
        _2.setSizeConstraint(com.trolltech.qt.gui.QLayout.SizeConstraint.SetMinimumSize);
        label_3 = new QLabel(verticalLayout_2);
        label_3.setObjectName("label_3");

        _2.addWidget(label_3);

        comboBox_2 = new QComboBox(verticalLayout_2);
        comboBox_2.setObjectName("comboBox_2");
        comboBox_2.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.WheelFocus);

        _2.addWidget(comboBox_2);

        label_4 = new QLabel(verticalLayout_2);
        label_4.setObjectName("label_4");

        _2.addWidget(label_4);

        listView_2 = new QListView(verticalLayout_2);
        listView_2.setObjectName("listView_2");
        listView_2.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.WheelFocus);

        _2.addWidget(listView_2);


        _4.addWidget(groupBox_2);

        toolBox.addItem(sequencePage, com.trolltech.qt.core.QCoreApplication.translate("GenericSequenceEditor", "Sequence"));
        routingPage = new QWidget();
        routingPage.setObjectName("routingPage");
        routingPage.setGeometry(new QRect(0, 0, 301, 145));
        toolBox.addItem(routingPage, com.trolltech.qt.core.QCoreApplication.translate("GenericSequenceEditor", "Routing"));
        editPage = new QWidget();
        editPage.setObjectName("editPage");
        toolBox.addItem(editPage, com.trolltech.qt.core.QCoreApplication.translate("GenericSequenceEditor", "Editing"));

        editorLayout.addWidget(toolBox);

        retranslateUi(GenericSequenceEditor);

        toolBox.setCurrentIndex(0);


        GenericSequenceEditor.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget GenericSequenceEditor)
    {
        GenericSequenceEditor.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("GenericSequenceEditor", "Form"));
        groupBox_2.setTitle(com.trolltech.qt.core.QCoreApplication.translate("GenericSequenceEditor", "GroupBox"));
        label_3.setText(com.trolltech.qt.core.QCoreApplication.translate("GenericSequenceEditor", "TextLabel"));
        label_4.setText(com.trolltech.qt.core.QCoreApplication.translate("GenericSequenceEditor", "TextLabel"));
        toolBox.setItemText(toolBox.indexOf(sequencePage), com.trolltech.qt.core.QCoreApplication.translate("GenericSequenceEditor", "Sequence"));
        toolBox.setItemText(toolBox.indexOf(routingPage), com.trolltech.qt.core.QCoreApplication.translate("GenericSequenceEditor", "Routing"));
        toolBox.setItemText(toolBox.indexOf(editPage), com.trolltech.qt.core.QCoreApplication.translate("GenericSequenceEditor", "Editing"));
    } // retranslateUi

}

