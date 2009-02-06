/********************************************************************************
** Form generated from reading ui file 'ListSelectionWidget.jui'
**
** Created: Mo Feb 2 16:47:05 2009
**      by: Qt User Interface Compiler version 4.4.2
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package sequence.view.form;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_ListSelectionWidget
{
    public QHBoxLayout horizontalLayout;
    public QVBoxLayout vboxLayout;
    public QGroupBox groupBox;
    public QHBoxLayout horizontalLayout_2;
    public QVBoxLayout _2;
    public QLabel label;
    public QComboBox comboBox;
    public QLabel label_2;
    public QListView listView;

    public Ui_ListSelectionWidget() { super(); }

    public void setupUi(QWidget ListSelectionWidget)
    {
        ListSelectionWidget.setObjectName("ListSelectionWidget");
        ListSelectionWidget.resize(new QSize(300, 346).expandedTo(ListSelectionWidget.minimumSizeHint()));
        QSizePolicy sizePolicy = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.MinimumExpanding, com.trolltech.qt.gui.QSizePolicy.Policy.MinimumExpanding);
        sizePolicy.setHorizontalStretch((byte)0);
        sizePolicy.setVerticalStretch((byte)0);
        sizePolicy.setHeightForWidth(ListSelectionWidget.sizePolicy().hasHeightForWidth());
        ListSelectionWidget.setSizePolicy(sizePolicy);
        horizontalLayout = new QHBoxLayout(ListSelectionWidget);
        horizontalLayout.setObjectName("horizontalLayout");
        vboxLayout = new QVBoxLayout();
        vboxLayout.setObjectName("vboxLayout");
        groupBox = new QGroupBox(ListSelectionWidget);
        groupBox.setObjectName("groupBox");
        horizontalLayout_2 = new QHBoxLayout(groupBox);
        horizontalLayout_2.setObjectName("horizontalLayout_2");
        _2 = new QVBoxLayout();
        _2.setObjectName("_2");
        label = new QLabel(groupBox);
        label.setObjectName("label");

        _2.addWidget(label);

        comboBox = new QComboBox(groupBox);
        comboBox.setObjectName("comboBox");
        comboBox.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.WheelFocus);

        _2.addWidget(comboBox);

        label_2 = new QLabel(groupBox);
        label_2.setObjectName("label_2");

        _2.addWidget(label_2);

        listView = new QListView(groupBox);
        listView.setObjectName("listView");
        listView.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.WheelFocus);

        _2.addWidget(listView);


        horizontalLayout_2.addLayout(_2);


        vboxLayout.addWidget(groupBox);


        horizontalLayout.addLayout(vboxLayout);

        retranslateUi(ListSelectionWidget);

        ListSelectionWidget.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget ListSelectionWidget)
    {
        ListSelectionWidget.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("ListSelectionWidget", "Form"));
        groupBox.setTitle(com.trolltech.qt.core.QCoreApplication.translate("ListSelectionWidget", "GroupBox"));
        label.setText(com.trolltech.qt.core.QCoreApplication.translate("ListSelectionWidget", "TextLabel"));
        label_2.setText(com.trolltech.qt.core.QCoreApplication.translate("ListSelectionWidget", "TextLabel"));
    } // retranslateUi

}

