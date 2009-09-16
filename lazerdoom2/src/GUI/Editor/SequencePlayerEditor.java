package GUI.Editor;

import java.util.LinkedList;
import java.util.List;

import com.trolltech.qt.gui.QGraphicsItemInterface;

import sparshui.common.messages.events.TouchEvent;

import lazerdoom.Core;

import GUI.Item.BaseSequencerItem;
import GUI.Item.SequencePlayerItem;
import GUI.Item.SynthesizerItem;
import GUI.Multitouch.TouchableGraphicsItem;
import GUI.Scene.Editor.SequencePlayerScene;
import SceneItems.Util;
import Synth.SynthInstance;

public class SequencePlayerEditor extends BaseSequencerItemEditor {
	private int id = Util.getGroupID();
	private SequencePlayerScene editorScene = new SequencePlayerScene(this);
	
	private LinkedList<Integer> allowedGestures = new LinkedList<Integer>();
	
	private int[] beatArray = new int[] {
		8, // 1
		4, // 1
		2, // 1
		1, // 1
		1, // 4
		1, // 8 
		1, // 16
		0, // none
	};
	
	private int[] measureArray = new int[] {
		/*	8,*/ 1,
		/*	4,*/ 1,
		/*	2,*/ 1,
		/*	1,*/ 1,
		/*	1,*/ 4,
		/*	1,*/ 8, 
		/*	1,*/ 16,
			0, // none
		};

	public SequencePlayerEditor() {
		super();
		this.setScene(editorScene);
		allowedGestures.add(sparshui.gestures.GestureType.TOUCH_GESTURE.ordinal());
		
		this.currentQuantizationIndex = measureArray.length-1; // no quant
		
		this.editorScene.nextPressed.connect(this, "nextPressed()");
		this.editorScene.prevPressed.connect(this, "prevPressed()");
		
		this.editorScene.enableNextButton(false);
	
	}
	
	private void nextPressed() {
		if(currentQuantizationIndex < measureArray.length-1) {
			currentQuantizationIndex++;
			this.setPlayerQuantization();
			
			if(currentQuantizationIndex == measureArray.length-1) {
				this.editorScene.enableNextButton(false);
			} 
		}
		this.editorScene.enablePrevButton(true);
	}
	
	private void prevPressed() {
		if(currentQuantizationIndex > 0) {
			currentQuantizationIndex--;
			this.setPlayerQuantization();
			
			if(currentQuantizationIndex == 0) {
				this.editorScene.enablePrevButton(false);
			}
		}
		this.editorScene.enableNextButton(true);
	}
	
	private int currentQuantizationIndex;
	private void setPlayerQuantization() {
		if(this.player != null) {
			long ticks = 0;
			if(currentQuantizationIndex != measureArray.length - 1) {
				ticks= Core.getInstance().beatMeasureToPPQ(beatArray[currentQuantizationIndex], measureArray[currentQuantizationIndex]);
			}
			this.player.setScheduleTicks(ticks);
			this.editorScene.setCurrentQuantization(beatArray[currentQuantizationIndex], measureArray[currentQuantizationIndex]);
		}
	}

	@Override
	public boolean allowViewpointChange() {
		return false;
	}

	@Override
	public List<Integer> getAllowedGestures() {
		return this.allowedGestures;
	}

	@Override
	public Integer getGroupID() {
		return this.id;
	}

	SequencePlayerItem player = null;
	@Override
	public void setItem(BaseSequencerItem item) {
		if(item instanceof SequencePlayerItem) {
			this.player = (SequencePlayerItem) item;
		}

	}
	
	@Override
	public void handleTouchEvent(TouchEvent e) {
		this.setShowTouchEvents(true);
		QGraphicsItemInterface item;
		if((item = editorScene.itemAt(e.getSceneLocation())) instanceof TouchableGraphicsItem) {
			((TouchableGraphicsItem) item).processEvent(e);
		}
	}

}
