/********************************************************************************
** Form generated from reading ui file 'AboutImageViewerUI.jui'
**
** Created: Tue Mar 10 10:27:05 2009
**      by: Qt User Interface Compiler version 4.5.0
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package com.trolltech.demos.imageviewer;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_AboutImageViewer implements com.trolltech.qt.QUiForm<QDialog>
{
    public QGridLayout gridLayout;
    public QGridLayout gridLayout1;
    public QLabel label;
    public QTextEdit textEdit;
    public QSpacerItem spacerItem;
    public QSpacerItem spacerItem1;
    public QPushButton okButton;
    public QSpacerItem spacerItem2;

    public Ui_AboutImageViewer() { super(); }

    public void setupUi(QDialog AboutImageViewer)
    {
        AboutImageViewer.setObjectName("AboutImageViewer");
        AboutImageViewer.resize(new QSize(445, 307).expandedTo(AboutImageViewer.minimumSizeHint()));
        AboutImageViewer.setAutoFillBackground(true);
        gridLayout = new QGridLayout(AboutImageViewer);
        gridLayout.setObjectName("gridLayout");
        gridLayout1 = new QGridLayout();
        gridLayout1.setObjectName("gridLayout1");
        label = new QLabel(AboutImageViewer);
        label.setObjectName("label");
        label.setPixmap(new QPixmap(("classpath:production/Qt Jambi (rc)/com/trolltech/images/qt-logo.png")));
        label.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignHorizontal_Mask,com.trolltech.qt.core.Qt.AlignmentFlag.AlignLeft,com.trolltech.qt.core.Qt.AlignmentFlag.AlignTop,com.trolltech.qt.core.Qt.AlignmentFlag.AlignVertical_Mask));

        gridLayout1.addWidget(label, 0, 0, 1, 1);

        textEdit = new QTextEdit(AboutImageViewer);
        textEdit.setObjectName("textEdit");
        QPalette palette= new QPalette();
        palette.setColor(QPalette.ColorGroup.Active, QPalette.ColorRole.Base, new QColor(224, 223, 227));
        palette.setColor(QPalette.ColorGroup.Inactive, QPalette.ColorRole.Base, new QColor(224, 223, 227));
        palette.setColor(QPalette.ColorGroup.Disabled, QPalette.ColorRole.Base, new QColor(224, 223, 227));
        textEdit.setPalette(palette);
        textEdit.setAcceptDrops(true);
        textEdit.setFrameShape(com.trolltech.qt.gui.QFrame.Shape.Box);
        textEdit.setUndoRedoEnabled(false);
        textEdit.setReadOnly(true);

        gridLayout1.addWidget(textEdit, 0, 1, 2, 1);

        spacerItem = new QSpacerItem(20, 221, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        gridLayout1.addItem(spacerItem, 1, 0, 1, 1);


        gridLayout.addLayout(gridLayout1, 0, 0, 1, 3);

        spacerItem1 = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        gridLayout.addItem(spacerItem1, 1, 0, 1, 1);

        okButton = new QPushButton(AboutImageViewer);
        okButton.setObjectName("okButton");

        gridLayout.addWidget(okButton, 1, 1, 1, 1);

        spacerItem2 = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum);

        gridLayout.addItem(spacerItem2, 1, 2, 1, 1);

        retranslateUi(AboutImageViewer);
        okButton.clicked.connect(AboutImageViewer, "accept()");

        AboutImageViewer.connectSlotsByName();
    } // setupUi

    void retranslateUi(QDialog AboutImageViewer)
    {
        label.setText("");
        textEdit.setHtml(com.trolltech.qt.core.QCoreApplication.translate("AboutImageViewer", "<html><head><meta name=\"qrichtext\" content=\"1\" /><title>Image Viewer</title><style type=\"text/css\">\n"+
"p, li { white-space: pre-wrap; }\n"+
"</style></head><body style=\" font-family:'MS Shell Dlg 2'; font-size:8.25pt; font-weight:400; font-style:normal;\">\n"+
"<p align=\"center\" style=\" margin-top:16px; margin-bottom:12px; margin-left:0px; margin-right:0px; -qt-block-indent:0; text-indent:0px; font-size:10pt; font-weight:600;\"><span style=\" font-size:x-large;\">Image Viewer</span></p>\n"+
"<p style=\" margin-top:12px; margin-bottom:12px; margin-left:0px; margin-right:0px; -qt-block-indent:0; text-indent:0px; font-size:10pt;\">This application was written to show the various aspects of Qt that can be used from Java, such as:</p>\n"+
"<ul style=\"-qt-list-indent: 1;\"><li style=\" font-size:10pt;\" style=\" margin-top:12px; margin-bottom:0px; margin-left:0px; margin-right:0px; -qt-block-indent:0; text-indent:0px;\">Qt Designer and generated user interfaces.</li>\n"+
"<li style=\" font-size:10pt;\" style=\" margin-top:0px; margin-bottom:0px; margin-left:0px; margin-right:0px; -qt-block-indent:0; text-indent:0px;\">Convenient default models for the item view framework, such as the <span style=\" font-family:'Courier New';\">QDirModel</span>, which shows the local filesystem in a<span style=\" font-family:'Courier New';\"> QTreeView</span>.</li>\n"+
"<li style=\" font-size:10pt;\" style=\" margin-top:0px; margin-bottom:0px; margin-left:0px; margin-right:0px; -qt-block-indent:0; text-indent:0px;\">Main window and docking architecture  the core of any application.</li>\n"+
"<li style=\" font-size:10pt;\" style=\" margin-top:0px; margin-bottom:0px; margin-left:0px; margin-right:0px; -qt-block-indent:0; text-indent:0px;\">Signals and Slots  Qt's intercomponent communication architecture with support for the following features:</li>\n"+
"<ul type=\"circle\" style=\"-qt-list-indent: 2;\"><li style=\" font-size:10pt;\" style=\" margin-top:0px; margin-bottom:0px; margin-left:0px; margin-right:0px; -qt-block-indent:0; text-indent:0px;\">Queued connections that provide a powerful mechanism for threaded programming.</li>\n"+
"<li style=\" font-size:10pt;\" style=\" margin-top:0px; margin-bottom:0px; margin-left:0px; margin-right:0px; -qt-block-indent:0; text-indent:0px;\">Automatic connections based on naming conventions.</li></ul>\n"+
"<li style=\" font-size:10pt;\" style=\" margin-top:0px; margin-bottom:12px; margin-left:0px; margin-right:0px; -qt-block-indent:0; text-indent:0px;\">The overall power and flexibility of the Qt API.</li></ul></body></html>", null));
        okButton.setText(com.trolltech.qt.core.QCoreApplication.translate("AboutImageViewer", "OK", null));
    } // retranslateUi

}

