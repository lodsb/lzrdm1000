package GUI.Editor.Commands;

import lazerdoom.Core;

import com.trolltech.qt.gui.QGraphicsScene;

import GUI.Editor.BaseEditorCommand;
import GUI.Item.SequenceConnection;
import GUI.Item.SequenceItem;

public class ConnectSequencesCommand extends BaseEditorCommand {
	private QGraphicsScene scene;
	private SequenceItem src;
	private SequenceItem dst;
	
	public ConnectSequencesCommand(SequenceItem src, SequenceItem dst, QGraphicsScene scene) {
		this.src = src;
		this.dst = dst;
		this.scene = scene;
	} 
	
	@Override
	public boolean execute() {
		boolean ret = Core.getInstance().getSequenceController().connectSequences(src.getSequence(), dst.getSequence());
		
		if(ret) {
			this.scene.addItem(new SequenceConnection(src.getSequenceOutConnector(), dst.getSequenceInConnector()));
		}
		
		return ret;
	}

}
