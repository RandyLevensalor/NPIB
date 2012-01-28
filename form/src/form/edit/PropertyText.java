
// Support for a PropertyEditor that uses text.

package form.edit;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;

class PropertyText extends TextField implements KeyListener, FocusListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	PropertyText(PropertyEditor pe) {
	super(pe.getAsText());
	editor = pe;
	addKeyListener(this);
	addFocusListener(this);
    }

    @Override
	public void repaint() {
	setText(editor.getAsText());
    }

    protected void updateEditor() {
	try {
	    editor.setAsText(getText());
	} catch (IllegalArgumentException ex) {
	    // Quietly ignore.
	}
    }
    
    //----------------------------------------------------------------------
    // Focus listener methods.

    @Override
	public void focusGained(FocusEvent e) {
    }

    @Override
	public void focusLost(FocusEvent e) {
    	updateEditor();
    }
    
    //----------------------------------------------------------------------
    // Keyboard listener methods.

    @Override
	public void keyReleased(KeyEvent e) {
 	if (e.getKeyCode() == KeyEvent.VK_ENTER) {
	    updateEditor();
	}
    }

    @Override
	public void keyPressed(KeyEvent e) {
    }

    @Override
	public void keyTyped(KeyEvent e) {
    }

    //----------------------------------------------------------------------
    private PropertyEditor editor;
}
