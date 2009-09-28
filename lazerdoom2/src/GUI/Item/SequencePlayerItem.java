package GUI.Item;

import java.util.LinkedList;
import java.util.List;

import lazerdoom.Core;
import lazerdoom.LzrDmObjectInterface;

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
import GUI.View.SequencerView;
import Sequencer.BaseSequence;
import Sequencer.SequenceEvent;
import Sequencer.SequenceEventListenerInterface;
import Sequencer.SequencePlayer;
import Sequencer.SequenceEvent.SequenceEventType;

public class SequencePlayerItem extends BaseSequenceViewItem implements ConnectableSequenceInterface, SequenceEventListenerInterface {
	
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
			
			this.startTimer.connect(timer, "start()");
			this.stopTimer.connect(timer, "stop()");
			
			this.timer.timeout.connect(this, "timerSlot()");
			this.timer.setInterval(this.timerInterval);
			
			viewGestures.add(sparshui.gestures.GestureType.TOUCH_GESTURE.ordinal());
		}
		
		
		private Signal0 startTimer = new Signal0();
		private Signal0 stopTimer = new Signal0();
		
		void setState(ButtonState state) {
			if(state == ButtonState.ON) {
				isButtonOn = true;
				this.stopTimer.emit();
			} else if(state == ButtonState.OFF) {
				isButtonOn = false;
				this.stopTimer.emit();
			} else {
				isButtonOn = false;
				this.startTimer.emit();
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
	
	private SequencePlayer sequencePlayer;
	
	public SequencePlayerItem(SequencePlayer sequencePlayer) {
		super();
		this.sequencePlayer = sequencePlayer;

	
			SequenceConnector connector = new SequenceConnector(false);
			//connector.scale(2.0, 2.0);
			connector.setZValue(100.0);
			connector.setParent(this);
			connector.rotate(90.0);
			connector.setPos(200,75);
			this.outConnector = connector;
		
		QSvgRenderer renderer = new QSvgRenderer(svgFileName);
		playButton = new Button(renderer, "playing", "play");
		playButton.pressed.connect(this, "playPressed()");
		playButton.setState(ButtonState.OFF);
		playButton.setParentItem(this);
		playButton.setPos(100,60);
			
		stopButton = new Button(renderer, "stopping", "stop");
		stopButton.pressed.connect(this, "stopPressed()");
		stopButton.setState(ButtonState.ON);
		stopButton.setParentItem(this);
		stopButton.setPos(100,140);
		
		this.setParent(SequencerView.getInstance());
		
		//this.setFlag(GraphicsItemFlag.ItemIsMovable, true);
		//this.setFlag(GraphicsItemFlag.ItemIsSelectable, true);
		this.updateGradientBrush();
		
		Core.getInstance().getSequenceController().registerSequenceInterfaceEventListener(sequencePlayer, this);
	}
	
	private long startTicks = 0;
	private long stopTicks = 0;
	
	public void setLoopingEnabled(boolean enabled) {
		this.sequencePlayer.setLoopSequence(enabled);
	}
	
	public void setScheduleTicks(long ticks) {
		startTicks = ticks;
		stopTicks = ticks;
	}
	
	private void stopPressed() {
		if(stopTicks == 0) {
			this.sequencePlayer.stopSequenceImmidiately();
		} else {
			this.sequencePlayer.scheduleStopNext(stopTicks);
		}
	}
	
	private void playPressed() {
		System.out.println("WHAT!!!!");
		if(startTicks == 0) {
			this.sequencePlayer.startSequenceImmidiately();
		} else {
			this.sequencePlayer.scheduleStartNext(startTicks);
		}
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

	@Override
	public void dispatchSequenceEvent(SequenceEvent se) {
		if(se.getSequenceEventType() == SequenceEventType.SEQUENCE_PLAYER_STARTED) {
			this.playButton.setState(ButtonState.ON);
			this.stopButton.setState(ButtonState.OFF);
		} 

		if(se.getSequenceEventType() == SequenceEventType.SEQUENCE_PLAYER_STOPPED) {
			this.playButton.setState(ButtonState.OFF);
			this.stopButton.setState(ButtonState.ON);
		} 
		
		if(se.getSequenceEventType() == SequenceEventType.SEQUENCE_PLAYER_STARTING) {
			this.playButton.setState(ButtonState.BLINKING);
			this.stopButton.setState(ButtonState.OFF);
		}
		
		if(se.getSequenceEventType() == SequenceEventType.SEQUENCE_PLAYER_STOPPING) {
			this.playButton.setState(ButtonState.OFF);
			this.stopButton.setState(ButtonState.BLINKING);
		} 
		
		this.update();
	}

	@Override
	public BaseSequence getBaseSequence() {
		return this.sequencePlayer;
	}

	@Override
	public boolean isInitialized() {
		return true;
	}

	@Override
	public void setContentObject(LzrDmObjectInterface object) {
	}

}
