package lazerdoom;
/*
	TUIO Java Example - part of the reacTIVision project
	http://reactivision.sourceforge.net/

	Copyright (c) 2005-2008 Martin Kaltenbrunner <mkalten@iua.upf.edu>

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

import TUIO.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import sparshui.common.*;
import sparshui.common.messages.events.TouchEvent;



public class SparshTUIOAdapter implements TuioListener {
	
	private TuioClient tuioClient;
	private Socket sparshAdapterSocket;
	private DataOutputStream sparshAdapterDataOutputStream;
	

	public SparshTUIOAdapter(int TUIOPort, int sparshPort) {
		tuioClient = new TuioClient(TUIOPort);
		
		System.out.println("listening to TUIO messages at port "+TUIOPort);
		System.out.println("Sending to sparsh at port"+sparshPort);
		tuioClient.addTuioListener(this);
		tuioClient.connect();
		
		try {
			sparshAdapterSocket = new Socket("127.0.0.1", sparshPort);
			sparshAdapterDataOutputStream = new DataOutputStream(sparshAdapterSocket.getOutputStream());
			
			sparshAdapterDataOutputStream.writeByte(ConnectionType.INPUT_DEVICE.value());
			sparshAdapterDataOutputStream.flush();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addTuioObject(TuioObject tobj) {
		System.out.println("add obj "+tobj.getSymbolID()+" ("+tobj.getSessionID()+") "+tobj.getX()+" "+tobj.getY()+" "+tobj.getAngle());	
	}

	public void updateTuioObject(TuioObject tobj) {
		System.out.println("set obj "+tobj.getSymbolID()+" ("+tobj.getSessionID()+") "+tobj.getX()+" "+tobj.getY()+" "+tobj.getAngle()+" "+tobj.getMotionSpeed()+" "+tobj.getRotationSpeed()+" "+tobj.getMotionAccel()+" "+tobj.getRotationAccel()); 
	}
	
	public void removeTuioObject(TuioObject tobj) {
		System.out.println("del obj "+tobj.getSymbolID()+" ("+tobj.getSessionID()+")");	
	}

	
	//EVENTS SENT TO SPARSH
	public void addTuioCursor(TuioCursor tcur) {
		//System.out.println("add cur "+tcur.getCursorID()+" ("+tcur.getSessionID()+") "+tcur.getX()+" "+tcur.getY());
		try {
			sparshAdapterDataOutputStream.writeInt(1);
			sparshAdapterDataOutputStream.writeInt(tcur.getCursorID());
			sparshAdapterDataOutputStream.writeFloat(tcur.getX());
			sparshAdapterDataOutputStream.writeFloat(tcur.getY());
			sparshAdapterDataOutputStream.writeByte(TouchState.BIRTH.ordinal());
			sparshAdapterDataOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateTuioCursor(TuioCursor tcur) {
		//System.out.println("set cur "+tcur.getCursorID()+" ("+tcur.getSessionID()+") "+tcur.getX()+" "+tcur.getY()+" "+tcur.getMotionSpeed()+" "+tcur.getMotionAccel());
		try {
			sparshAdapterDataOutputStream.writeInt(1);
			sparshAdapterDataOutputStream.writeInt(tcur.getCursorID());
			sparshAdapterDataOutputStream.writeFloat(tcur.getX());
			sparshAdapterDataOutputStream.writeFloat(tcur.getY());
			sparshAdapterDataOutputStream.writeByte(TouchState.MOVE.ordinal());
			sparshAdapterDataOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void removeTuioCursor(TuioCursor tcur) {
		//System.out.println("del cur "+tcur.getCursorID()+" ("+tcur.getSessionID()+")");
		try {
			sparshAdapterDataOutputStream.writeInt(1);
			sparshAdapterDataOutputStream.writeInt(tcur.getCursorID());
			sparshAdapterDataOutputStream.writeFloat(tcur.getX());
			sparshAdapterDataOutputStream.writeFloat(tcur.getY());
			sparshAdapterDataOutputStream.writeByte(TouchState.DEATH.ordinal());
			sparshAdapterDataOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	
	public void refresh(TuioTime bundleTime) {}

	
	public static void main(String argv[]) {


 		SparshTUIOAdapter demo = new SparshTUIOAdapter(3333,5945);
 		
	}
}
