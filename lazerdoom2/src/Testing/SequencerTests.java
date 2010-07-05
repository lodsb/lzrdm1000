package Testing;

import sequencer.EventPointsSequenceTest;
import sequencer.SequentialSequenceContainerTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class SequencerTests {

	static Class[] testClasses = {EventPointsSequenceTest.class, SequentialSequenceContainerTest.class};
	
	public static Test suite() {
		TestSuite suite = new TestSuite();
		
		for(Class testCase: testClasses) {
			try {
				TestCase test = (TestCase) testCase.newInstance();
				test.setName(testCase.getName());
				suite.addTest(test);
			} catch (InstantiationException e) {
				System.err.println("Error instantiating Test-Case");
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				System.err.println("Error instantiating Test-Case");
				e.printStackTrace();
			}
		}

		//$JUnit-END$
		return suite;
	}

}
