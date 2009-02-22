/********************************************************************************
** Form generated from reading ui file 'CursorToolBox.jui'
**
** Created: Fr Feb 20 23:26:01 2009
**      by: Qt User Interface Compiler version 4.4.2
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package sequence.view.form;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_CursorToolBox
{
    public QVBoxLayout verticalLayout_2;
    public QGroupBox groupBox;
    public QVBoxLayout verticalLayout;
    public QFrame frame_3;
    public QHBoxLayout horizontalLayout_3;
    public QRadioButton radioButton;
    public QPushButton pushButton_2;
    public QPushButton pushButton;
    public QLabel label_3;
    public QFrame frame;
    public QHBoxLayout horizontalLayout;
    public QSpinBox spinBox;
    public QLabel label_2;
    public QSpinBox spinBox_2;
    public QLabel label;
    public QSpinBox spinBox_3;
    public QLabel label_4;
    public QFrame frame_2;
    public QHBoxLayout horizontalLayout_2;
    public QSpinBox spinBox_4;
    public QLabel label_5;
    public QSpinBox spinBox_5;
    public QLabel label_6;
    public QSpinBox spinBox_6;

    public Ui_CursorToolBox() { super(); }

    public void setupUi(QWidget CursorToolBox)
    {
        CursorToolBox.setObjectName("CursorToolBox");
        CursorToolBox.resize(new QSize(655, 390).expandedTo(CursorToolBox.minimumSizeHint()));
        CursorToolBox.setMaximumSize(new QSize(16777215, 390));
        verticalLayout_2 = new QVBoxLayout(CursorToolBox);
        verticalLayout_2.setObjectName("verticalLayout_2");
        groupBox = new QGroupBox(CursorToolBox);
        groupBox.setObjectName("groupBox");
        verticalLayout = new QVBoxLayout(groupBox);
        verticalLayout.setObjectName("verticalLayout");
        frame_3 = new QFrame(groupBox);
        frame_3.setObjectName("frame_3");
        frame_3.setFrameShape(com.trolltech.qt.gui.QFrame.Shape.StyledPanel);
        frame_3.setFrameShadow(com.trolltech.qt.gui.QFrame.Shadow.Raised);
        horizontalLayout_3 = new QHBoxLayout(frame_3);
        horizontalLayout_3.setObjectName("horizontalLayout_3");
        radioButton = new QRadioButton(frame_3);
        radioButton.setObjectName("radioButton");
        QFont font = new QFont();
        font.setPointSize(12);
        font.setKerning(false);
        radioButton.setFont(font);
        radioButton.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);

        horizontalLayout_3.addWidget(radioButton);

        pushButton_2 = new QPushButton(frame_3);
        pushButton_2.setObjectName("pushButton_2");
        pushButton_2.setMaximumSize(new QSize(55, 30));
        pushButton_2.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);
        pushButton_2.setIcon(new QIcon(new QPixmap("classpath:sequence/view/icons/left_arrow.png")));
        pushButton_2.setIconSize(new QSize(50, 23));
        pushButton_2.setAutoDefault(true);
        pushButton_2.setDefault(false);
        pushButton_2.setFlat(false);

        horizontalLayout_3.addWidget(pushButton_2);

        pushButton = new QPushButton(frame_3);
        pushButton.setObjectName("pushButton");
        pushButton.setMaximumSize(new QSize(55, 30));
        pushButton.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);
        pushButton.setIcon(new QIcon(new QPixmap("classpath:sequence/view/icons/right_arrow.png")));
        pushButton.setIconSize(new QSize(50, 23));

        horizontalLayout_3.addWidget(pushButton);


        verticalLayout.addWidget(frame_3);

        label_3 = new QLabel(groupBox);
        label_3.setObjectName("label_3");
        QFont font1 = new QFont();
        font1.setPointSize(13);
        font1.setBold(true);
        font1.setWeight(75);
        label_3.setFont(font1);
        label_3.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignLeft,com.trolltech.qt.core.Qt.AlignmentFlag.AlignLeft,com.trolltech.qt.core.Qt.AlignmentFlag.AlignVCenter));

        verticalLayout.addWidget(label_3);

        frame = new QFrame(groupBox);
        frame.setObjectName("frame");
        frame.setFrameShape(com.trolltech.qt.gui.QFrame.Shape.StyledPanel);
        frame.setFrameShadow(com.trolltech.qt.gui.QFrame.Shadow.Raised);
        horizontalLayout = new QHBoxLayout(frame);
        horizontalLayout.setObjectName("horizontalLayout");
        spinBox = new QSpinBox(frame);
        spinBox.setObjectName("spinBox");
        spinBox.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.WheelFocus);

        horizontalLayout.addWidget(spinBox);

        label_2 = new QLabel(frame);
        label_2.setObjectName("label_2");
        label_2.setMaximumSize(new QSize(10, 16777215));
        QFont font2 = new QFont();
        font2.setPointSize(30);
        font2.setBold(true);
        font2.setWeight(75);
        label_2.setFont(font2);
        label_2.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignBottom,com.trolltech.qt.core.Qt.AlignmentFlag.AlignHCenter));

        horizontalLayout.addWidget(label_2);

        spinBox_2 = new QSpinBox(frame);
        spinBox_2.setObjectName("spinBox_2");
        spinBox_2.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.WheelFocus);

        horizontalLayout.addWidget(spinBox_2);

        label = new QLabel(frame);
        label.setObjectName("label");
        label.setMaximumSize(new QSize(10, 16777215));
        QFont font3 = new QFont();
        font3.setPointSize(30);
        font3.setBold(true);
        font3.setWeight(75);
        label.setFont(font3);
        label.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignBottom,com.trolltech.qt.core.Qt.AlignmentFlag.AlignHCenter));

        horizontalLayout.addWidget(label);

        spinBox_3 = new QSpinBox(frame);
        spinBox_3.setObjectName("spinBox_3");
        spinBox_3.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.WheelFocus);

        horizontalLayout.addWidget(spinBox_3);


        verticalLayout.addWidget(frame);

        label_4 = new QLabel(groupBox);
        label_4.setObjectName("label_4");
        QFont font4 = new QFont();
        font4.setPointSize(13);
        font4.setBold(true);
        font4.setUnderline(false);
        font4.setWeight(75);
        label_4.setFont(font4);
        label_4.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignLeft,com.trolltech.qt.core.Qt.AlignmentFlag.AlignLeft,com.trolltech.qt.core.Qt.AlignmentFlag.AlignVCenter));

        verticalLayout.addWidget(label_4);

        frame_2 = new QFrame(groupBox);
        frame_2.setObjectName("frame_2");
        frame_2.setFrameShape(com.trolltech.qt.gui.QFrame.Shape.StyledPanel);
        frame_2.setFrameShadow(com.trolltech.qt.gui.QFrame.Shadow.Raised);
        horizontalLayout_2 = new QHBoxLayout(frame_2);
        horizontalLayout_2.setObjectName("horizontalLayout_2");
        spinBox_4 = new QSpinBox(frame_2);
        spinBox_4.setObjectName("spinBox_4");
        spinBox_4.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.WheelFocus);

        horizontalLayout_2.addWidget(spinBox_4);

        label_5 = new QLabel(frame_2);
        label_5.setObjectName("label_5");
        label_5.setMaximumSize(new QSize(10, 16777215));
        QFont font5 = new QFont();
        font5.setPointSize(30);
        font5.setBold(true);
        font5.setWeight(75);
        label_5.setFont(font5);
        label_5.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignLeft));

        horizontalLayout_2.addWidget(label_5);

        spinBox_5 = new QSpinBox(frame_2);
        spinBox_5.setObjectName("spinBox_5");
        spinBox_5.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.WheelFocus);

        horizontalLayout_2.addWidget(spinBox_5);

        label_6 = new QLabel(frame_2);
        label_6.setObjectName("label_6");
        label_6.setMaximumSize(new QSize(10, 16777215));
        QFont font6 = new QFont();
        font6.setPointSize(30);
        font6.setBold(true);
        font6.setWeight(75);
        label_6.setFont(font6);
        label_6.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignCenter));

        horizontalLayout_2.addWidget(label_6);

        spinBox_6 = new QSpinBox(frame_2);
        spinBox_6.setObjectName("spinBox_6");
        spinBox_6.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.WheelFocus);

        horizontalLayout_2.addWidget(spinBox_6);


        verticalLayout.addWidget(frame_2);


        verticalLayout_2.addWidget(groupBox);

        retranslateUi(CursorToolBox);

        CursorToolBox.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget CursorToolBox)
    {
        CursorToolBox.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("CursorToolBox", "Form"));
        groupBox.setTitle(com.trolltech.qt.core.QCoreApplication.translate("CursorToolBox", "Cursor"));
        radioButton.setText(com.trolltech.qt.core.QCoreApplication.translate("CursorToolBox", "absolute positioning"));
        pushButton_2.setText(com.trolltech.qt.core.QCoreApplication.translate("CursorToolBox", ""));
        pushButton.setText(com.trolltech.qt.core.QCoreApplication.translate("CursorToolBox", ""));
        label_3.setText(com.trolltech.qt.core.QCoreApplication.translate("CursorToolBox", "Bar"));
        label_2.setText(com.trolltech.qt.core.QCoreApplication.translate("CursorToolBox", "."));
        label.setText(com.trolltech.qt.core.QCoreApplication.translate("CursorToolBox", "."));
        spinBox_3.setSuffix(com.trolltech.qt.core.QCoreApplication.translate("CursorToolBox", ""));
        label_4.setText(com.trolltech.qt.core.QCoreApplication.translate("CursorToolBox", "Time"));
        spinBox_4.setSuffix(com.trolltech.qt.core.QCoreApplication.translate("CursorToolBox", " m"));
        label_5.setText(com.trolltech.qt.core.QCoreApplication.translate("CursorToolBox", ":"));
        spinBox_5.setSuffix(com.trolltech.qt.core.QCoreApplication.translate("CursorToolBox", " s"));
        label_6.setText(com.trolltech.qt.core.QCoreApplication.translate("CursorToolBox", ":"));
        spinBox_6.setSuffix(com.trolltech.qt.core.QCoreApplication.translate("CursorToolBox", " ms"));
    } // retranslateUi

}

