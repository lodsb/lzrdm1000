package GUI.Item;

import java.util.LinkedList;
import java.util.List;

import sparshui.common.Event;
import sparshui.common.TouchState;
import sparshui.common.messages.events.TouchEvent;

import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSizeF;
import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QRadialGradient;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.svg.QSvgRenderer;
import GUI.Multitouch.*;

public class SequencePlayerItem extends BaseSequenceViewItem implements ConnectableSequenceInterface {
	
	private static QRectF boundingRect = new QRectF(0,0,200,200);
	private QRectF centerRect = new QRectF(39.5,39.5, 121, 121);
	private static String svgFileName = System.getProperty("user.dir")+"/src/GUI/Item/SVG/sequence-player-icons.svg";
	private static QColor normalColor = new QColor(130,130,130);
	private QColor customColor = new QColor(38,50,62);
	private static QBrush outerBrush = new QBrush(new QColor(211,120,0));
	
	private Button playButton;
	private Button stopButton;
	private SequenceConnector outConnector;
	
	private QBrush gradientBrush;
	
	private enum ButtonState {
		ON,
		OFF,
		BLINKING,
	};
	
	private class Button extends TouchableGraphicsItem {
		
		private QSvgRenderer renderer;
		private ButtonState state = ButtonState.OFF;
		
		Signal0 pressed = new Signal0();
		
		private boolean isButtonOn = false;
		private String stateOnElementID;
		private String stateOffElementID;
		
		private LinkedList<Integer> viewGestures = new LinkedList<Integer>();
		
		private QTimer timer = new QTimer();
		private int timerInterval = 500;
		
		private QRectF boundingRect = new QRectF(-40.0, -40.0, 80,80);
		
		Button(QSvgRenderer renderer, String stateOnElementID, String stateOffElementID) {
			this.renderer = renderer;
			this.stateOnElementID = stateOnElementID;
			this.stateOffElementID = stateOffElementID;
			
			this.timer.timeout.connect(this, "timerSlot()");
			
			viewGestures.add(sparshui.gestures.GestureType.TOUCH_GESTURE.ordinal());
		}
		
		void setState(ButtonState state) {
			if(state == ButtonState.ON) {
				isButtonOn = true;
				this.timer.stop();
			} else if(state == ButtonState.OFF) {
				isButtonOn = false;
				this.timer.stop();
			} else {
				isButtonOn = false;
				this.timer.start(timerInterval);
			}
			
			this.state = state;
		}
		
		private void timerSlot() {
			if(state == ButtonState.BLINKING) {
				isButtonOn = isButtonOn ^ true;
				update();
			}
		}
		
		@Override
		public QRectF boundingRect() {
			return boundingRect;
		}

		@Override
		public void paint(QPainter painter, QStyleOptionGraphicsItem option,
				QWidget widget) {
			if(isButtonOn) {
				renderer.render(painter, stateOnElementID, boundingRect);
			} else {
				renderer.render(painter, stateOffElementID, boundingRect);
			}
		}

		@Override
		public QSizeF getPreferedSize() {
			return null;
		}

		@Override
		public void setGeometry(QRectF size) {
		}
		
		@Override
		public List<Integer> getAllowedGestures() {
			return this.viewGestures;
		}
		
		@Override
		public boolean processEvent(Event event) {
			if(event instanceof TouchEvent) {
				TouchEvent e = (TouchEvent) event;
				if(e.getState() == TouchState.BIRTH) {
					pressed.emit();
				}
			}
			
			return true;
		}

		@Override
		public QSizeF getMaximumSize() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	
	public SequencePlayerItem() {
		QSvgRenderer renderer = new QSvgRenderer(svgFileName);
		playButton = new Button(renderer, "playing", "play");
		playButton.pressed.connect(this, "stopPressed()");
		playButton.setParentItem(this);
		playButton.setPos(100,60);
			
		stopButton = new Button(renderer, "stopping", "stop");
		stopButton.pressed.connect(this, "stopPressed()");
		stopButton.setParentItem(this);
		stopButton.setPos(100,140);
		
		SequenceConnector connector = new SequenceConnector();
		//connector.scale(2.0, 2.0);
		connector.setZValue(0.23);
		connector.setParentItem(this);
		connector.rotate(90.0);
		connector.setPos(200,75);
		this.outConnector = connector;
		
		this.setFlag(GraphicsItemFlag.ItemIsMovable, true);
		this.setFlag(GraphicsItemFlag.ItemIsSelectable, true);
		this.updateGradientBrush();
	}
	
	private void stopPressed() {
		
	}
	
	private void playPressed() {
		
	}
	
	@Override
	public QRectF boundingRect() {
		return boundingRect;
	}
	
	private void updateGradientBrush() {
		double rad = centerRect.width();
		QRadialGradient gr = new QRadialGradient(100.0,100.0, rad);
		gr.setColorAt(0.0, new QColor(255,2,104, 220));
		//gr.setColorAt(0.1, new QColor(255,2,104, 200));
		//gr.setColorAt(0.9, new QColor(50, 50, 50, 30));
		gr.setColorAt(1.0, new QColor(0, 0, 0, 20));
		//gr.setColorAt(1, new QColor(0, 0, 0, 0));

		gradientBrush = new QBrush(gr);
	}
	
	@Override
	public void paint(QPainter painter, QStyleOptionGraphicsItem option,
			QWidget widget) {
		QColor frameColor;
		frameColor = normalColor;
		
		painter.setPen(frameColor);
		painter.setBrush(QBrush.NoBrush);
		painter.drawEllipse(boundingRect);
		
		// inner circle + text
		painter.setBrush(outerBrush);
		painter.setPen(frameColor);
		painter.drawEllipse(centerRect);
			
		/*painter.setBrush(gradientBrush);
		painter.setPen(frameColor);

		painter.drawEllipse(boundingRect);
		*/

	}

	@Override
	public QSizeF getPreferedSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGeometry(QRectF size) {
		// TODO Auto-generated method stub

	}

	@Override
	public SequenceConnector getSequenceInConnector() {
		return null;
	}

	@Override
	public SequenceConnector getSequenceOutConnector() {
		return this.outConnector;
	}

	@Override
	public QSizeF getMaximumSize() {
		// TODO Auto-generated method stub
		return null;
	}

}
