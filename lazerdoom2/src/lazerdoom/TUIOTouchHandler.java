package lazerdoom;

import TUIO.TuioClient;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;
import sparshui.client.Client;

public class TUIOTouchHandler extends TouchHandler implements TuioListener {
	
	private int TUIOPort = 3333;
	private TuioClient tuioClient;
	
	public TUIOTouchHandler(Client mc) {
		super(mc);
		
		tuioClient = new TuioClient();
		
		System.out.println("listening to TUIO messages at port "+TUIOPort);
		tuioClient.addTuioListener(this);
		tuioClient.connect();
		
	}

	@Override
	public void addTuioCursor(TuioCursor tcur) {
		this.addTouchPoint(tcur.getCursorID(), tcur.getX(), tcur.getY());
	}

	@Override
	public void addTuioObject(TuioObject arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(TuioTime arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTuioCursor(TuioCursor tcur) {
		this.removeTouchPoint(tcur.getCursorID(), tcur.getX(), tcur.getY());
		
	}

	@Override
	public void removeTuioObject(TuioObject arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTuioCursor(TuioCursor tcur) {
		//System.out.println(tcur.getTuioTime().getSessionTime().getTotalMilliseconds());
		this.updateTouchPoint(tcur.getCursorID(), tcur.getX(), tcur.getY());
	}

	@Override
	public void updateTuioObject(TuioObject arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

}
