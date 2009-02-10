package sequence.view.item.types;

import java.util.HashMap;
import java.util.Map;

import com.trolltech.qt.core.QFile;
import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.gui.QGraphicsItem;
import com.trolltech.qt.gui.QGraphicsSceneHoverEvent;
import com.trolltech.qt.gui.QGraphicsSceneMouseEvent;
import com.trolltech.qt.gui.QKeySequence;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QStyleOptionGraphicsItem;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.svg.QGraphicsSvgItem;
import com.trolltech.qt.svg.QSvgRenderer;

public class Node extends QGraphicsSvgItem {
	private static String resourceFile = new String("classpath:sequence/view/item/types/icons/nodes.svg");
	
	public Signal0 hoverEnterEvent = new Signal0();
	public Signal0 hoverLeaveEvent = new Signal0();
	
	public Signal0 mousePressEvent = new Signal0();
	public Signal0 mouseReleaseEvent=new Signal0();
	
	public Signal1<QPointF> mouseMoveEvent = new Signal1<QPointF>(); 
	
	public enum NodeType {
		none, 
		pointNode,
		pointNodeEditing
	}
	
	private static Map<NodeType, String> nodeTypes = new HashMap<NodeType, String>(); 
	static {
		nodeTypes.put(NodeType.pointNode,"g4214");
		nodeTypes.put(NodeType.pointNodeEditing,"g4210" );
	};
	
	
	private static QSvgRenderer renderer = new QSvgRenderer(resourceFile);
	
	private String nodeSvgID;
	private String nodeEditingSvgID;
	
	public void setEditing(boolean editing) {
		if(!editing) {
			if(nodeSvgID != null) {
				this.setElementId(nodeSvgID);
			}
		} else {
			if(nodeEditingSvgID != null) {
				this.setElementId(nodeEditingSvgID);
			}
		}
	}

	public Node(NodeType nodeType, NodeType editingNodeType) {
		super();
		
		nodeSvgID = nodeTypes.get(nodeType);
		nodeEditingSvgID = nodeTypes.get(editingNodeType);
		
		this.setSharedRenderer(renderer);
		
		this.setEditing(false);
		
		this.setAcceptHoverEvents(true);
	}
	
	public void hoverEnterEvent (QGraphicsSceneHoverEvent event ) {
		hoverEnterEvent.emit();
	}
	
	public void hoverLeaveEvent (QGraphicsSceneHoverEvent event ) {
		hoverLeaveEvent.emit();
	}
	
	public void mousePressEvent( QGraphicsSceneMouseEvent  event ) {
		mousePressEvent.emit();
	} 
	
	public void mouseReleaseEvent( QGraphicsSceneMouseEvent  event ) {
		mouseReleaseEvent.emit();
	} 
	
	public void mouseMoveEvent( QGraphicsSceneMouseEvent  event ) {
		mouseMoveEvent.emit(event.pos());
	}
}
