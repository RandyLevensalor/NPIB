package form.comp;

import form.*;
import form.edit.*;
import java.awt.*;
import java.awt.event.*;

public class FormInterfaceListener implements java.awt.event.MouseMotionListener, 
java.awt.event.MouseListener {


	private boolean moving = false;
	private PropertySheet prop = null;
	protected boolean selected	= false;
	private FormInterface comp = null;

	public Component getComponent(){
		return comp.getComponent();
	}

	public FormInterfaceListener(FormInterface fi){
		comp = fi;
		comp.addMouseMotionListener(this);
		comp.addMouseListener(this);
	}

	//MouseMotionListener
	@Override
	public void mouseMoved(MouseEvent e){
	  //System.out.println("Moved "+comp.getName()+" "+e.paramString());
	}

	//MouseMotionListener
	@Override
	public void mouseDragged(MouseEvent e){
	   moving = true;
	  //System.out.println("Dragged "+comp.getName()+" "+e.paramString());
        }
	//end Mouse MotionListener

	//MouseListener
	@Override
	public void mouseClicked(MouseEvent e){ 

		((FormPanel)comp.getParent()).register(this);
	}

	public void setSelect(boolean sel){

		if (sel){
			//creates a new property sheet if need be
			if (prop == null){
				prop = new PropertySheet(0,600);
				prop.setTarget(comp);
			}
			//sets it visible if it doens't
			prop.setVisible(true);
		}
		//hides property sheet if de-selected
		else {
			prop.setVisible(false);
		}

		selected = sel;
		comp.setSelect(sel);

        	  //System.out.println("Selected "+comp.getName());
	}

	@Override
	public void mouseEntered(MouseEvent e){ 
		((FormPanel)comp.getParent()).setCurrent(comp);
		  //System.out.println("Entered "+comp.getName());
	}

	@Override
	public void mouseExited(MouseEvent e){ 
		  //System.out.println("Exited "+comp.getName());
	}

	@Override
	public void mousePressed(MouseEvent e){ 
		moving = false;
		  //System.out.println("Pressed "+comp.getName());
	}

	@Override
	public void mouseReleased(MouseEvent e){
	    if (moving){
	       Point dest = comp.getComponent().getLocation();
	       dest.x += e.getPoint().x;
	       dest.y += e.getPoint().y;
		  //System.out.println("X="+dest.x+"Y="+dest.y);
	       ((FormPanel)comp.getParent()).moveComp(comp,dest);
	    }
		  //System.out.println("Released "+comp.getName());
	}


}
