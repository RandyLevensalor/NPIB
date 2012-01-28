package form;

import form.data.*;
import java.awt.*;
import java.awt.event.*;

public class FormActionListener implements FormListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Viewer viewer = null;

	public void setData(Data dat) {
	}

	public FormActionListener(Viewer v) {
		viewer = v;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.isPopupTrigger()) {
			System.out.println("popup event");
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		Button b = ((Button) e.getSource());
		String params = b.getActionCommand();
		if (command.startsWith("panel")) {
			System.out.println("Params for button: " + params);
			viewer.setViewable(params);
			// d.setVisible(true);
			return;
		} else if (command.startsWith("submit")) {
			System.out.println("Submitting");
			viewer.submit();
			System.exit(1);
		} else if (command.startsWith("dismiss")) {
			((Window) b.getParent().getParent()).dispose();
		}
	}

}
