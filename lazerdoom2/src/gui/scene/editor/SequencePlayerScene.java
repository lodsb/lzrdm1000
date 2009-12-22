package gui.scene.editor;

import gui.editor.SequencePlayerEditor;
import gui.editor.SynthesizerEditor;
import gui.item.editor.PushButton;
import gui.item.editor.Slider;
import gui.item.editor.TextLabel;
import gui.multitouch.TouchableGraphicsItemContainer;

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

public class SequencePlayerScene extends EditorScene {
	
	private TextLabel descriptionLabel;
	private PushButton previousQuantButton;
	private PushButton nextQuantButton;
	private PushButton enableLoopingButton;
	private TextLabel currentQuantizationLabel;
	
	public Signal0 prevPressed = new Signal0();
	public Signal0 nextPressed = new Signal0();
	public Signal0 loopEnablePressed = new Signal0();
	
	
	public void setCurrentQuantization(int beat, int measure) {
		if(beat != 0 && measure != 0) {
			this.currentQuantizationLabel.setText(beat+"/"+measure);
		} else {
			this.currentQuantizationLabel.setText("NONE");
		}
	}
	
	public void enablePrevButton(boolean enabled) {
		this.previousQuantButton.setButtonEnabled(enabled);
	}
	
	public void enableNextButton(boolean enabled) {
		this.nextQuantButton.setButtonEnabled(enabled);
	}
	
	public void setLoopModeEnabled(boolean enabled) {
		this.enableLoopingButton.setStateEnabled(enabled);
	}
	
    protected void drawBackground(QPainter painter, QRectF rect) {
        // Fill
        //QRadialGradient gradient = new QRadialGradient(0,0,rect.width());
        //gradient.setColorAt(0, QColor.black);
        //gradient.setColorAt(1, QColor.blue);
        painter.fillRect(rect, new QBrush(QColor.black));
    }
    
	public SequencePlayerScene(SequencePlayerEditor editor) {
		
		descriptionLabel = new TextLabel(editor, "Player quantization:");
		previousQuantButton = new PushButton(editor, "<");
		nextQuantButton = new PushButton(editor, ">");
		enableLoopingButton = new PushButton(editor, "loop: on", "loop: off", false);
		currentQuantizationLabel = new TextLabel(editor, "NONE");
		
		this.previousQuantButton.buttonPressed.connect(this.prevPressed);
		this.nextQuantButton.buttonPressed.connect(this.nextPressed);
		this.enableLoopingButton.buttonPressed.connect(this.loopEnablePressed);
		
		
		//this.setSceneRect(new QRectF(0,0,1000,1000));
		
		QGraphicsLinearLayout topLayout = new QGraphicsLinearLayout();
		topLayout.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);
		TouchableGraphicsItemContainer container = new TouchableGraphicsItemContainer();
		container.setItem(descriptionLabel);
		topLayout.addItem(container);
		
		
		QGraphicsLinearLayout midLayout = new QGraphicsLinearLayout();
		midLayout.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);
		container = new TouchableGraphicsItemContainer();
		container.setItem(previousQuantButton);
		midLayout.addItem(container);
		
		container = new TouchableGraphicsItemContainer();
		container.setItem(currentQuantizationLabel);
		midLayout.addItem(container);
		
		container = new TouchableGraphicsItemContainer();
		container.setItem(nextQuantButton);
		midLayout.addItem(container);

		
		QGraphicsLinearLayout bottomLayout = new QGraphicsLinearLayout();
		bottomLayout.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);
		container = new TouchableGraphicsItemContainer();
		container.setItem(enableLoopingButton);
		bottomLayout.addItem(container);
	
		
		QGraphicsLinearLayout layout = new QGraphicsLinearLayout();
		layout.setOrientation(com.trolltech.qt.core.Qt.Orientation.Vertical);
		
		layout.addItem(topLayout);
		layout.addItem(midLayout);
		layout.addItem(bottomLayout);
		layout.setMinimumWidth(400.0);
		
		QGraphicsWidget w = new QGraphicsWidget();
		w.setLayout(layout);
		this.addItem(w);
		w.show();
		w.setPos(0, 0);
		this.setSceneRect(new QRectF(0,0,800,400));
		//this.setSceneRect(new QRectF(0,0,w.size().width(), w.size().height()));
		w.scale(2.0, 2.0);
	}
}
