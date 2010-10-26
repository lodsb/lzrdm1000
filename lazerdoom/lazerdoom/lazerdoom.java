package lazerdoom;
import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.Qt.DockWidgetArea;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.sql.QSqlTableModel;
import java.math.*;
import java.util.*;

import patch.scene.PatchScene;
import patch.view.PatchView;

import sequence.GenericTimelineSequence;
import sequence.view.GenericSequenceEditor;
import sequence.view.SequenceEditorStack;
import sequence.view.types.DoublePointSequenceEditor;

public class lazerdoom extends QMainWindow{

    public static void main(String[] args) {
        QApplication.initialize(args);
        QApplication.setStyle(new QPlastiqueStyle());

        lazerdoom testlazerdoom = new lazerdoom(null);
        
        testlazerdoom.show();

        QApplication.exec();

    }

    public lazerdoom(QWidget parent){
        super(parent);
        this.setWindowTitle("lazerdoom 1000");
        
        PatchScene patchScene = new PatchScene();
        PatchView patchView = new PatchView(patchScene);
        QDockWidget patchViewDock = new QDockWidget(this);
        patchViewDock.setWindowTitle("Patch");
        patchViewDock.setWidget(patchView);
        this.addDockWidget(DockWidgetArea.LeftDockWidgetArea, patchViewDock);
        
        SequenceEditorStack editorStack = new SequenceEditorStack();
        QDockWidget sequenceEditorDock = new QDockWidget(this);
        sequenceEditorDock.setWindowTitle("Editor");
        sequenceEditorDock.setWidget(editorStack);
        this.addDockWidget(DockWidgetArea.RightDockWidgetArea, sequenceEditorDock);
        
        patchScene.addNode(new QPointF(200,200));
        patchScene.addNode(new QPointF(200,400));
        patchScene.addNode(new QPointF(400,200));
        patchScene.addNode(new QPointF(600,200));
        patchScene.addNode(new QPointF(200,600));
        
       
        editorStack.addSequenceEditor(new DoublePointSequenceEditor());
        editorStack.addSequenceEditor(new DoublePointSequenceEditor());
    }
}
