package form.edit;

import form.comp.*;
import form.*;
import java.awt.* ;
import java.awt.event.* ;

public class EditListener implements FormListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PropertySheet propSheet = null;

	private Component selected = null;

	public void setData(){
	}

	public EditListener (Viewer v){
		propSheet = new PropertySheet(600,0);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Component c = e.getComponent();
		//add and exit if parent not FormPanel
		FormPanel p = ((FormPanel)c.getParent());
		p.validate();

		if (selected == null){
			c.setBackground(Color.blue);
			selected    = c;
			propSheet.setTarget(selected);
		}
		else if (selected == c){
			selected.setBackground(Color.lightGray);
			selected = null;
			return;
		}
		//selected != null 
		//will move new comp before (may change) old comp
		else {
			p.remove(selected);
			int location = p.getIndex(c);
			selected.setBackground(Color.lightGray);
			p.add(selected,location);
			selected = null;
		}

		p.validate();
	}

	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void actionPerformed (ActionEvent e) {
	}

}
