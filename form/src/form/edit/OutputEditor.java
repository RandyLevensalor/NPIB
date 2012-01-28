package form.edit;

import form.*;
import java.awt.*;
import java.io.*;

public class OutputEditor extends Frame implements  Serializable{

   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private BorderLayout border = null;

   private TextField command = null;
   private TextField args    = null;
   private TextField email   = null;
   private TextArea   output  = null;

   public void save(Save save){
	save.println(command.getText());
	save.println(email.getText());
	save.println(args.getText());
	save.println(output.getText());
   }

   @Override
public Dimension getPreferredSize(){
                return new Dimension(500,600);
   }


   //add validate button to create a data object
   public OutputEditor (){
	super("Output Editor");
	setLocation(600,0);

	new WindowCloser(this);
      border = new BorderLayout();
      setSize(getPreferredSize());
      setLayout(border);

	  
	  Panel p = new Panel();

	  p.setLayout(new FlowLayout());
	  p.setVisible(true);
	  border.addLayoutComponent(p,"Center");

	  p.add(new Label("Command Name    "));
	  p.add(new Label("Arguments to be passed to shell script                "));
	  command = new TextField(12);
	  command.setVisible(true);
	  p.add(command);
	  args	  = new TextField(40);
	  args.setVisible(true);
	  p.add(args);
	  p.add(new Label("Email address to send results to                "));
	  email	  = new TextField(40);
	  email.setVisible(true);
	  p.add(email);
	  output  = new TextArea(20,55);
	  output.setVisible(true);
	  p.add(new Label("The format for the file(s) to be sent to the server"));
	  p.add(output);

	  

	  super.add(p);

	  setVisible(true);
	}

	public void initialize(Open open){
	   command.setText(open.getNext());
	   email.setText(open.getNext());
	   args.setText(open.getNext());

	   String nextLine= open.getNext();
	   String line = "";
	   while (nextLine != null) {
              line += nextLine+"\n";
              nextLine = open.getNext();
           }
	   output.setText(line);

	}

}
