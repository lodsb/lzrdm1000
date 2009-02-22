package patch;

import java.util.List;


public interface NodeInterface {
	List<InputInterface> getInputs();
	List<OutputInterface> getOutputs();
	
	String getName();
	
	void process();
}
