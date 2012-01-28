package form.data;

import form.*;
import java.util.Vector;
import java.io.*;

public class Data implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector<DataElement> output = null;
	private Viewer viewer = null;

	public Data(Viewer v){
		viewer  = v;
		output  = new Vector<DataElement>();
	}

	//Initializes the data for use in submitting forms
	public void initialize(Open open){

		System.out.println("data: ");
		String   line = "";
		String   nextLine = "";
		//Creates one long string consisting of all the data to submit
		nextLine = open.getNext();

		while (nextLine != null) {
			line += nextLine+"\n";
			nextLine = open.getNext();
		}

		add(line);
	}

	/**
	 * takes one string consisting of the entire 
	 * output formatting
	 * @param String data the output	
	 */
	public void add(String data){

		int linkAddr = 0;
		int fileAddr = 0;
		int striAddr = 0;
		int next =0;
		int nextType = 0;

		String newData = "";

		//a scratch var used for new data elements
		//while they are being initialized
		//access the last element in the vector would work
		//but that is more confusing and probably more expensive
		DataElement de = null;


		//add support for " " so <file and <link may be strings
		while (next != -1){
			linkAddr = data.indexOf("<link=");
			fileAddr = data.indexOf("<file=");
			striAddr = data.indexOf("<string=");

			if ( ( ( (fileAddr < linkAddr) || (linkAddr ==-1) ) && (fileAddr!=-1) ) && ( ( (fileAddr < striAddr) || (striAddr ==-1) ) && (fileAddr!=-1) ) ){
				next	 = fileAddr;
				nextType = DataElement.FILE;
			}
			else if ( ( (linkAddr < striAddr) || (striAddr ==-1) ) && (linkAddr!=-1) ){
				next	 = linkAddr;
				nextType = DataElement.LINK;
			}
			else {
				next	 = striAddr;
				nextType = DataElement.STRING2;
			}

			//the next output token is a link or file
			if ( (linkAddr == 0) || (fileAddr==0) ){
				next = data.indexOf("\\>");

				//change 6 to length of consts
				//also is a new type is added with a different size
				//qualifier this will have to be changed
				newData = data.substring(6,next);
				data = data.substring(next+2);
			}
			//is a string with dependencies

			else if ( striAddr == 0){
				next = data.indexOf("\\>");

				System.out.println("string cond");
				//change 8 to length of consts
				//also is a new type is added with a different size
				//qualifier this will have to be changed
				newData = data.substring(8,next);
				data = data.substring(next+2);
			}
			//is a string
			else {
				nextType = DataElement.STRING;
				if (next !=-1){
					newData = data.substring(0,next);
					data = data.substring(next);
				}
				else {
					newData = data;
					data = null;
				}

				System.out.println("STR:New data "+newData);
				System.out.println("STR:Rest of data "+data);
			}


			de = new DataElement(nextType,newData);
			//checks for existing Viewer Object if link
			if ( (!de.isLink()) || (viewer.contains(de.getValue())) )
				output.addElement(de);
			else System.err.println("Could not find link to : "+de.getValue());

		}



	}


	public void getText(Save save){

		DataElement de = null;

		for (int i = 0; i < output.size(); i++){

			de = output.elementAt(i);
			Vector<String> dep = de.getDepends();
			if ( checkDepends(dep) ){
				if (de.isFile())
					//denotes a new file whose name is the value
					save.println("***NEW***");
				//Currently not supported
				if (de.isLink())
					save.print(viewer.getComp(de.getValue()).getText());
				else 
					save.print(de.getValue());
			}
		}

	}

	//disabled for now
	private boolean checkDepends(Vector<String> dep){
		Dependency d = null;
		if (dep == null){
			return true;
		}

		//probably should make this it's own class
		for (int I = 0 ; I < dep.size(); I++){
			d = new Dependency(dep.elementAt(I));
			//the value at data location loc must be the same as
			//this will work on strings as well as numbers
			if (d.oper().equals("==")){
				if (!viewer.getComp(d.left()).getText().equals(d.right()) ){
					return false;
				}
			}

			else if (d.oper().equals("<=")){
				if ( (Integer.valueOf(viewer.getComp(d.left()).getText()).intValue() >  Integer.valueOf(d.right()).intValue()) ){
					return false;
				}
			}
			else if (d.oper().equals(">=")){
				if ( (Integer.valueOf(viewer.getComp(d.left()).getText()).intValue() <  Integer.valueOf(d.right()).intValue()) ){
					return false;
				}
			}
			else if (d.oper().equals("<")){
				if ( (Integer.valueOf(viewer.getComp(d.left()).getText()).intValue() >=  Integer.valueOf(d.right()).intValue()) ){
					return false;
				}
			}
			else if (d.oper().equals(">")){
				if ( (Integer.valueOf(viewer.getComp(d.left()).getText()).intValue() <=  Integer.valueOf(d.right()).intValue()) ){
					return false;
				}
			}
			else if (d.oper().equals("!=")){
				if ( (Integer.valueOf(viewer.getComp(d.left()).getText()).intValue() !=  Integer.valueOf(d.right()).intValue()) ){
					return false;
				}
			}
		}


		return true;

	}

}
