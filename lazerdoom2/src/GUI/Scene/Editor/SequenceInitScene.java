package GUI.Scene.Editor;

import GUI.Editor.SequencePlayerEditor;
import GUI.Editor.SynthesizerEditor;
import GUI.Editor.Commands.SequenceEditor;
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

public class SequenceInitScene extends EditorScene {
	
	private PushButton previousSequenceTypeButton;
	private PushButton nextSequenceTypeButton;
	private PushButton createSequenceButton;
	private TextLabel currentSequenceTypeLabel;
	
	public Signal0 prevPressed = new Signal0();
	public Signal0 nextPressed = new Signal0();
	
	
	public void setCurrentSequenceType(String name) {
		this.currentSequenceTypeLabel.setText(name);
	}
	public void enablePrevButton(boolean enabled) {
		this.previousSequenceTypeButton.setButtonEnabled(enabled);
	}
	
	public void enableNextButton(boolean enabled) {
		this.nextSequenceTypeButton.setButtonEnabled(enabled);
	}
	
    protected void drawBackground(QPainter painter, QRectF rect) {
        // Fill
        //QRadialGradient gradient = new QRadialGradient(0,0,rect.width());
        //gradient.setColorAt(0, QColor.black);
        //gradient.setColorAt(1, QColor.blue);
        painter.fillRect(rect, new QBrush(QColor.black));
    }
    
	public SequenceInitScene(SequenceEditor editor) {
		
		previousSequenceTypeButton = new PushButton(editor, "<");
		nextSequenceTypeButton = new PushButton(editor, ">");
		currentSequenceTypeLabel = new TextLabel(editor, "Pause");
		createSequenceButton = new PushButton(editor, "create Sequence");
		
		this.previousSequenceTypeButton.buttonPressed.connect(this.prevPressed);
		this.nextSequenceTypeButton.buttonPressed.connect(this.nextPressed);
		
		
		//this.setSceneRect(new QRectF(0,0,1000,1000));		
		
		QGraphicsLinearLayout midLayout = new QGraphicsLinearLayout();
		midLayout.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);
		TouchableGraphicsItemContainer container = new TouchableGraphicsItemContainer();
		container.setItem(previousSequenceTypeButton);
		midLayout.addItem(container);
		
		container = new TouchableGraphicsItemContainer();
		container.setItem(currentSequenceTypeLabel);
		midLayout.addItem(container);
		
		container = new TouchableGraphicsItemContainer();
		container.setItem(nextSequenceTypeButton);
		midLayout.addItem(container);


		QGraphicsLinearLayout bottomLayout = new QGraphicsLinearLayout();
		bottomLayout.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);
		container = new TouchableGraphicsItemContainer();
		container.setItem(createSequenceButton);
		bottomLayout.addItem(container);
		
		QGraphicsLinearLayout layout = new QGraphicsLinearLayout();
		layout.setOrientation(com.trolltech.qt.core.Qt.Orientation.Vertical);
		
		layout.addItem(midLayout);
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
