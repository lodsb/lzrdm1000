package GUI.Scene.Editor;

import GUI.Editor.SynthesizerEditor;
import GUI.Item.Editor.PushButton;
import GUI.Item.Editor.Slider;
import GUI.Item.Editor.TextLabel;
import GUI.Multitouch.TouchableGraphicsItemContainer;
import SceneItems.TouchableGraphicsItemLayout;

import com.sun.java.swing.plaf.gtk.GTKConstants.Orientation;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QFrame;
import com.trolltech.qt.gui.QGraphicsLayoutItem;
import com.trolltech.qt.gui.QGraphicsLinearLayout;
import com.trolltech.qt.gui.QGraphicsProxyWidget;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QGraphicsWidget;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QRadialGradient;

public class SynthesizerScene extends EditorScene {
	
	private TextLabel currentSynthLabel;
	private PushButton previousSynthButton;
	private PushButton nextSynthButton;
	private TextLabel synthChoiceLabel;
	private TextLabel synthChoiceDescriptionLabel;
	
	private PushButton loadSynthInstanceButton;
	
	public Signal0 prevPressed = new Signal0();
	public Signal0 nextPressed = new Signal0();
	public Signal0 loadSynthPressed = new Signal0();
	
	
	public void setCurrentSynth(String name) {
		this.currentSynthLabel.setText("Synthesizer: "+name);
	}
	
	public void setCurrentSelectionSynthname(String name) {
		this.synthChoiceLabel.setText(name);
	}
	
	public void setCurrentSelectionDescription(String name) {
		this.synthChoiceDescriptionLabel.setText(name);
	}

	
	public void enablePrevButton(boolean enabled) {
		this.previousSynthButton.setButtonEnabled(enabled);
	}
	
	public void enableNextButton(boolean enabled) {
		this.nextSynthButton.setButtonEnabled(enabled);
	}
	
    protected void drawBackground(QPainter painter, QRectF rect) {
        // Fill
        //QRadialGradient gradient = new QRadialGradient(0,0,rect.width());
        //gradient.setColorAt(0, QColor.black);
        //gradient.setColorAt(1, QColor.blue);
        painter.fillRect(rect, new QBrush(QColor.black));
    }
    
	public SynthesizerScene(SynthesizerEditor editor) {
		
		currentSynthLabel = new TextLabel(editor, "Synthesizer: NONE");
		previousSynthButton = new PushButton(editor, "<");
		nextSynthButton = new PushButton(editor, ">");
		synthChoiceLabel = new TextLabel(editor, "         ");
		synthChoiceDescriptionLabel = new TextLabel(editor, "");
		loadSynthInstanceButton = new PushButton(editor, "load synth");
		
		this.previousSynthButton.buttonPressed.connect(this.prevPressed);
		this.nextSynthButton.buttonPressed.connect(this.nextPressed);
		this.loadSynthInstanceButton.buttonPressed.connect(this.loadSynthPressed);
		
		
		this.setSceneRect(new QRectF(0,0,1000,1000));
		
		QGraphicsLinearLayout topLayout = new QGraphicsLinearLayout();
		topLayout.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);
		TouchableGraphicsItemContainer container = new TouchableGraphicsItemContainer();
		container.setItem(currentSynthLabel);
		topLayout.addItem(container);
		
		
		QGraphicsLinearLayout midLayout = new QGraphicsLinearLayout();
		midLayout.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);
		container = new TouchableGraphicsItemContainer();
		container.setItem(previousSynthButton);
		midLayout.addItem(container);
		
		container = new TouchableGraphicsItemContainer();
		container.setItem(synthChoiceLabel);
		midLayout.addItem(container);
		
		container = new TouchableGraphicsItemContainer();
		container.setItem(nextSynthButton);
		midLayout.addItem(container);
		
		QGraphicsLinearLayout midLayout2 = new QGraphicsLinearLayout();
		midLayout2.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);
		container = new TouchableGraphicsItemContainer();
		container.setItem(synthChoiceDescriptionLabel);
		midLayout2.addItem(container);
		
		
		QGraphicsLinearLayout bottomLayout = new QGraphicsLinearLayout();
		bottomLayout.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);
		container = new TouchableGraphicsItemContainer();
		container.setItem(loadSynthInstanceButton);
		bottomLayout.addItem(container);
		
		QGraphicsLinearLayout layout = new QGraphicsLinearLayout();
		layout.setOrientation(com.trolltech.qt.core.Qt.Orientation.Vertical);
		
		layout.addItem(topLayout);
		layout.addItem(midLayout);
		layout.addItem(midLayout2);
		layout.addItem(bottomLayout);
		layout.setMinimumWidth(400.0);
		
		QGraphicsWidget w = new QGraphicsWidget();
		w.setLayout(layout);
		this.addItem(w);
		w.show();
		w.setPos(0, 0);
		//this.setSceneRect(new QRectF(0,0,w.size().width(), w.size().height()));
		w.scale(2.0, 2.0);
	}
}
