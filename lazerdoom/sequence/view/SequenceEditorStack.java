package sequence.view;

import sequence.view.types.DoublePointSequenceEditor;

import com.trolltech.qt.gui.QFrame;
import com.trolltech.qt.gui.QScrollArea;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QLayout.SizeConstraint;

public class SequenceEditorStack extends QScrollArea {
	QVBoxLayout layout = new QVBoxLayout();
	QFrame containerWidget = new QFrame();
	
	public SequenceEditorStack() {
		containerWidget.setLayout(layout);
		layout.setSizeConstraint(SizeConstraint.SetMinAndMaxSize);
		this.setWidget(containerWidget);
		containerWidget.show();
	}
	
	@SuppressWarnings("unchecked")
	public void addSequenceEditor(GenericSequenceEditor editor) {
		layout.addWidget(editor);
		containerWidget.adjustSize();

	}
}
