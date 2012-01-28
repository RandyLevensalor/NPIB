package form.comp;

import form.*;
import java.awt.*;
import java.io.*;

public class FormPanel extends java.awt.Panel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Font font = null;
	private FormInterface current = null;

	//the listener for the selected object
	private FormInterfaceListener selected = null;

	public void setCurrent(FormInterface curr){
		current = curr;
	}

	public void save(Save save){
		save.println("form.comp.FormPanel");
		save.println(getName());
		Component[] all = getComponents();
		for (int i = 0; i < all.length; i++){
			((FormInterface)all[i]).save(save);
		}
	}

	/**
	 * Moves a component withing a form
	 * @param FormInterface src The Object to be moved
	 * @param Point dest The desired location
	 * the src object will be placed before the dest object in the form.
	 */
	public void moveComp(FormInterface src, Point dest){
		//the index the component will be moved to
		int index =0;
		//Component temp = src.getComponent().getComponentAt(dest);
		//may have to check if in panel
		if (src == current)
			return;
		if (current == null) {
			index = getComponentCount();
		}
		else {
			index = getIndex(current.getComponent()) ;
		}
		remove(src.getComponent());
		addImpl(src.getComponent(),src.getGridBagConstraints(),index);
		validate();
	}

	public FormPanel (){
		super(new GridBagLayout());
	}

	public void initialize(Open open){
		setName(open.getNext());

	}

	public void removeSelected(){
		//remove from viewer
		selected.setSelect(false);
		remove(selected.getComponent());
	}

	public void register(FormInterfaceListener al){
		if (selected != null){
			((GridBagLayout)getLayout()).setConstraints(selected.getComponent(),((FormInterface)selected.getComponent()).getGridBagConstraints());
			selected.getComponent().invalidate();
			selected.setSelect(false);

		}

		selected = al;
		selected.setSelect(true);

		validate();
	}

	public Dimension getPreferredSized(){
		return new Dimension (500, 500);
	}

	public java.awt.Component add(FormInterface f){
		if (selected != null){
			int index = getIndex(current.getComponent()) ;
			return add((Component)f,index);
		} else {
			return add((Component)f);
		}
	}

	@Override
	public java.awt.Component add(Component c){

		//adds the comp to the end with its specified constraints
		super.addImpl(c,((FormInterface)c).getGridBagConstraints(),-1);
		return c;
	}

	public void printComps(){

		for (int i =0; i < getComponentCount(); i++)
			System.out.println(getComponent(i).toString());
	}

	@Override
	public void setFont(Font newFont){
		font = newFont;
		for (int i =0; i < getComponentCount(); i++)
			getComponent(i).setFont(font);
	}

	public int getIndex(Component c){
		Component[] all = getComponents();

		for (int i = 0; i < all.length; i++){
			if (all[i] == c)
				return i;
		}

		return -1;
	}

}
