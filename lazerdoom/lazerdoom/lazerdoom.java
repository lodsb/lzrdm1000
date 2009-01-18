package lazerdoom;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.sql.QSqlTableModel;
import java.math.*;
import java.util.*;

import sequence.GenericTimelineSequence;

public class lazerdoom extends QWidget{

    public static void main(String[] args) {
        /*QApplication.initialize(args);

        lazerdoom testlazerdoom = new lazerdoom(null);
        
        testlazerdoom.show();

        QApplication.exec();
        */
    	
    	int numEntries = 100000;
    	GenericTimelineSequence<Double> gs = new GenericTimelineSequence<Double>(); 
    	
    	long stIn = System.nanoTime();
    	
    	for(int i = 0; i< numEntries; i++) {
    		gs.insert((long)(1000000*Math.random()), Math.random());
    	}
    	
    	long eIn = System.nanoTime() - stIn;
    	System.out.println("Insert Time msecs ("+numEntries+" Entries): "+eIn/(1000*1000));
    	System.out.println("Insert entries/msecs:"+ (float)(numEntries/(eIn/(1000*1000))));
    	
    	long stGet = System.nanoTime();
    	
    	for(int i = 0; i< numEntries; i++) {
    		gs.getAt((long)(1000000*Math.random()));
    	}
    	
    	long eGet = System.nanoTime() - stGet;
    	
    	System.out.println("Get Time (random) msecs ("+numEntries+" Entries): "+eGet/(1000*1000));
    	System.out.println("Get entries/msecs (random):"+ (float)(numEntries/(eGet/(1000*1000))));
    	
    	
    	long stGetS = System.nanoTime();
    	
    	for(int i = 0; i< numEntries; i++) {
    		gs.getAt((long)(1000000*(i/numEntries)));
    	}
    	
    	long eGetS = System.nanoTime() - stGetS;
    	
    	System.out.println("Get Time (sequential) msecs ("+numEntries+" Entries): "+eGetS/(1000*1000));
    	System.out.println("Get entries/msecs (sequential):"+ (float)(numEntries/(eGetS/(1000*1000))));
    }

    public lazerdoom(QWidget parent){
        super(parent);
    }
}
