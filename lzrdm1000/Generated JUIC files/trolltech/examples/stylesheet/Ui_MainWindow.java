/********************************************************************************
** Form generated from reading ui file 'mainwindow.jui'
**
** Created: Di Apr 14 23:02:41 2009
**      by: Qt User Interface Compiler version 4.4.2
**
** WARNING! All changes made in this file will be lost when recompiling ui file!
********************************************************************************/

package trolltech.examples.stylesheet;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

public class Ui_MainWindow
{
    public QAction exitAction;
    public QAction aboutQtAction;
    public QAction editStyleAction;
    public QAction aboutAction;
    public QAction aboutQtJambiAction;
    public QWidget centralwidget;
    public QVBoxLayout vboxLayout;
    public QFrame mainFrame;
    public QGridLayout gridLayout;
    public QCheckBox agreeCheckBox;
    public QLabel label;
    public QLabel nameLabel;
    public QRadioButton maleRadioButton;
    public QLabel passwordLabel;
    public QComboBox countryCombo;
    public QLabel ageLabel;
    public QLabel countryLabel;
    public QLabel genderLabel;
    public QLineEdit passwordEdit;
    public QRadioButton femaleRadioButton;
    public QSpinBox ageSpinBox;
    public QComboBox nameCombo;
    public QSpacerItem spacerItem;
    public QSpacerItem spacerItem1;
    public QDialogButtonBox buttonBox;
    public QListWidget professionList;
    public QMenuBar menubar;
    public QMenu menu_File;
    public QMenu menu_Help;
    public QStatusBar statusbar;

    public Ui_MainWindow() { super(); }

