/********************************************************************************
** Form generated from reading ui file 'EditorViewPropertiesBox.jui'
**
** Created: Mo Feb 23 05:56:08 2009
**      by: Qt User Interface Compiler version 4.4.2
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package sequence.view.form;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_EditorViewPropertiesBox
{
    public QVBoxLayout verticalLayout;
    public QPushButton showHorizontalRuler;
    public QPushButton showVerticalRuler;
    public QSpacerItem horizontalSpacer;
    public QPushButton showToolBox;
    public QPushButton showViewInfoWidget;
    public QSpacerItem verticalSpacer;

    public Ui_EditorViewPropertiesBox() { super(); }

    public void setupUi(QWidget EditorViewPropertiesBox)
    {
        EditorViewPropertiesBox.setObjectName("EditorViewPropertiesBox");
        EditorViewPropertiesBox.resize(new QSize(34, 197).expandedTo(EditorViewPropertiesBox.minimumSizeHint()));
        EditorViewPropertiesBox.setMaximumSize(new QSize(34, 16777215));
        verticalLayout = new QVBoxLayout(EditorViewPropertiesBox);
        verticalLayout.setObjectName("verticalLayout");
        showHorizontalRuler = new QPushButton(EditorViewPropertiesBox);
        showHorizontalRuler.setObjectName("showHorizontalRuler");
        showHorizontalRuler.setMaximumSize(new QSize(16, 16));
        showHorizontalRuler.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);
        showHorizontalRuler.setCheckable(true);
        showHorizontalRuler.setChecked(true);

        verticalLayout.addWidget(showHorizontalRuler);

        showVerticalRuler = new QPushButton(EditorViewPropertiesBox);
        showVerticalRuler.setObjectName("showVerticalRuler");
        showVerticalRuler.setMaximumSize(new QSize(16, 16));
        showVerticalRuler.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);
        showVerticalRuler.setCheckable(true);
        showVerticalRuler.setChecked(true);

        verticalLayout.addWidget(showVerticalRuler);

        horizontalSpacer = new QSpacerItem(0, 0, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        verticalLayout.addItem(horizontalSpacer);

        showToolBox = new QPushButton(EditorViewPropertiesBox);
        showToolBox.setObjectName("showToolBox");
        showToolBox.setMaximumSize(new QSize(16, 16));
        showToolBox.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);
        showToolBox.setCheckable(true);
        showToolBox.setChecked(true);

        verticalLayout.addWidget(showToolBox);

        showViewInfoWidget = new QPushButton(EditorViewPropertiesBox);
        showViewInfoWidget.setObjectName("showViewInfoWidget");
        showViewInfoWidget.setMaximumSize(new QSize(16, 16));
        showViewInfoWidget.setFocusPolicy(com.trolltech.qt.core.Qt.FocusPolicy.StrongFocus);
        showViewInfoWidget.setCheckable(true);
        showViewInfoWidget.setChecked(true);

        verticalLayout.addWidget(showViewInfoWidget);

        verticalSpacer = new QSpacerItem(0, 0, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        verticalLayout.addItem(verticalSpacer);

        retranslateUi(EditorViewPropertiesBox);

        EditorViewPropertiesBox.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget EditorViewPropertiesBox)
    {
        EditorViewPropertiesBox.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("EditorViewPropertiesBox", "Form"));
        showHorizontalRuler.setToolTip(com.trolltech.qt.core.QCoreApplication.translate("EditorViewPropertiesBox", "Show horizontal ruler"));
        showHorizontalRuler.setText(com.trolltech.qt.core.QCoreApplication.translate("EditorViewPropertiesBox", "hr"));
        showVerticalRuler.setToolTip(com.trolltech.qt.core.QCoreApplication.translate("EditorViewPropertiesBox", "Show vertical ruler"));
        showVerticalRuler.setText(com.trolltech.qt.core.QCoreApplication.translate("EditorViewPropertiesBox", "vr"));
        showToolBox.setToolTip(com.trolltech.qt.core.QCoreApplication.translate("EditorViewPropertiesBox", "Show Toolbox"));
        showToolBox.setText(com.trolltech.qt.core.QCoreApplication.translate("EditorViewPropertiesBox", "tb"));
        showViewInfoWidget.setToolTip(com.trolltech.qt.core.QCoreApplication.translate("EditorViewPropertiesBox", "Show view info"));
        showViewInfoWidget.setText(com.trolltech.qt.core.QCoreApplication.translate("EditorViewPropertiesBox", "e"));
    } // retranslateUi

}

