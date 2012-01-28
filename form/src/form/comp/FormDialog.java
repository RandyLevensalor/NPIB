package form.comp;

import java.awt.*;
import java.io.*;

public class FormDialog extends Panel implements  Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BorderLayout border = null;
	private String title = "";
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(600,600);
	}

	/**
	 *@param Frame Patent speaks for it's self
	 *@param Sting formDescriptor Either describes the form or the location of the 
	 * descriptor file.  Currently only url's are accepted should be easy to add more
	 * methods.  Possible add a different constructor to take BUffered Reader.
	 **/
	public FormDialog (){
		super();
		border = new BorderLayout();
		setSize(getPreferredSize());
		setLayout(border);
		//f = parent;

	}


	public String getTitle(){	return title;}

	@Override
	public Component add(Component c){
		c.setSize(c.getPreferredSize());
		c.setVisible(true);

		border.addLayoutComponent(c,"Center");

		super.add(c);

		//will have to change if more then one component will be in a given dialog
		setVisible(false);
		//setVisible(true);
		return c;
	}

}
