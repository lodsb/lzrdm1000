package patch;

import java.util.List;


public interface NodeInterface {
	List<InputInterface> getInputs();
	List<OutputInterface> getOutputs();
	
	void setID(int id);
	
	String getName();
	
	boolean isReadyForProcessing();
	void process();
}
