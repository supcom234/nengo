package ca.shu.ui.lib.actions;

import java.awt.geom.Point2D;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import ca.shu.ui.lib.objects.Window;
import ca.shu.ui.lib.world.WorldObject;
import edu.umd.cs.piccolo.PNode;

/**
 * Action which allows the dragging of objects by the selection handler to be
 * done and undone. NOTE: Special care is taken of Window objects. These objects
 * maintain their own Window state, and are thus immune to this action handler's
 * undo action.
 * 
 * @author Shu Wu
 */
public class DragAction extends ReversableAction {

	private static final long serialVersionUID = 1L;
	private Collection<WeakReference<WorldObject>> selectedObjectsRef;

	private HashMap<WeakReference<WorldObject>, ObjectState> objectStates;

	/**
	 * @param selectedObjects
	 *            Nodes before they are dragged. Their offset positions will be
	 *            used as initial positions.
	 */
	public DragAction(Collection<WorldObject> selectedObjects) {
		super("Drag operation");

		selectedObjectsRef = new ArrayList<WeakReference<WorldObject>>(
				selectedObjects.size());

		objectStates = new HashMap<WeakReference<WorldObject>, ObjectState>(
				selectedObjects.size());

		for (WorldObject wo : selectedObjects) {

			WeakReference<WorldObject> woRef = new WeakReference<WorldObject>(
					wo);
			selectedObjectsRef.add(woRef);
			
			ObjectState state = new ObjectState(wo.getParent(), wo.getOffset());
			objectStates.put(woRef, state);

		}

	}

	/**
	 * @param obj
	 *            Object whose drag is being undone
	 * @return True, if Object's drag can be undone
	 */
	public static boolean isObjectDragUndoable(WorldObject obj) {
		if (obj instanceof Window) {
			/*
			 * Window drag actions are immune to being undone
			 */
			return false;
		} else
			return true;
	}

	/**
	 * Stores the final positions based on the node offsets... called from
	 * selection handler after dragging has ended
	 */
	public void setFinalPositions() {
		for (WeakReference<WorldObject> object : selectedObjectsRef) {
			WorldObject node = object.get();

			if (node != null) {
				ObjectState state = objectStates.get(object);
				if (state != null) {
					state.setFinalState(node.getParent(), node.getOffset());
				}
			}
		}
	}

	@Override
	protected void action() throws ActionException {

		for (WeakReference<WorldObject> object : selectedObjectsRef) {
			WorldObject node = object.get();

			if (node != null) {
				ObjectState state = objectStates.get(object);
				PNode fParent = state.getFinalParentRef().get();
				if (fParent != null) {

					fParent.addChild(node);
					node.setOffset(state.getFinalOffset());

					node.justDropped();
				}
			}
		}
	}

	@Override
	protected void undo() throws ActionException {
		for (WeakReference<WorldObject> woRef : selectedObjectsRef) {
			WorldObject wo = woRef.get();
			if (wo != null) {
				if (isObjectDragUndoable(wo)) {
					ObjectState state = objectStates.get(woRef);

					PNode iParent = state.getInitialParentRef().get();

					if (iParent != null) {

						iParent.addChild(wo);
						wo.setOffset(state.getInitialOffset());

						wo.justDropped();
					}
				}
			}
		}
	}

	@Override
	protected boolean isReversable() {
		int numOfDraggableObjects = 0;
		for (WeakReference<WorldObject> woRef : selectedObjectsRef) {
			if (woRef.get() != null && isObjectDragUndoable(woRef.get())) {
				numOfDraggableObjects++;
			}
		}

		if (numOfDraggableObjects >= 1) {
			return true;
		} else {
			return false;
		}
	}
}

/**
 * Stores UI state variables required to do and undo drag operations.
 * 
 * @author Shu Wu
 */
class ObjectState {
	private WeakReference<PNode> iParent;
	private Point2D iOffset;
	private WeakReference<PNode> fParent;
	private Point2D fOffset;

	protected ObjectState(PNode initialParent, Point2D initialOffset) {
		super();
		this.iParent = new WeakReference<PNode>(initialParent);
		this.iOffset = initialOffset;
	}

	protected void setFinalState(PNode finalParent, Point2D finalOffset) {
		this.fParent = new WeakReference<PNode>(finalParent);
		this.fOffset = finalOffset;
	}

	protected WeakReference<PNode> getInitialParentRef() {
		return iParent;
	}

	protected Point2D getInitialOffset() {
		return iOffset;
	}

	protected WeakReference<PNode> getFinalParentRef() {
		return fParent;
	}

	protected Point2D getFinalOffset() {
		return fOffset;
	}

}
