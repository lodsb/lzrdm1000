package gui.editor.commands;

import gui.editor.BaseEditorCommand;
import gui.item.SynthConnection;
import gui.item.SynthInConnector;
import gui.item.SynthesizerItem;
import gui.scene.editor.EditorScene;

import java.util.LinkedList;

import lazerdoom.Core;
import lazerdoom.LzrDmObjectInterface;

import com.trolltech.qt.gui.QGraphicsScene;


public class DeleteSynthItemCommand extends BaseEditorCommand {
	private LzrDmObjectInterface it;
	private LzrDmObjectInterface scn;

	public DeleteSynthItemCommand(SynthesizerItem item, EditorScene scene) {
		this.it = item;
		this.scn = scene;
	}

	@Override
	public boolean execute() {
		boolean ret = false;
		
		SynthesizerItem item = (SynthesizerItem) this.it;
		EditorScene scene = (EditorScene) this.scn;
		
		if(item.isInitialized()) {
			
			//System.out.println(item.getSynthesizer());
			ret = Core.getInstance().getSynthController().remove(item.getSynthesizer());
			//System.out.println(ret);
			if(ret) {
				for(SynthInConnector inConnector :item.getSynthInConnectors()) {

					LinkedList<SynthConnection> synConnections = new LinkedList<SynthConnection>();
					for(SynthConnection connection: inConnector.getConnections()) {
						scene.removeItem(connection);
						synConnections.add(connection);
					}

					for(SynthConnection con: synConnections) {
						con.remove();
					}
				}

				item.getSynthesizer().free();
				scene.removeItem(item);
				item.undockAllCursors();
			}
		} else {
			scene.removeItem(item);
			item.undockAllCursors();
		}
		return ret;
	}

}
