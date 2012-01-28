package form;

import form.data.*;
import form.comp.*;
import form.edit.*;
import java.awt.*;
import java.util.*;
import java.applet.*;
import java.net.URL;


public class Viewer {

	public boolean edit = false;
	private Vector<FormPanel> panels = null;
	private FormMenu menu = null;
	private Data data = null;
	private Panel panel = null;
	private ScrollPane sPane = null;
	private OutputEditor oe = null;
	private FormPanel currFormPanel = null;
	private Applet applet = null;

	//a vector containing all components
	//this is only used for retrieving data
	//when for is submitted
	private Vector<FormInterface> comps  = null;

	private FormListener listen = null;

	static final String delimeter = "#";
	static final String END = "end";

	public void save(){
		//ask for location
		Save save = new Save();
		if (save.open()){
			for (int i = 0; i < panels.size(); i++){
				panels.elementAt(i).save(save);
			}
			save.println("data");
			oe.save(save);
			save.close();
		}
	}

	public Viewer(boolean edi,Panel p,Applet a){
		applet = a;
		edit = edi;
		panel = p;

		if (!edit){
			listen = new FormActionListener(this);
			data = new Data(this);
		}
		else {
			oe = new OutputEditor();
			menu = new FormMenu(this);
			//frame.setMenuBar(menu);
		}

		panels = new Vector<FormPanel>();
		FormPanel fp =  new FormPanel();
		panels.addElement(fp);
		comps  = new Vector<FormInterface>();
		setViewable("root");
	}

	public MenuBar getMenuBar(){
		return menu;
	}

	public void open(){

		Open open = new Open();
		if (open.is()){
			currFormPanel.setVisible(false);
			oe.setVisible(false);
			comps  = new Vector<FormInterface>();
			oe = new OutputEditor();
			initialize(open);
		}
	}

	public void newf(){
		currFormPanel.setVisible(false);
		oe.setVisible(false);
		panels = new Vector<FormPanel>();
		comps  = new Vector<FormInterface>();
		panels.addElement(new FormPanel());
		setViewable("root");
		panel.validate();
		oe = new OutputEditor();
	}

	public void initialize(Open open){

		String        className = "";//the class to be added
		Class	 newClass  = null;
		FormInterface newObject = null;
		panels = new Vector<FormPanel>();
		className = open.getNext();

		//set data to a const
		while ( className != null && !className.equals("data")){

			System.out.println("Viewer classname "+className);

			try {

				if (className.equals("form.comp.FormPanel")){
					FormPanel fp =  new FormPanel();
					fp.initialize(open);
					panels.addElement(fp);
				}
				else {

					//returns a Class for the named component
					newClass = Class.forName(className);
					//returns a new form object created with the empty constructor
					newObject = ((FormInterface)newClass.newInstance());


					if (!edit) newObject.addActionListener(listen);
					//sets the parameters for specific component
					newObject.initialize(open,edit);

					panels.lastElement().add(newObject);
					comps.addElement(newObject);
				}
			} catch (ClassNotFoundException e){System.out.println("Can't find class : "+className);
			} catch (Exception e) { e.printStackTrace();}

			className = open.getNext();

		}

		if (edit){
			oe.initialize(open);
		}
		else {
			data.initialize(open);
		}
		setViewable("root");
	}

	/**
	 *@param String name Checks if a component of this name exists
	 */
	public FormInterface getComp(String name){

		for (int i =0; i < comps.size(); i++) 
			if ( comps.elementAt(i).getName().equals(name) )
				return comps.elementAt(i);

		return null;
	}

	/**
	 *@param String name Checks if a component of this name exists
	 */
	public boolean contains(String name){

		for (int i =0; i < comps.size(); i++) 
			if ( comps.elementAt(i).getName().equals(name) )
				return true;

		return false;
	}

	/**
	 *@param String name sets the panel with this name viewable
	 * if name does not exist the default or root panel is displayed
	 */
	public FormPanel setViewable(String name){
		FormPanel curr = null;
		//should create a generic container so both pane and scroll panel are smoother

		//should probably move to constructor
		if (edit && (sPane==null) ){
			sPane = new ScrollPane();
			sPane.setVisible(true);
			panel.add(sPane);
			panel.validate();
		}

		if (name.equals("root")){
			curr = panels.elementAt(0);
		}
		else {
			for (int i =0; i < panels.size(); i++)
				if ( panels.elementAt(i).getName().equals(name) )
					curr = panels.elementAt(i);
		}

		if (currFormPanel != null){
			currFormPanel.setVisible(false);
			if (edit)  sPane.remove(currFormPanel);
			else panel.remove(currFormPanel);
		}

		currFormPanel = curr;

		currFormPanel.setVisible(true);

		if (edit){
			sPane.add(curr);
			sPane.validate();
		}
		else {
			panel.add(curr);
			panel.validate();
		}

		return curr;
	}


	/**
	 * @param FormInterface fi A new form interface object
	 * this method is called from from the form menu.
	 * It is used to insert new objects after the initial
	 * creation
	 */
	public void add(FormInterface fi){

		currFormPanel.add(fi);
		comps.addElement(fi);
		currFormPanel.validate();
	}

	public void removeSelected(){
		currFormPanel.removeSelected();
		currFormPanel.validate();

	}

	public void submit(){
		Save save = new Save("socket://",applet);
		data.getText(save);
		String directory = save.close();
		if (directory != null){
			URL  url = applet.getDocumentBase();
			String link = url.getProtocol()+"://"+ url.getHost() +"/"+directory;
			System.out.println("Built url"+link);
			try {
				applet.getAppletContext().showDocument(new URL(link),"_blank");
			} catch (java.net.MalformedURLException e){System.err.println("can't open url");}
		}
	}
}
