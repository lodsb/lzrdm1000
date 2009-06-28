/********************************************************************************
** Form generated from reading ui file 'LauncherUI.jui'
**
** Created: Tue Mar 10 10:27:05 2009
**      by: Qt User Interface Compiler version 4.5.0
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package com.trolltech.launcher;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;


public class Ui_Launcher implements com.trolltech.qt.QUiForm<QWidget>
{
    public QGridLayout gridLayout;
    public QGroupBox group_main;
    public QGridLayout gridLayout1;
    public QGroupBox groupBox;
    public QGridLayout gridLayout2;
    public com.trolltech.launcher.CustomListView list;
    public QStackedWidget container;
    public QWidget page;
    public QGridLayout gridLayout3;
    public com.trolltech.launcher.ScrollingHTMLView description;
    public QWidget page_2;
    public QGridLayout gridLayout4;
    public com.trolltech.launcher.ScrollingHTMLView source;
    public QVBoxLayout vboxLayout;
    public QGroupBox group_styles;
    public QVBoxLayout vboxLayout1;
    public QSpacerItem spacerItem;
    public QPushButton button_documentation;
    public QPushButton button_content;
    public QPushButton button_launch;

    public Ui_Launcher() { super(); }

    public void setupUi(QWidget Launcher)
    {
        Launcher.setObjectName("Launcher");
        Launcher.resize(new QSize(913, 708).expandedTo(Launcher.minimumSizeHint()));
        QSizePolicy sizePolicy = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(5));
        sizePolicy.setHorizontalStretch((byte)0);
        sizePolicy.setVerticalStretch((byte)0);
        sizePolicy.setHeightForWidth(Launcher.sizePolicy().hasHeightForWidth());
        Launcher.setSizePolicy(sizePolicy);
        QPalette palette= new QPalette();
        palette.setColor(QPalette.ColorGroup.Active, QPalette.ColorRole.WindowText, new QColor(0, 0, 0));
        palette.setColor(QPalette.ColorGroup.Active, QPalette.ColorRole.Button, new QColor(224, 223, 227));
        palette.setColor(QPalette.ColorGroup.Active, QPalette.ColorRole.Light, new QColor(255, 255, 255));
        palette.setColor(QPalette.ColorGroup.Active, QPalette.ColorRole.Midlight, new QColor(241, 239, 226));
        palette.setColor(QPalette.ColorGroup.Active, QPalette.ColorRole.Dark, new QColor(157, 157, 161));
        palette.setColor(QPalette.ColorGroup.Active, QPalette.ColorRole.Mid, new QColor(149, 149, 151));
        palette.setColor(QPalette.ColorGroup.Active, QPalette.ColorRole.Text, new QColor(0, 0, 0));
        palette.setColor(QPalette.ColorGroup.Active, QPalette.ColorRole.BrightText, new QColor(255, 255, 255));
        palette.setColor(QPalette.ColorGroup.Active, QPalette.ColorRole.ButtonText, new QColor(0, 0, 0));
        palette.setColor(QPalette.ColorGroup.Active, QPalette.ColorRole.Base, new QColor(255, 255, 255));
        palette.setColor(QPalette.ColorGroup.Active, QPalette.ColorRole.Window, new QColor(255, 255, 255));
        palette.setColor(QPalette.ColorGroup.Active, QPalette.ColorRole.Shadow, new QColor(113, 111, 100));
        palette.setColor(QPalette.ColorGroup.Active, QPalette.ColorRole.Highlight, new QColor(178, 180, 191));
        palette.setColor(QPalette.ColorGroup.Active, QPalette.ColorRole.HighlightedText, new QColor(0, 0, 0));
        palette.setColor(QPalette.ColorGroup.Active, QPalette.ColorRole.Link, new QColor(0, 0, 255));
        palette.setColor(QPalette.ColorGroup.Active, QPalette.ColorRole.LinkVisited, new QColor(255, 0, 255));
        palette.setColor(QPalette.ColorGroup.Active, QPalette.ColorRole.AlternateBase, new QColor(232, 232, 232));
        palette.setColor(QPalette.ColorGroup.Inactive, QPalette.ColorRole.WindowText, new QColor(0, 0, 0));
        palette.setColor(QPalette.ColorGroup.Inactive, QPalette.ColorRole.Button, new QColor(224, 223, 227));
        palette.setColor(QPalette.ColorGroup.Inactive, QPalette.ColorRole.Light, new QColor(255, 255, 255));
        palette.setColor(QPalette.ColorGroup.Inactive, QPalette.ColorRole.Midlight, new QColor(241, 239, 226));
        palette.setColor(QPalette.ColorGroup.Inactive, QPalette.ColorRole.Dark, new QColor(157, 157, 161));
        palette.setColor(QPalette.ColorGroup.Inactive, QPalette.ColorRole.Mid, new QColor(149, 149, 151));
        palette.setColor(QPalette.ColorGroup.Inactive, QPalette.ColorRole.Text, new QColor(0, 0, 0));
        palette.setColor(QPalette.ColorGroup.Inactive, QPalette.ColorRole.BrightText, new QColor(255, 255, 255));
        palette.setColor(QPalette.ColorGroup.Inactive, QPalette.ColorRole.ButtonText, new QColor(0, 0, 0));
        palette.setColor(QPalette.ColorGroup.Inactive, QPalette.ColorRole.Base, new QColor(255, 255, 255));
        palette.setColor(QPalette.ColorGroup.Inactive, QPalette.ColorRole.Window, new QColor(255, 255, 255));
        palette.setColor(QPalette.ColorGroup.Inactive, QPalette.ColorRole.Shadow, new QColor(113, 111, 100));
        palette.setColor(QPalette.ColorGroup.Inactive, QPalette.ColorRole.Highlight, new QColor(224, 223, 227));
        palette.setColor(QPalette.ColorGroup.Inactive, QPalette.ColorRole.HighlightedText, new QColor(0, 0, 0));
        palette.setColor(QPalette.ColorGroup.Inactive, QPalette.ColorRole.Link, new QColor(0, 0, 255));
        palette.setColor(QPalette.ColorGroup.Inactive, QPalette.ColorRole.LinkVisited, new QColor(255, 0, 255));
        palette.setColor(QPalette.ColorGroup.Inactive, QPalette.ColorRole.AlternateBase, new QColor(232, 232, 232));
        palette.setColor(QPalette.ColorGroup.Disabled, QPalette.ColorRole.WindowText, new QColor(112, 111, 113));
        palette.setColor(QPalette.ColorGroup.Disabled, QPalette.ColorRole.Button, new QColor(224, 223, 227));
        palette.setColor(QPalette.ColorGroup.Disabled, QPalette.ColorRole.Light, new QColor(255, 255, 255));
        palette.setColor(QPalette.ColorGroup.Disabled, QPalette.ColorRole.Midlight, new QColor(239, 239, 241));
        palette.setColor(QPalette.ColorGroup.Disabled, QPalette.ColorRole.Dark, new QColor(157, 157, 161));
        palette.setColor(QPalette.ColorGroup.Disabled, QPalette.ColorRole.Mid, new QColor(149, 149, 151));
        palette.setColor(QPalette.ColorGroup.Disabled, QPalette.ColorRole.Text, new QColor(112, 111, 113));
        palette.setColor(QPalette.ColorGroup.Disabled, QPalette.ColorRole.BrightText, new QColor(255, 255, 255));
        palette.setColor(QPalette.ColorGroup.Disabled, QPalette.ColorRole.ButtonText, new QColor(112, 111, 113));
        palette.setColor(QPalette.ColorGroup.Disabled, QPalette.ColorRole.Base, new QColor(255, 255, 255));
        palette.setColor(QPalette.ColorGroup.Disabled, QPalette.ColorRole.Window, new QColor(255, 255, 255));
        palette.setColor(QPalette.ColorGroup.Disabled, QPalette.ColorRole.Shadow, new QColor(0, 0, 0));
        palette.setColor(QPalette.ColorGroup.Disabled, QPalette.ColorRole.Highlight, new QColor(178, 180, 191));
        palette.setColor(QPalette.ColorGroup.Disabled, QPalette.ColorRole.HighlightedText, new QColor(0, 0, 0));
        palette.setColor(QPalette.ColorGroup.Disabled, QPalette.ColorRole.Link, new QColor(0, 0, 255));
        palette.setColor(QPalette.ColorGroup.Disabled, QPalette.ColorRole.LinkVisited, new QColor(255, 0, 255));
        palette.setColor(QPalette.ColorGroup.Disabled, QPalette.ColorRole.AlternateBase, new QColor(232, 232, 232));
        Launcher.setPalette(palette);
        gridLayout = new QGridLayout(Launcher);
        gridLayout.setSpacing(6);
        gridLayout.setMargin(9);
        gridLayout.setObjectName("gridLayout");
        group_main = new QGroupBox(Launcher);
        group_main.setObjectName("group_main");
        group_main.setFlat(true);
        gridLayout1 = new QGridLayout(group_main);
        gridLayout1.setSpacing(2);
        gridLayout1.setMargin(2);
        gridLayout1.setObjectName("gridLayout1");
        groupBox = new QGroupBox(group_main);
        groupBox.setObjectName("groupBox");
        groupBox.setFlat(false);
        gridLayout2 = new QGridLayout(groupBox);
        gridLayout2.setSpacing(6);
        gridLayout2.setMargin(9);
        gridLayout2.setObjectName("gridLayout2");
        list = new com.trolltech.launcher.CustomListView(groupBox);
        list.setObjectName("list");
        QSizePolicy sizePolicy1 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(1), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(7));
        sizePolicy1.setHorizontalStretch((byte)0);
        sizePolicy1.setVerticalStretch((byte)0);
        sizePolicy1.setHeightForWidth(list.sizePolicy().hasHeightForWidth());
        list.setSizePolicy(sizePolicy1);
        list.setFrameShape(com.trolltech.qt.gui.QFrame.Shape.NoFrame);
        list.setVerticalScrollBarPolicy(com.trolltech.qt.core.Qt.ScrollBarPolicy.ScrollBarAsNeeded);
        list.setHorizontalScrollBarPolicy(com.trolltech.qt.core.Qt.ScrollBarPolicy.ScrollBarAlwaysOff);

        gridLayout2.addWidget(list, 0, 0, 1, 1);


        gridLayout1.addWidget(groupBox, 0, 0, 1, 1);

        container = new QStackedWidget(group_main);
        container.setObjectName("container");
        container.setEnabled(true);
        QSizePolicy sizePolicy2 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(3), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(3));
        sizePolicy2.setHorizontalStretch((byte)1);
        sizePolicy2.setVerticalStretch((byte)0);
        sizePolicy2.setHeightForWidth(container.sizePolicy().hasHeightForWidth());
        container.setSizePolicy(sizePolicy2);
        page = new QWidget();
        page.setObjectName("page");
        gridLayout3 = new QGridLayout(page);
        gridLayout3.setSpacing(6);
        gridLayout3.setMargin(9);
        gridLayout3.setObjectName("gridLayout3");
        description = new com.trolltech.launcher.ScrollingHTMLView(page);
        description.setObjectName("description");

        gridLayout3.addWidget(description, 0, 0, 1, 1);

        container.addWidget(page);
        page_2 = new QWidget();
        page_2.setObjectName("page_2");
        gridLayout4 = new QGridLayout(page_2);
        gridLayout4.setSpacing(6);
        gridLayout4.setMargin(9);
        gridLayout4.setObjectName("gridLayout4");
        source = new com.trolltech.launcher.ScrollingHTMLView(page_2);
        source.setObjectName("source");
        QSizePolicy sizePolicy3 = new QSizePolicy(com.trolltech.qt.gui.QSizePolicy.Policy.resolve(13), com.trolltech.qt.gui.QSizePolicy.Policy.resolve(13));
        sizePolicy3.setHorizontalStretch((byte)0);
        sizePolicy3.setVerticalStretch((byte)0);
        sizePolicy3.setHeightForWidth(source.sizePolicy().hasHeightForWidth());
        source.setSizePolicy(sizePolicy3);

        gridLayout4.addWidget(source, 0, 0, 1, 1);

        container.addWidget(page_2);

        gridLayout1.addWidget(container, 0, 1, 1, 1);

        vboxLayout = new QVBoxLayout();
        vboxLayout.setSpacing(6);
        vboxLayout.setMargin(0);
        vboxLayout.setObjectName("vboxLayout");
        group_styles = new QGroupBox(group_main);
        group_styles.setObjectName("group_styles");
        vboxLayout1 = new QVBoxLayout(group_styles);
        vboxLayout1.setSpacing(6);
        vboxLayout1.setMargin(9);
        vboxLayout1.setObjectName("vboxLayout1");

        vboxLayout.addWidget(group_styles);

        spacerItem = new QSpacerItem(20, 40, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        vboxLayout.addItem(spacerItem);

        button_documentation = new QPushButton(group_main);
        button_documentation.setObjectName("button_documentation");
        button_documentation.setEnabled(true);

        vboxLayout.addWidget(button_documentation);

        button_content = new QPushButton(group_main);
        button_content.setObjectName("button_content");
        button_content.setEnabled(false);

        vboxLayout.addWidget(button_content);

        button_launch = new QPushButton(group_main);
        button_launch.setObjectName("button_launch");
        button_launch.setEnabled(false);

        vboxLayout.addWidget(button_launch);


        gridLayout1.addLayout(vboxLayout, 0, 2, 1, 1);


        gridLayout.addWidget(group_main, 0, 0, 1, 1);

        retranslateUi(Launcher);

        container.setCurrentIndex(0);


        Launcher.connectSlotsByName();
    } // setupUi

    void retranslateUi(QWidget Launcher)
    {
        Launcher.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("Launcher", "Form", null));
        group_main.setTitle(com.trolltech.qt.core.QCoreApplication.translate("Launcher", "Qt Jambi Example and Demo Launcher", null));
        groupBox.setTitle("");
        source.setHtml(com.trolltech.qt.core.QCoreApplication.translate("Launcher", "<html><head><meta name=\"qrichtext\" content=\"1\" /><style type=\"text/css\">\n"+
"p, li { white-space: pre-wrap; }\n"+
"</style></head><body style=\" font-family:'Sans Serif'; font-size:9pt; font-weight:400; font-style:normal; text-decoration:none;\">\n"+
"<p style=\" margin-top:0px; margin-bottom:0px; margin-left:0px; margin-right:0px; -qt-block-indent:0; text-indent:0px; font-family:'MS Shell Dlg 2'; font-size:8pt; font-style:italic;\">No source</p></body></html>", null));
        group_styles.setTitle(com.trolltech.qt.core.QCoreApplication.translate("Launcher", "Styles", null));
        button_documentation.setText(com.trolltech.qt.core.QCoreApplication.translate("Launcher", "Documentation", null));
        button_content.setText(com.trolltech.qt.core.QCoreApplication.translate("Launcher", "View Source", null));
        button_launch.setText(com.trolltech.qt.core.QCoreApplication.translate("Launcher", "Launch", null));
    } // retranslateUi

}

