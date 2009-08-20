package GUI.Scene.Editor;

import GUI.Item.Editor.PushButton;
import GUI.Item.Editor.Slider;
import GUI.Item.Editor.TextLabel;
import GUI.Multitouch.TouchableGraphicsItemContainer;
import SceneItems.TouchableGraphicsItemLayout;

import com.sun.java.swing.plaf.gtk.GTKConstants.Orientation;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.gui.QFrame;
import com.trolltech.qt.gui.QGraphicsLayoutItem;
import com.trolltech.qt.gui.QGraphicsLinearLayout;
import com.trolltech.qt.gui.QGraphicsProxyWidget;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QGraphicsWidget;

public class SynthesizerScene extends QGraphicsScene {
	public SynthesizerScene() {
		this.setSceneRect(new QRectF(0,0,1000,1000));
		QGraphicsLinearLayout layout = new QGraphicsLinearLayout();
		layout.setOrientation(com.trolltech.qt.core.Qt.Orientation.Horizontal);
		PushButton button = new PushButton("hello");
		Slider button1 = new Slider();
		TextLabel button2 = new TextLabel("shit");
		TouchableGraphicsItemContainer container = new TouchableGraphicsItemContainer();
		container.setItem(button);
		layout.addItem(container);
		container = new TouchableGraphicsItemContainer();
		container.setItem(button1);
		layout.addItem(container);
		container = new TouchableGraphicsItemContainer();
		container.setItem(button2);
		layout.addItem(container);
		
		QGraphicsWidget w = new QGraphicsWidget();
		w.setLayout(layout);
		this.addItem(w);
		
		//QGraphicsLayoutItem l = new QGraphicsLayoutItem();
	}
}
