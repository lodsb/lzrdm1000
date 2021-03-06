package gui.editor.commands.sequenceeditor;

import gui.editor.BaseEditorCommand;
import gui.editor.SequenceEditor;
import gui.editor.SequenceEditor.SequenceType;

import java.util.LinkedList;

import sequencer.BaseSequence;
import sequencer.EventPointsSequence;
import sequencer.Pause;
import session.BaseSequenceDataDescriptor;
import session.DoubleEventPointsDataDescriptor;
import session.NoteEventPointsDataDescriptor;
import session.SessionHandler;
import session.DoubleEventPointsDataDescriptor.DoubleDataEntry;
import session.NoteEventPointsDataDescriptor.NoteDataEntry;

import control.types.DoubleType;
import control.types.NoteType;

import lazerdoom.Core;
import lazerdoom.LzrDmObjectInterface;

public class CreateBaseSequence extends BaseEditorCommand {

	LzrDmObjectInterface editor;
	SequenceType type;
	
	public CreateBaseSequence(SequenceEditor editor, SequenceType type) {
		this.editor = editor;
		this.type = type;
	}
	
	@Override
	public boolean execute() {
		
		// OH SO UGLY!
		BaseSequence sequence = null;
		
		switch(this.type) {
		case PAUSE:
			sequence = Core.getInstance().getSequenceController().createPauseSequence(Core.getInstance().oneBarInPPQ());
			break;
		case EVENT_POINTS:
			sequence = Core.getInstance().getSequenceController().createDoubleTypeEventPointsSequence();
			//((EventPointsSequence)sequence).setLength(Core.getInstance().oneBarInPPQ());
			break;
		case NOTE:
			sequence = Core.getInstance().getSequenceController().createDoubleTypeEventPointsSequence();
			//((EventPointsSequence)sequence).setLength(Core.getInstance().oneBarInPPQ());
			break;			
		} 
		
		SessionHandler.getInstance().registerObject(sequence);
		BaseSequenceDataDescriptor dataDescriptor = null;
		
		if(SessionHandler.getInstance().hasDataDescriptor(sequence)) {
			dataDescriptor = SessionHandler.getInstance().getDataDescriptor(sequence);
		} else {
			switch(this.type) {
			case PAUSE:
				dataDescriptor = new BaseSequenceDataDescriptor(sequence);
				break;
			case EVENT_POINTS:
				dataDescriptor = new DoubleEventPointsDataDescriptor(sequence);
				//((EventPointsSequence)sequence).setLength(Core.getInstance().oneBarInPPQ());
				break;
			case NOTE:
				//sequence = Core.getInstance().getSequenceController().createDoubleTypeEventPointsSequence();
				dataDescriptor = new NoteEventPointsDataDescriptor(sequence);
				break;			
			} 
			SessionHandler.getInstance().registerDataDescriptor(sequence, dataDescriptor);
		}
		
		switch(this.type) {
		case PAUSE:
			((Pause)sequence).setPauseTicks(dataDescriptor.getLength());
			break;
		case EVENT_POINTS:
			DoubleEventPointsDataDescriptor epsDescriptor = (DoubleEventPointsDataDescriptor) dataDescriptor; 
			EventPointsSequence<DoubleType> eps = (EventPointsSequence<DoubleType>) sequence;
			eps.setLength(dataDescriptor.getLength());
			eps.setStartOffset(epsDescriptor.getStartOffset());
			
			LinkedList<LinkedList<DoubleDataEntry>> dataList;
			if((dataList = epsDescriptor.getDoubleData()) != null) {
				for(LinkedList<DoubleDataEntry> ddel: dataList) {
					for(DoubleDataEntry dde: ddel) {
						eps.insert(new DoubleType(dde.data), dde.tick);
						System.out.println("insert!!!!! "+dde.data+" @ "+dde.tick);
					}
				}
			}
			
			//((EventPointsSequence)sequence).setLength(Core.getInstance().oneBarInPPQ());
			break;
		case NOTE:
			NoteEventPointsDataDescriptor npsDescriptor = (NoteEventPointsDataDescriptor) dataDescriptor;
			EventPointsSequence<NoteType> nps = (EventPointsSequence<NoteType>) sequence;
			nps.setLength(dataDescriptor.getLength());
			nps.setStartOffset(npsDescriptor.getStartOffset());
			LinkedList<LinkedList<NoteDataEntry>> dataList2;
			if((dataList2 = npsDescriptor.getNoteData()) != null) {
				for(LinkedList<NoteDataEntry> ndel: dataList2) {
					for(NoteDataEntry nde: ndel) {
						NoteType noteOn = new NoteType(nde.dataOn, nde.gateOn);
						long noteOnTick = nde.tickOn;
						long noteOffTick = nde.tickOn+nde.length;
						noteOn.setLength(nde.length);
						NoteType noteOff = new NoteType(nde.dataOff, nde.gateOff);
						noteOff.setIsNoteOff();
						noteOn.setNoteOff(noteOff);
						nps.insert(noteOn, noteOnTick);
						nps.insert(noteOff, noteOffTick);
					}
				}
			}
			//dataDescriptor = new NoteEventPointsDataDescriptor(sequence);
			break;			
		} 
		
		((SequenceEditor)editor).setSequence(sequence, this.type, dataDescriptor);
		
		return true;
	}

}
