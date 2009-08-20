package lazerdoom;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;


import sparshui.client.Client;
import sparshui.common.Event;
import sparshui.common.Location;
import sparshui.common.messages.events.DragEvent;
import sparshui.common.messages.events.RotateEvent;
import sparshui.common.messages.events.TouchEvent;
import sparshui.gestures.GestureType;

import GUI.Item.SequenceButton;
import GUI.Item.Editor.PushButton;
import GUI.Item.Editor.Slider;
import SceneItems.ContextMenu;
import SceneItems.CurveItem;
import SceneItems.HorizontalTouchabelGraphicsItemLayout;
import GUI.Multitouch.*;

import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QGraphicsItemInterface;
import com.trolltech.qt.gui.QGraphicsLayoutItem;
import com.trolltech.qt.gui.QGraphicsLinearLayout;
import com.trolltech.qt.gui.QGraphicsProxyWidget;
import com.trolltech.qt.gui.QGraphicsRectItem;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QGraphicsView;


public class Scene extends QGraphicsScene {

	public Scene() {
		this.setSceneRect(0,0, 2400, 2400);
		
	/*	LinkedList<String> menuItems = new LinkedList<String>();
		menuItems.add("test");
		menuItems.add("blam");
		menuItems.add("blim");
		ContextMenu cm = new ContextMenu(menuItems, "doubt", "what?");
		this.addItem(cm);
		cm.setPos(500,500);
		
		Slider s1 = new Slider();
		this.addItem(s1);
		s1.setPos(600,600);
		Slider s2 = new Slider();
		this.addItem(s2);
		
		HorizontalTouchabelGraphicsItemLayout layout = new HorizontalTouchabelGraphicsItemLayout();
		layout.addTouchableItem(s1);
		layout.addTouchableItem(s2);
		layout.setPos(600,600);
		this.addItem(layout);
		*/
		
		//MulticontrolItem mci = new MulticontrolItem();
		//mci.setPos(500,500);
		//this.addItem(mci);
		
		/*CurveItem ci = new CurveItem(new QPointF(200,200), new QPointF(400,400));
		ci.setPos(100,100);
		this.addItem(ci);
		*/
		
		/*SVGButton button1 = new SVGButton("addSequence");
		SVGButton button2 = new SVGButton("addSynth");
		SVGButton button3 = new SVGButton("delete");
		button1.setPos(0,100);
		button2.setPos(0,210);
		button3.setPos(0,310);
		this.addItem(button1);
		this.addItem(button2);
		this.addItem(button3);*/
		
		
	/*	QGraphicsView view2 = new QGraphicsView();
		QGraphicsScene scene2 = new QGraphicsScene();
		scene2.setSceneRect(0, 0, 140, 140);
		QGraphicsProxyWidget w2 = this.addWidget(view2);
		w2.setPos(200, 200);
		*/
		
		//PushButton p = new PushButton("whahahahaha");
		//this.addItem(p);
		//p.setPos(700,500);
	}
	
}
