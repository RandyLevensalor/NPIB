package form.edit;

import form.*;
import form.comp.*;
import java.awt.*;
import java.awt.event.*;


public class FormMenu extends MenuBar implements ActionListener, ItemListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Menu edit = null;
	private Menu insert = null;
	private Viewer viewer = null;

	public FormMenu(Viewer viewe){
		super();

		viewer = viewe;

		//File Menu
		Menu file = new Menu("File");

		MenuItem newf = new MenuItem("New");
		newf.setActionCommand("newf");
		file.add(newf);

		MenuItem open = new MenuItem("Open");
		open.setActionCommand("open");
		file.add(open);

		MenuItem save = new MenuItem("Save");
		save.setActionCommand("save");
		file.add(save);

		MenuItem exit = new MenuItem("Exit");
		exit.setActionCommand("exit");
		file.add(exit);

		add(file);
		file.addActionListener(this);

		MenuItem remove = new MenuItem("Remove");
		remove.setActionCommand("remove");
		edit = new Menu("Edit");
		edit.add(remove);

		add(edit);
		edit.addActionListener(this);

		insert = new Menu("Insert");

		MenuItem textField = new MenuItem("Text Field");
		textField.setActionCommand("FormTextField");
		insert.add(textField);

		MenuItem label = new MenuItem("Label");
		label.setActionCommand("Label");
		insert.add(label);

		MenuItem button = new MenuItem("Button");
		button.setActionCommand("FormButton");
		insert.add(button);

		add(insert);
		insert.addActionListener(this);

		
	}

    @Override
	public void actionPerformed(ActionEvent e) {
        System.out.println("\"" + e.getActionCommand()
                      + "\" action detected in menu labeled \""
                      + ((MenuItem)(e.getSource())).getLabel()
                      + "\".");

		if (e.getActionCommand().equals("newf") ){
			viewer.newf();
		}

		if (e.getActionCommand().equals("open") ){
			viewer.open();
		}

		if (e.getActionCommand().equals("save") ){
			viewer.save();
		}

		if (e.getActionCommand().equals("exit") ){
			System.exit(1);
		}

		else if ( ((MenuItem)(e.getSource())).getLabel().equals("Insert") ){
			AskDialog ad = new AskDialog("Insert","Enter Unique name.");
			String name = ad.answer();

			while ( viewer.contains(name) ){
			   //ErrorDialog ed = new ErrorDialog(new Frame(), "Name must be unique");
				ad.setVisible(true);
			   name = ad.answer();
			}
				
			if (name != null) {

		         if (e.getActionCommand().equals("FormTextField") ){
			   viewer.add(new FormTextField(name));
		         }
		         if (e.getActionCommand().equals("Label") ){
			   viewer.add(new FormLabel(name));
		         }
		         else if (e.getActionCommand().equals("FormButton") ){
			   viewer.add(new FormButton(name));
		         }
		   }
		}

		else if (e.getActionCommand().equals("remove") ){
			viewer.removeSelected();
		}
    }

    @Override
	public void itemStateChanged(ItemEvent e) {
        System.out.println("Item state change detected on item \""
                      + e.getItem()
                      + "\" (state is " 
                      + ((e.getStateChange() == 
                            ItemEvent.SELECTED)? 
                            "selected)."
                          : "deselected).")
                      );
    }

}
