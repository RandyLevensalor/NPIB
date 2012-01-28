package form.data;

import java.util.StringTokenizer;
import java.util.Vector;


public class DataElement {

	public static final int STRING = 0;
	public static final int FILE = 1;
	public static final int LINK = 2;
	public static final int STRING2 = 3;

	//Either a constant (string) or link to a form element
  	private int type	  = STRING;

  	//The string to be printed
  	//or the name of the form element which contains the data
  	private String value    = null;

  	//String containing the formatting information
  	//also whether or not to pruing the data
  	private Vector<String> depends  = new Vector<String>();	
 
  	public DataElement(int typ, String val){

           type    = typ;

           if (type == STRING2){
             System.out.println("String2");
	     StringTokenizer s = new StringTokenizer(val,"\"");
           
             value   = s.nextToken();
             System.out.println("val of str "+value);
               
             while (s.hasMoreTokens()){
	         String temp = s.nextToken();
		 System.out.println("the Depends "+temp);
		 depends.addElement(temp);
	     }  	
           } 

	   else if (type!=STRING){
	     StringTokenizer s = new StringTokenizer(val," ,");
           
             value   = s.nextToken();
	     System.out.println("val "+val+" Value "+value);

             while (s.hasMoreTokens()){
	         String temp = s.nextToken();
		 System.out.println("the Depends "+temp);
		 depends.addElement(temp);
	     }  	
	   }
	   else {
	     value = val;
	   }
        }

	public boolean isString2(){
		return (type == STRING2);
	}

	public boolean isFile(){
		return (type == FILE);
	}

	public boolean isLink(){
		return (type == LINK);
	}

	public int getType(){
		return type;
	}

	public void setType(int typ){
		if ( (typ !=(STRING)) || (typ != (FILE)) || (typ != (LINK)) ){ 
			System.out.println("Invalid data type using : "+STRING);
			type = STRING;
		}
		else
			type = typ;
	}

	public String getValue(){
		return value;
	}

	public void setValue(String str){
		value = str;
	}

	public Vector<String> getDepends(){
		return depends;
	}

	public void setDepends(Vector<String> str){
		depends = str;
	}

}
