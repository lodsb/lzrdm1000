package Session;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import lazerdoom.LzrDmObjectInterface;

import com.thoughtworks.xstream.XStream;

import GUI.Editor.BaseEditorCommand;
import GUI.Editor.Editor;
import Sequencer.BaseSequence;
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
	
	public boolean hasDataDescriptor(BaseSequence sequence) {
		Long seqID = this.getRegisteredObjectID(sequence);
		return this.dataDescriptors.containsKey(seqID);
	}
	
	public BaseSequenceDataDescriptor getDataDescriptor(BaseSequence sequence) {
		Long seqID = this.getRegisteredObjectID(sequence);
		return this.dataDescriptors.get(seqID);		
	}
	
	public void registerDataDescriptor(BaseSequence sequence, BaseSequenceDataDescriptor descriptor) {
		Long seqID = this.getRegisteredObjectID(sequence);
		this.dataDescriptors.put(seqID, descriptor);
	}
	
	public void loadSession() {
		if(sessionName != null) {
			File sessionFile = new File(sessionName+".lds");
			File sequenceDataFile = new File(sessionName+".ldseq");
			
			if(sessionFile.exists()) {
				System.out.print("Opening session "+sessionName+" ... ");
				FileReader sessionFileReader;
				FileReader sequenceDataFileReader;
				
				try {
					System.out.print("loading sequence data...");
					sequenceDataFileReader = new FileReader(sequenceDataFile);
					HashMap<Long, BaseSequenceDataDescriptor> seqMap = (HashMap<Long, BaseSequenceDataDescriptor>) xstream.fromXML(sequenceDataFileReader);
					System.out.println(seqMap);
					this.dataDescriptors.putAll(seqMap);
					
					System.out.println("done!");
					
					sessionFileReader = new FileReader(sessionFile);
					LinkedList<String> cmdListXml = (LinkedList<String>) xstream.fromXML(sessionFileReader);
					
					System.out.print("replaying commands...");
					for(String cmd: cmdListXml) {
						System.out.println("-----");
						BaseEditorCommand command = (BaseEditorCommand) xstream.fromXML(cmd);
						System.out.println("EXEC COMMAND: "+command);
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
	private HashMap<Long, BaseSequenceDataDescriptor> dataDescriptors = new HashMap<Long, BaseSequenceDataDescriptor>();
	private long currentID = 0;
	
	public long registerObject(LzrDmObjectInterface obj) {
		if(keyDataBase.containsKey(obj)) {
			return keyDataBase.get(obj);
		} else {
			currentID++;
			objectDataBase.put(currentID, obj);
			keyDataBase.put(obj, currentID);
	
			System.out.println("registered "+obj+" "+currentID); 
			System.out.println(keyDataBase);
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
		File sessionFile = new File(url+".lds");
		FileWriter sessionFileWriter;
		
		File sequenceFile = new File(url+".ldseq");
		FileWriter sequenceFileWriter;
		
		try {
			
			System.out.println(this.commandStack);
			sessionFileWriter = new FileWriter(sessionFile);
			System.out.print("Dumping session....");
			LinkedList<String> xmls = new LinkedList<String>();
			
			for(BaseEditorCommand command: this.commandStack) {
				if(command.allowSaveToSessionFile()) {
					System.out.println("cmd: "+command);
					xmls.push(xstream.toXML(command));
					System.out.println(xstream.toXML(command));
				}
			}
			xstream.toXML(xmls, sessionFileWriter);
			System.out.println("done!");
			
			System.out.print("Dumping seq-data...");
			sequenceFileWriter = new FileWriter(sequenceFile);
			
			for(Entry<Long, BaseSequenceDataDescriptor> entry: dataDescriptors.entrySet()) {
				entry.getValue().gatherData();
			}
			System.out.println("done!");
			xstream.toXML(dataDescriptors, sequenceFileWriter);
			System.out.println(xstream.toXML(dataDescriptors));
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
		System.out.println(keyDataBase);
	}
	
}
