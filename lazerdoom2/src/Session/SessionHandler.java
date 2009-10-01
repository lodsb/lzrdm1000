package Session;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import lazerdoom.LzrDmObjectInterface;

import com.thoughtworks.xstream.XStream;

import GUI.Editor.BaseEditorCommand;
import GUI.Editor.Editor;
import Session.Converter.LzrDmObjectConverter;
import Session.Converter.QPointFConverter;

public class SessionHandler {
	private static SessionHandler instance; 
	
	public static SessionHandler getInstance() {
		return instance;
	}

	private XStream xstream = new XStream(); 
	
	private String sessionName = null; 
	
	public SessionHandler(String fileName) {
		xstream.registerConverter(new QPointFConverter());
		xstream.registerConverter(new LzrDmObjectConverter());
		
		SessionHandler.instance = this;
		
		sessionName = fileName;
		
		commandStack = new LinkedList<BaseEditorCommand>();
	}
	
	public void loadSession() {
		if(sessionName != null) {
			File openFile = new File(sessionName);
			if(openFile.exists()) {
				System.out.print("Opening session "+sessionName+" ... ");
				FileReader reader;
				try {
					reader = new FileReader(openFile);
					LinkedList<String> cmdListXml = (LinkedList<String>) xstream.fromXML(reader);
					
					for(String cmd: cmdListXml) {
						BaseEditorCommand command = (BaseEditorCommand) xstream.fromXML(cmd);
						command.execute();
						this.commandStack.push(command);
					}
					
					/*for(BaseEditorCommand command: commandStack) {
						System.out.println("exec command: "+command);
						command.execute();
					}*/

					System.out.println("done!");

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
		}
	}
	
	private LinkedList<BaseEditorCommand> commandStack;
	
	public LinkedList<BaseEditorCommand> getCommandStack(Editor editor) {
		// TODO: add contexts for editors
		// ignore editor for now
		
		return this.commandStack;
	}
	
	private HashMap<Long, LzrDmObjectInterface> objectDataBase = new HashMap<Long, LzrDmObjectInterface>();
	private HashMap<LzrDmObjectInterface, Long> keyDataBase = new HashMap<LzrDmObjectInterface, Long>();
	private long currentID = 0;
	
	public long registerObject(LzrDmObjectInterface obj) {
		if(keyDataBase.containsKey(obj)) {
			return keyDataBase.get(obj);
		} else {
			currentID++;
			objectDataBase.put(currentID, obj);
			keyDataBase.put(obj, currentID);
	
			return currentID;
		}
	}
	
	public Long getRegisteredObjectID(LzrDmObjectInterface obj) {
		return keyDataBase.get(obj);
	}
	
	public LzrDmObjectInterface getRegisteredObject(long id) {
		return objectDataBase.get(id);
	}
	
	public String dumpSession() {
		return xstream.toXML(this.commandStack);
	}
	
	public void safeSession(String url) {
		File outputFile = new File(url);
		
		FileWriter writer;
		
		try {
			
			System.out.println(this.commandStack);
			writer = new FileWriter(outputFile);
			System.out.print("dumping session....");
			LinkedList<String> xmls = new LinkedList<String>();
			
			for(BaseEditorCommand command: this.commandStack) {
				xmls.push(xstream.toXML(command));
			}
			xstream.toXML(xmls, writer);
			System.out.println("done!");
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
}
