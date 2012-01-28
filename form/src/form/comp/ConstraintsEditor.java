// This example is from the book "Java in a Nutshell, Second Edition".
// Written by David Flanagan.  Copyright (c) 1997 O'Reilly & Associates.
// You may distribute this source code for non-commercial purposes only.
// You may study, modify, and use this example for any purpose, as long as
// this notice is retained.  Note that this example is provided "as is",
// WITHOUT WARRANTY of any kind either expressed or implied.

package form.comp;

import java.beans.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class is a custom editor for the message property of the YesNoDialog
 * bean. It is necessary because the default editor for properties of type
 * String does not allow multi-line strings to be entered.
 */
public class ConstraintsEditor implements PropertyEditor {
	protected Constraints constraints; // The value we will be editing.

	@Override
	public void setValue(Object o) {
		constraints = ((Constraints) o);
	}

	@Override
	public Object getValue() {
		return constraints;
	}

	@Override
	public void setAsText(String s) {
	}

	@Override
	public String getAsText() {
		return "";
	}

	@Override
	public String[] getTags() {
		return null;
	} // not enumerated; no tags

	// Say that we allow custom editing.
	@Override
	public boolean supportsCustomEditor() {
		return true;
	}

	// Return the custom editor. This just creates and returns a TextArea
	// to edit the multi-line text. But it also registers a listener on the
	// text area to update the value as the user types and to fire the
	// property change events that property editors are required to fire.
	@Override
	public Component getCustomEditor() {

		final Panel p = new Panel();

		final Label lweightx = new Label("Weight X");
		lweightx.setVisible(true);
		p.add(lweightx);
		final TextField weightx = new TextField(constraints.weightx + "");
		weightx.setVisible(true);
		p.add(weightx);

		final Label lweighty = new Label("Weight Y");
		lweighty.setVisible(true);
		p.add(lweighty);
		final TextField weighty = new TextField(constraints.weighty + "");
		weighty.setVisible(true);
		p.add(weighty);

		final Label lgridheight = new Label("Grid Height");
		lgridheight.setVisible(true);
		p.add(lgridheight);
		final TextField gridheight = new TextField(constraints.gridheight + "");
		gridheight.setVisible(true);
		p.add(gridheight);

		final Label lgridwidth = new Label("Grid Width");
		lgridwidth.setVisible(true);
		p.add(lgridwidth);
		final TextField gridwidth = new TextField(constraints.gridwidth + "");
		gridwidth.setVisible(true);
		p.add(gridwidth);

		TextListener tl = new TextListener() {
			@Override
			public void textValueChanged(TextEvent e) {
				constraints.weightx = Double.valueOf(weightx.getText())
						.doubleValue();
				constraints.weighty = Double.valueOf(weighty.getText())
						.doubleValue();
				constraints.gridheight = Integer.valueOf(gridheight.getText())
						.intValue();
				constraints.gridwidth = Integer.valueOf(gridwidth.getText())
						.intValue();
				listeners.firePropertyChange("gridwidth", null, null);
			}
		};

		weighty.addTextListener(tl);
		weightx.addTextListener(tl);
		gridheight.addTextListener(tl);
		gridwidth.addTextListener(tl);

		return p;
	}

	// Visual display of the value, for use with the custom editor.
	// Just print some instructions and hope they fit in the in the box.
	// This could be more sophisticated.
	@Override
	public boolean isPaintable() {
		return true;
	}

	@Override
	public void paintValue(Graphics g, Rectangle r) {
		g.setClip(r);
		g.drawString("Click to edit...", r.x + 5, r.y + 15);
	}

	// Important method for code generators. Note that it
	// ought to add any necessary escape sequences.
	@Override
	public String getJavaInitializationString() {
		return "\"" + constraints + "\"";
	}

	// This code uses the PropertyChangeSupport class to maintain a list of
	// listeners interested in the edits we make to the value.
	protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);

	@Override
	public void addPropertyChangeListener(PropertyChangeListener l) {
		listeners.addPropertyChangeListener(l);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener l) {
		listeners.removePropertyChangeListener(l);
	}
}
