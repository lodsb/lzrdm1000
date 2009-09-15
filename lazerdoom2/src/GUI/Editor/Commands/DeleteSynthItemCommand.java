package GUI.Editor.Commands;

import java.util.LinkedList;

import lazerdoom.Core;

import com.trolltech.qt.gui.QGraphicsScene;

import GUI.Editor.BaseEditorCommand;
import GUI.Item.SynthConnection;
import GUI.Item.SynthInConnector;
import GUI.Item.SynthesizerItem;
import GUI.Scene.Editor.EditorScene;

public class DeleteSynthItemCommand extends BaseEditorCommand {
	private SynthesizerItem item;
	private QGraphicsScene scene;

	public DeleteSynthItemCommand(SynthesizerItem item, QGraphicsScene scene) {
		this.item = item;
		this.scene = scene;
	}

	@Override
	public boolean execute() {
		boolean ret = false;
		
		if(item.isInitialized()) {
			System.out.println(item.getSynthesizer());
			ret = Core.getInstance().getSynthController().remove(item.getSynthesizer());
			System.out.println(ret);
			if(ret) {
				for(SynthInConnector inConnector :item.getSynthInConnectors()) {

					LinkedList<SynthConnection> synConnections = new LinkedList<SynthConnection>();
					for(SynthConnection connection: inConnector.getConnections()) {
						this.scene.removeItem(connection);
						synConnections.add(connection);
					}

					for(SynthConnection con: synConnections) {
						con.remove();
					}
				}

				this.scene.removeItem(item);
				item.undockAllCursors();
			}
		} else {
			this.scene.removeItem(item);
			item.undockAllCursors();
		}
		return ret;
	}

}
