package form.comp;

import java.awt.*;
import form.*;

//Extends GridBagConstraints and implements a custom property editor
//there is probably a better way to do this, but I don't know it
public class Constraints extends GridBagConstraints {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Constraints(){
		super();
	   	//default values to be set on all objects
	   	insets = new Insets(2,2,2,2);
	   	fill = GridBagConstraints.BOTH;
	   	weightx = 1.0;
	}

	/** 
	* @param Open open 
	* Format:
	* weightx
	* weighty
	* gridheight 
	* gridwidth 
	*/
	public void initialize(Open open){

	   //default values to be set on all objects
	   insets = new Insets(2,2,2,2);
	   fill = GridBagConstraints.BOTH;

	   weightx = open.getDouble();
	   weighty = open.getDouble();
	   gridheight = open.getInt();
	   gridwidth = open.getInt();
	}
	   
	public void save(Save save){
		save.println(weightx);
		save.println(weighty);
		save.println(gridheight);
		save.println(gridwidth);
	}
}
