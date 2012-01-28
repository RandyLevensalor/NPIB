package form.comp;

import form.*;
import java.awt.*;
import java.awt.event.*;

public class FormLabel extends java.awt.Label implements FormInterface{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	* The specific constraints for this object
	*/
	private Constraints constraints = new Constraints();

	/**
	* The name of this object.
	* it is (should) be unique over the entire form
	*/
	private String name    = null;

	/**
	* The initial value of the of this object
	* Used to reset the values in the form.
	* otherwise it is basically useless.
	* Is only modified by the setText method
	*/
	private String initVal = null;

	/**
	* Says if this object has the focus of the form
	* window during editing.
	* Is only used when in edit mode
	*/
	protected boolean selected = false;

	/**
	* Resets this object to its default value
	* Should only be used in default mode.
	*/
	public void reset(){
		setText(initVal);
	}

	/**
	* Returns the Component representation of this object.
	* It is assumed that all FormInterface comps will
	* extend a component.  If not this will help the work around
	* it also simplifies casting.
	*/
	@Override
	public Component getComponent(){
		return this;
	}

	/**
	* The default / blank constructor
	* A blank constructor must exist for the creation
	* from the string representation of the class (In Viewer.java)
	*/
	public FormLabel(){	
		super();
	}

	/**
	* @param String name The name of the object to be created
	* This constructor is used with the insert method during edit.
	*/
	public FormLabel(String nam){	
		super();
		setName(nam);
		setText(nam);
		setVisible(true);
		new FormInterfaceListener(this);
		
	}

	/**
	* @param boolean set True if object is selected
	* used to set the selected object
	*/
	@Override
	public void setSelect(boolean sel){
		selected = sel;
	}

	/**
	* Used to get and type cast the GridBagConstraints
	* for this object.
	*/
	@Override
	public GridBagConstraints getGridBagConstraints(){
		return constraints;
	}


	/**
	*@param Open open file open object
	* reads the file one line at a time
	* format is:
	* NOTE: update and sync this with the same method
	* The name of this object
	* The initial text
	*/
	@Override
	public void initialize(Open open,boolean edit){

		setName( open.getNext() );

		this.setText( open.getNext() );

		setBackground(new Color(open.getInt()) );

		setForeground(new Color(open.getInt()) );

		setFont( new Font(open.getNext(), open.getInt(), open.getInt()) );

		constraints.initialize( open);

		setVisible(true);

		if (edit)
			new FormInterfaceListener(this);
	}

	/**
	* @param Save save The save object which writes
	* the output to the designated location
	* Note: This method should always replicat initialize(...);
	*/
	@Override
	public void save(Save save){
		save.println("form.comp.FormLabel");
		save.println(getName());
		save.println(getText());
		save.println(getBackground().getRGB());
		save.println(getForeground().getRGB());
		save.println(getFont().getName() );
		save.println(getFont().getStyle() );
		save.println(getFont().getSize() );
		constraints.save(save);
	}

	@Override
	public void setText(String str){
		initVal = str;
		super.setText(str);
	}

	public Constraints getConstraints(){
		return constraints;
	}

	public void setConstraints(Constraints nconst){
		System.out.println("Constraint now : "+nconst.gridheight);
		constraints = nconst;
	}

	@Override
	public String getName(){
		return name;
	}

	@Override
	public void setName(String str){
		name = str;
		System.out.println("Name is now : "+str);
	}

	@Override
	public Dimension getPreferredSize(){
		return new Dimension (50, 30);
	}

	public void addListener(FormActionListener listen){
		addMouseListener(listen);
	}

	//does nothing, just for FormInterface Compatibility
	@Override
	public void addActionListener(ActionListener listen){
	}
}
