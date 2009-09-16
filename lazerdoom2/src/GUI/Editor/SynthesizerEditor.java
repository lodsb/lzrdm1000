package GUI.Editor;

import java.util.LinkedList;
import java.util.List;

import lazerdoom.Core;

import com.trolltech.qt.gui.QGraphicsItemInterface;

import sparshui.common.messages.events.TouchEvent;
import GUI.Editor.Commands.SynthesizerEditor.SetCurrentSynthCommand;
import GUI.Editor.Commands.SynthesizerEditor.XchgSynthesizerCommand;
import GUI.Item.BaseSequencerItem;
import GUI.Item.SynthesizerItem;
import GUI.Multitouch.TouchableGraphicsItem;
import GUI.Scene.Editor.EditorScene;
import GUI.Scene.Editor.SynthesizerScene;
import GUI.View.SequencerView;
import SceneItems.Util;
import Synth.SynthInfo;
import Synth.SynthInstance;

public class SynthesizerEditor extends BaseSequencerItemEditor {

	private int id = Util.getGroupID();
	
	private SynthesizerScene editorScene = new SynthesizerScene(this);
	private SynthesizerItem synthesizerItem = null;
	
	private SynthInstance currentSynth = null;
	private LinkedList<Integer> allowedGestures = new LinkedList<Integer>();
	
	private List<SynthInfo> availableSynths;
	
	public SynthesizerEditor() {
		super();
		this.setScene(editorScene);
		allowedGestures.add(sparshui.gestures.GestureType.TOUCH_GESTURE.ordinal());
		
		this.editorScene.loadSynthPressed.connect(this, "loadPressed()");
		this.editorScene.nextPressed.connect(this, "nextPressed()");
		this.editorScene.prevPressed.connect(this, "prevPressed()");
		
		this.availableSynths = Core.getInstance().getSynthController().getAvailableSynths();
		
		this.editorScene.enablePrevButton(false);
		
		if(this.availableSynths.size() > 0) {
			this.editorScene.setCurrentSelectionSynthname(this.availableSynths.get(0).getName());
			this.editorScene.setCurrentSelectionDescription(this.availableSynths.get(currentSynthIndex).getDescription());
		} else if(this.availableSynths.size() == 0) {
			this.editorScene.setCurrentSelectionSynthname("none available");
			this.editorScene.enableNextButton(false);
		}
		
		
	}
	
	private void pSetCurrentSynth(SynthInstance synth) {
			if(currentSynth == null) {
				this.executeCommand(new SetCurrentSynthCommand(synth, this));
			} else {
				this.executeCommand(new XchgSynthesizerCommand(synthesizerItem, currentSynth, synth, this,  SequencerView.getInstance().getSequencerEditor()));
			}
	}
	
	public void setCurrentSynth(SynthInstance synth) {
		if(synthesizerItem != null) {
			currentSynth = synth;
			synthesizerItem.setContentObject(synth);
			this.editorScene.setCurrentSynth(this.currentSynth.getSynthInfo().getName());
		}
	}
	
	int currentSynthIndex = 0;
	
	private void prevPressed() {
		if(currentSynthIndex > 0) {
			if(currentSynthIndex == 1) {
				this.editorScene.enablePrevButton(false);
			} else {
				this.editorScene.enablePrevButton(true);
			}
			currentSynthIndex--;
			this.editorScene.setCurrentSelectionSynthname(this.availableSynths.get(currentSynthIndex).getName());
			this.editorScene.setCurrentSelectionDescription(this.availableSynths.get(currentSynthIndex).getDescription());
			
			if(currentSynthIndex < this.availableSynths.size()) {
				this.editorScene.enableNextButton(true);
			}
		}
	}
	
	private void nextPressed() {
		if(currentSynthIndex < this.availableSynths.size()-1) {
			if(currentSynthIndex == this.allowedGestures.size()-1) {
				this.editorScene.enableNextButton(false);
			} else {
				this.editorScene.enableNextButton(true);
			} 
			
			currentSynthIndex++;
			this.editorScene.setCurrentSelectionSynthname(this.availableSynths.get(currentSynthIndex).getName());
			this.editorScene.setCurrentSelectionDescription(this.availableSynths.get(currentSynthIndex).getDescription());
			
			if(currentSynthIndex > 0) {
				this.editorScene.enablePrevButton(true);
			}
		}
	}
	
	private void loadPressed() {
//		this.currentSynth = Core.getInstance().getSynthController().createSynthInstance(this.availableSynths.get(currentSynthIndex));
//		this.editorScene.setCurrentSynth(this.currentSynth.getSynthInfo().getName());
		this.pSetCurrentSynth(Core.getInstance().getSynthController().createSynthInstance(this.availableSynths.get(currentSynthIndex)));
	}
	
	@Override
	public void handleTouchEvent(TouchEvent e) {
		this.setShowTouchEvents(true);
		QGraphicsItemInterface item;
		if((item = editorScene.itemAt(e.getSceneLocation())) instanceof TouchableGraphicsItem) {
			((TouchableGraphicsItem) item).processEvent(e);
		}
	}
	
	@Override
	public void setItem(BaseSequencerItem item) {
		if(item instanceof SynthesizerItem) {
			this.synthesizerItem = (SynthesizerItem) item;
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

}
