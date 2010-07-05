package session.converter;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.trolltech.qt.core.QPointF;

public class QPointFConverter implements Converter {

	@Override
	public void marshal(Object point, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		QPointF p = (QPointF) point;
		
		writer.startNode("xValue");
		writer.setValue(new Double(p.x()).toString());
		writer.endNode();
		
		writer.startNode("yValue");
		writer.setValue(new Double(p.y()).toString());
		writer.endNode();
		
		

	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		
		double x;
		double y;
		
		reader.moveDown();
		x = new Double(reader.getValue());
		reader.moveUp();
		reader.moveDown();
		y = new Double(reader.getValue());
		reader.moveUp();
		
		return new QPointF(x,y);
	}

	@Override
	public boolean canConvert(Class clazz) {
		return (clazz.equals(QPointF.class));
	}

}
