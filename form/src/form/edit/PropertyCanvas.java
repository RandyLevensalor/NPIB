
// Support for drawing a property value in a Canvas.

package form.edit;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;

class PropertyCanvas extends Canvas implements MouseListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	PropertyCanvas(Frame frame, PropertyEditor pe) {
	this.frame = frame;
	editor = pe;
	addMouseListener(this);
    }

    @Override
	public void paint(Graphics g) {
	Rectangle box = new Rectangle(2, 2, getSize().width - 4, getSize().height - 4);
	editor.paintValue(g, box);
    }

    private static boolean ignoreClick = false;

    @Override
	public void mouseClicked(MouseEvent evt) {
	if (! ignoreClick) {
	    try {
		ignoreClick = true;
		int x = frame.getLocation().x;
		int y = frame.getLocation().y + 50;
		new PropertyDialog(frame, editor, x, y);
	    } finally {
		ignoreClick = false;
	    }
	}
    }

    @Override
	public void mousePressed(MouseEvent evt) {
    }

    @Override
	public void mouseReleased(MouseEvent evt) {
    }

    @Override
	public void mouseEntered(MouseEvent evt) {
    }

    @Override
	public void mouseExited(MouseEvent evt) {
    }

    private Frame frame;
    private PropertyEditor editor;
}
