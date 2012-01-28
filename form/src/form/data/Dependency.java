package form.data;

import java.io.*;

public class Dependency implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String left = null;
	private String oper = null;
	private String right = null;

	public Dependency(String raw){
		//index of the operator
		int index = 0;
		if ( (index = raw.indexOf("==")) !=-1){
			left = raw.substring(0,index);
			oper = raw.substring(index, index+2);
			right = raw.substring(index+2);
		}
		else if ( (index = raw.indexOf("<=")) !=-1){
			left = raw.substring(0,index);
			oper = raw.substring(index, index+2);
			right = raw.substring(index+2);
		}
		else if ( (index = raw.indexOf(">=")) !=-1){
			left = raw.substring(0,index);
			oper = raw.substring(index, index+2);
			right = raw.substring(index+2);
		}
		else if ( (index = raw.indexOf("<")) !=-1){
			left = raw.substring(0,index);
			oper = raw.substring(index, index+1);
			right = raw.substring(index+1);
		}
		else if ( (index = raw.indexOf(">")) !=-1){
			left = raw.substring(0,index);
			oper = raw.substring(index, index+1);
			right = raw.substring(index+1);
		}
	} 

	public String left(){
		return left;
	}

	public String right(){
		return right;
	}

	public String oper(){
		return oper;
	}

}
