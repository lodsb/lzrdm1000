package sequence.view;

import sequence.view.form.EditorViewPropertiesBox;
import sequence.view.form.ListSelectionWidget;
import sequence.view.types.DoublePointSequenceEditor;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.gui.QLayout.SizeConstraint;

public class GenericSequenceEditor<P,T> extends QWidget {
	
	private QGridLayout layout;
	
	// side panel
	private QFrame sidePanel;
	private QToolBox toolBox;
	private QPushButton compactifyButton;
	
	private ListSelectionWidget routingForm;
	private ListSelectionWidget sequenceListForm;
	
	private GenericSequenceViewWidget<P,T> sequenceView;
	
	private QWidget toolsWidget;
	private QVBoxLayout toolsLayout;
	


	protected GenericSequenceViewWidget<P,T> getSequenceViewWidget() {
		return sequenceView;
	}
	
    public static void main(String[] args) {
        QApplication.initialize(args);
        QApplication.setStyle(new QPlastiqueStyle());

        DoublePointSequenceEditor testGenericSequenceEditor = new DoublePointSequenceEditor();
        testGenericSequenceEditor.show();

        QApplication.exec();
    }

    public void addToolBoxWidget(QWidget widget) {
    	toolsLayout.addWidget(widget);
    }
    
    public void init() {
    	layout = new QGridLayout();
    	
    	createSequenceView();
    	layout.addWidget(sequenceView, 0, 0);
    	createSidePanel();
    	layout.addWidget(sidePanel, 0, 1);
    	
    	//layout.setColumnStretch(0, 70);
    	//layout.setColumnStretch(1, 30);
    	
    	this.setLayout(layout);
    	
    	sequenceView.mouseAtScenePos.connect(this, "mouseAtScenePos(QPointF)");
    	
    	//compactifyButton.clicked.connect(this, "compactifyButtonClicked()");
    }

    private void createSequenceView() {
    	sequenceView = new GenericSequenceViewWidget<P,T>(this);
    }
    
   /* private void compactifyButtonClicked() {
    	if(compactifyButton.isChecked()) {
    		sequenceView.setCompactView(true);
    	} else {
    		sequenceView.setCompactView(false);
    	}
    }
    */
    
    private void showToolBoxButtonClicked(Boolean show) {
    	if(show) {
    		toolBox.show();
    	} else {
    		toolBox.hide();
    	}
    }
    
    private void showHorizontalRulerClicked(Boolean show) {
    	sequenceView.setShowHorizontalRuler(show);
    }
    
    private void showVerticalRulerClicked(Boolean show) {
    	sequenceView.setShowVerticalRuler(show);
    }

    private void showViewInfoWidgetClicked(Boolean show) {
    	sequenceView.showViewInfoWidget(show);
    }

    
    
    protected void mouseAtScenePos(QPointF pos) {
    
    }
    
    private void createSidePanel() {
    	sidePanel = new QFrame(this);
    	QHBoxLayout sidePanelLayout = new QHBoxLayout();
    	sidePanel.setLayout(sidePanelLayout);
    	sidePanelLayout.setSizeConstraint(SizeConstraint.SetMinAndMaxSize);
    	
    	
    	toolBox = new QToolBox(this);
    	toolBox.setMaximumWidth(200);
    	
    	
    	toolsWidget = new QWidget(this);
    	toolsLayout = new QVBoxLayout();
    	toolsWidget.setLayout(toolsLayout);
    	toolsLayout.setSizeConstraint(SizeConstraint.SetMinAndMaxSize);
    	
    	/*
    	compactifyButton = new QPushButton(tr("Compact view"),this);
    	compactifyButton.setCheckable(true);
    	toolsLayout.addWidget(compactifyButton);
    	*/
    	
    	
    	sequenceListForm = new ListSelectionWidget(this, tr("Sequences"), tr("Sequence"), tr("Shown sequences"));
    	routingForm = new ListSelectionWidget(this, tr("Routing"), tr("Output"), tr("Possible Connections"));
    	
    	toolBox.addItem(toolsWidget, tr("Tools"));
    	toolBox.addItem(sequenceListForm, tr("Sequences"));
    	toolBox.addItem(routingForm, tr("Routing"));
    	
    	
    	EditorViewPropertiesBox editorViewPropertiesBox = new EditorViewPropertiesBox();
    	
    	editorViewPropertiesBox.showToolBoxClicked.connect(this, "showToolBoxButtonClicked(Boolean)");
    	editorViewPropertiesBox.showHorizontalRulerClicked.connect(this, "showHorizontalRulerClicked(Boolean)");
    	editorViewPropertiesBox.showVerticalRulerClicked.connect(this, "showVerticalRulerClicked(Boolean)");
    	editorViewPropertiesBox.showViewInfoWidgetClicked.connect(this, "showViewInfoWidgetClicked(Boolean)");
    	
    	sidePanelLayout.addWidget(toolBox);
    	sidePanelLayout.addWidget(editorViewPropertiesBox);
    	
    	
    }
   
    public GenericSequenceEditor(QWidget parent) {
    	super(parent);
    	
    	init();
    }
    
    public GenericSequenceEditor() {
    	init();
    }
}
