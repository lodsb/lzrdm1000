package sequence.view;

import sequence.view.form.ListSelectionWidget;
import sequence.view.types.DoublePointSequenceEditor;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.gui.*;

public class GenericSequenceEditor<P,T> extends QWidget {
	
	private QGridLayout layout;
	
	// side panel
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
    	layout.addWidget(toolBox, 0, 1);
    	
    	layout.setColumnStretch(0, 70);
    	layout.setColumnStretch(1, 30);
    	
    	this.setLayout(layout);
    	
    	sequenceView.mouseAtScenePos.connect(this, "mouseAtScenePos(QPointF)");
    	
    	compactifyButton.clicked.connect(this, "compactifyButtonClicked()");
    }

    private void createSequenceView() {
    	sequenceView = new GenericSequenceViewWidget<P,T>(this);
    }
    
    private void compactifyButtonClicked() {
    	if(compactifyButton.isChecked()) {
    		sequenceView.setCompactView(true);
    	} else {
    		sequenceView.setCompactView(false);
    	}
    }
    
    protected void mouseAtScenePos(QPointF pos) {
    
    }
    
    private void createSidePanel() {
    	toolBox = new QToolBox(this);
    	
    	toolsWidget = new QWidget(this);
    	toolsLayout = new QVBoxLayout();
    	toolsWidget.setLayout(toolsLayout);
    	
    	compactifyButton = new QPushButton(tr("Compact view"),this);
    	compactifyButton.setCheckable(true);
    	
    	toolsLayout.addWidget(compactifyButton);
    	
    	sequenceListForm = new ListSelectionWidget(this, tr("Sequences"), tr("Sequence"), tr("Shown sequences"));
    	routingForm = new ListSelectionWidget(this, tr("Routing"), tr("Output"), tr("Possible Connections"));
    	
    	toolBox.addItem(toolsWidget, tr("Tools"));
    	toolBox.addItem(sequenceListForm, tr("Sequences"));
    	toolBox.addItem(routingForm, tr("Routing"));
    	
    }
   
    public GenericSequenceEditor(QWidget parent) {
    	super(parent);
    	
    	init();
    }
    
    public GenericSequenceEditor() {
    	init();
    }
}