    public void setupUi(QMainWindow MainWindow)
    {
        MainWindow.setObjectName("MainWindow");
        MainWindow.resize(new QSize(400, 413).expandedTo(MainWindow.minimumSizeHint()));
        exitAction = new QAction(MainWindow);
        exitAction.setObjectName("exitAction");
        aboutQtAction = new QAction(MainWindow);
        aboutQtAction.setObjectName("aboutQtAction");
        editStyleAction = new QAction(MainWindow);
        editStyleAction.setObjectName("editStyleAction");
        aboutAction = new QAction(MainWindow);
        aboutAction.setObjectName("aboutAction");
        aboutQtJambiAction = new QAction(MainWindow);
        aboutQtJambiAction.setObjectName("aboutQtJambiAction");
        centralwidget = new QWidget(MainWindow);
        centralwidget.setObjectName("centralwidget");
        vboxLayout = new QVBoxLayout(centralwidget);
        vboxLayout.setSpacing(6);
        vboxLayout.setObjectName("vboxLayout");
        mainFrame = new QFrame(centralwidget);
        mainFrame.setObjectName("mainFrame");
        mainFrame.setFrameShape(com.trolltech.qt.gui.QFrame.Shape.StyledPanel);
        mainFrame.setFrameShadow(com.trolltech.qt.gui.QFrame.Shadow.Raised);
        gridLayout = new QGridLayout(mainFrame);
        gridLayout.setSpacing(6);
        gridLayout.setObjectName("gridLayout");
        agreeCheckBox = new QCheckBox(mainFrame);
        agreeCheckBox.setObjectName("agreeCheckBox");

        gridLayout.addWidget(agreeCheckBox, 6, 0, 1, 5);

        label = new QLabel(mainFrame);
        label.setObjectName("label");
        label.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignRight,com.trolltech.qt.core.Qt.AlignmentFlag.AlignTop));

        gridLayout.addWidget(label, 5, 0, 1, 1);

        nameLabel = new QLabel(mainFrame);
        nameLabel.setObjectName("nameLabel");
        nameLabel.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignRight,com.trolltech.qt.core.Qt.AlignmentFlag.AlignVCenter));

        gridLayout.addWidget(nameLabel, 0, 0, 1, 1);

        maleRadioButton = new QRadioButton(mainFrame);
        maleRadioButton.setObjectName("maleRadioButton");

        gridLayout.addWidget(maleRadioButton, 1, 1, 1, 1);

        passwordLabel = new QLabel(mainFrame);
        passwordLabel.setObjectName("passwordLabel");
        passwordLabel.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignRight,com.trolltech.qt.core.Qt.AlignmentFlag.AlignVCenter));

        gridLayout.addWidget(passwordLabel, 3, 0, 1, 1);

        countryCombo = new QComboBox(mainFrame);
        countryCombo.setObjectName("countryCombo");

        gridLayout.addWidget(countryCombo, 4, 1, 1, 4);

        ageLabel = new QLabel(mainFrame);
        ageLabel.setObjectName("ageLabel");
        ageLabel.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignRight,com.trolltech.qt.core.Qt.AlignmentFlag.AlignVCenter));

        gridLayout.addWidget(ageLabel, 2, 0, 1, 1);

        countryLabel = new QLabel(mainFrame);
        countryLabel.setObjectName("countryLabel");
        countryLabel.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignRight,com.trolltech.qt.core.Qt.AlignmentFlag.AlignVCenter));

        gridLayout.addWidget(countryLabel, 4, 0, 1, 1);

        genderLabel = new QLabel(mainFrame);
        genderLabel.setObjectName("genderLabel");
        genderLabel.setAlignment(com.trolltech.qt.core.Qt.AlignmentFlag.createQFlags(com.trolltech.qt.core.Qt.AlignmentFlag.AlignRight,com.trolltech.qt.core.Qt.AlignmentFlag.AlignVCenter));

        gridLayout.addWidget(genderLabel, 1, 0, 1, 1);

        passwordEdit = new QLineEdit(mainFrame);
        passwordEdit.setObjectName("passwordEdit");
        passwordEdit.setEchoMode(com.trolltech.qt.gui.QLineEdit.EchoMode.Password);

        gridLayout.addWidget(passwordEdit, 3, 1, 1, 4);

        femaleRadioButton = new QRadioButton(mainFrame);
        femaleRadioButton.setObjectName("femaleRadioButton");

        gridLayout.addWidget(femaleRadioButton, 1, 2, 1, 2);

        ageSpinBox = new QSpinBox(mainFrame);
        ageSpinBox.setObjectName("ageSpinBox");
        ageSpinBox.setMinimum(12);
        ageSpinBox.setValue(22);

        gridLayout.addWidget(ageSpinBox, 2, 1, 1, 2);

        nameCombo = new QComboBox(mainFrame);
        nameCombo.setObjectName("nameCombo");
        nameCombo.setEditable(true);

        gridLayout.addWidget(nameCombo, 0, 1, 1, 4);

        spacerItem = new QSpacerItem(40, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        gridLayout.addItem(spacerItem, 1, 4, 1, 1);

        spacerItem1 = new QSpacerItem(61, 20, com.trolltech.qt.gui.QSizePolicy.Policy.Minimum, com.trolltech.qt.gui.QSizePolicy.Policy.Expanding);

        gridLayout.addItem(spacerItem1, 2, 3, 1, 2);

        buttonBox = new QDialogButtonBox(mainFrame);
        buttonBox.setObjectName("buttonBox");
        buttonBox.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);
        buttonBox.setStandardButtons(com.trolltech.qt.gui.QDialogButtonBox.StandardButton.createQFlags(com.trolltech.qt.gui.QDialogButtonBox.StandardButton.Cancel,com.trolltech.qt.gui.QDialogButtonBox.StandardButton.NoButton,com.trolltech.qt.gui.QDialogButtonBox.StandardButton.Ok));

        gridLayout.addWidget(buttonBox, 7, 3, 1, 2);

        professionList = new QListWidget(mainFrame);
        professionList.setObjectName("professionList");

        gridLayout.addWidget(professionList, 5, 1, 1, 4);


        vboxLayout.addWidget(mainFrame);

        MainWindow.setCentralWidget(centralwidget);
        menubar = new QMenuBar(MainWindow);
        menubar.setObjectName("menubar");
        menubar.setGeometry(new QRect(0, 0, 400, 21));
        menu_File = new QMenu(menubar);
        menu_File.setObjectName("menu_File");
        menu_Help = new QMenu(menubar);
        menu_Help.setObjectName("menu_Help");
        MainWindow.setMenuBar(menubar);
        statusbar = new QStatusBar(MainWindow);
        statusbar.setObjectName("statusbar");
        MainWindow.setStatusBar(statusbar);
        nameLabel.setBuddy(nameCombo);
        passwordLabel.setBuddy(passwordEdit);
        ageLabel.setBuddy(ageSpinBox);

        menubar.addAction(menu_File.menuAction());
        menubar.addAction(menu_Help.menuAction());
        menu_File.addAction(editStyleAction);
        menu_File.addSeparator();
        menu_File.addAction(exitAction);
        menu_Help.addAction(aboutAction);
        menu_Help.addSeparator();
        menu_Help.addAction(aboutQtJambiAction);
        menu_Help.addAction(aboutQtAction);
        retranslateUi(MainWindow);

        countryCombo.setCurrentIndex(6);
        professionList.setCurrentRow(0);


        MainWindow.connectSlotsByName();
    } // setupUi

    void retranslateUi(QMainWindow MainWindow)
    {
        MainWindow.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Style Sheet"));
        exitAction.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "&Exit"));
        aboutQtAction.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "About Q&t"));
        editStyleAction.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Edit &Style..."));
        aboutAction.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "About"));
        aboutQtJambiAction.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "About &Qt Jambi"));
        agreeCheckBox.setToolTip(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Please read the LICENSE file before checking"));
        agreeCheckBox.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "I &accept the terms and &conditions"));
        label.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Profession:"));
        nameLabel.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "&Name:"));
        maleRadioButton.setToolTip(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Check this if you are male"));
        maleRadioButton.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "&Male"));
        passwordLabel.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "&Password:"));
        countryCombo.clear();
        countryCombo.addItem(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Egypt"));
        countryCombo.addItem(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "France"));
        countryCombo.addItem(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Germany"));
        countryCombo.addItem(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "India"));
        countryCombo.addItem(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Italy"));
        countryCombo.addItem(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Norway"));
        countryCombo.addItem(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Pakistan"));
        countryCombo.setToolTip(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Specify country of origin"));
        countryCombo.setStatusTip(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Specify country of origin"));
        ageLabel.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "&Age:"));
        countryLabel.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Country:"));
        genderLabel.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Gender:"));
        passwordEdit.setToolTip(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Specify your password"));
        passwordEdit.setStatusTip(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Specify your password"));
        passwordEdit.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Password"));
        femaleRadioButton.setStyleSheet(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Check this if you are female"));
        femaleRadioButton.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "&Female"));
        ageSpinBox.setToolTip(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Specify your age"));
        ageSpinBox.setStatusTip(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Specify your age"));
        nameCombo.setToolTip(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Specify your name"));
        professionList.clear();

        QListWidgetItem __item = new QListWidgetItem(professionList);
        __item.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Developer"));

        QListWidgetItem __item1 = new QListWidgetItem(professionList);
        __item1.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Student"));

        QListWidgetItem __item2 = new QListWidgetItem(professionList);
        __item2.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Fisherman"));
        professionList.setToolTip(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Select your profession"));
        professionList.setStatusTip(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Specify your name here"));
        professionList.setWhatsThis(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Specify your name here"));
        menu_File.setTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "&File"));
        menu_Help.setTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "&Help"));
    } // retranslateUi

}

