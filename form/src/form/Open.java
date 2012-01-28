/**
* The class responsible for opening the config file
* @author Randy Levensalor
* @version 2.0b1
*/

package form;

import java.io.*;
import java.awt.*;
import java.net.*;

public class Open implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	* The reader for the input stream from the file
	*/
	private BufferedReader inReader = null;


	public Open(){
	   FileDialog fd = new FileDialog(new Frame(),"Save",FileDialog.LOAD);
	   fd.setVisible(true);
	   if (fd.getDirectory()!=null){
	      String fileName = fd.getDirectory()+fd.getFile();
	      open(fileName);
	   }
	}

	public boolean is(){
		return (inReader != null);
	}

	/**
	*@param String fileLocation Contains the location of
	* .npib file to be opened
	**/
   	public Open (String fileLocation){
		open(fileLocation);
	}

	public boolean open(String fileLocation){
   	   //checks if on a web page
	   if (fileLocation.startsWith("http://")){
      	      //opens the file on the server
      	      URL url = null;
	      try { 
                 url = new URL(fileLocation);
        	 inReader = new BufferedReader(new InputStreamReader(url.openStream()));
              }catch(IOException e) { System.out.println("Cannot open URL "+fileLocation); }
	   }else {  //a local file
	      FileReader f = null;
	      try {
		 f = new FileReader(fileLocation);
		 inReader = new BufferedReader(f);
	      } catch (IOException e) { System.out.println("Cannot open file "+fileLocation); }
	   }

	   if (inReader == null){
		return false;
	   }
	   else {
	     return true;
           }
	
         }

	/**
	* Returns the next line from the input file.
	* Returns null if eof or error
	*/
	public String getNext(){
	   String nextLine = null;
	   if (inReader == null) return null;
	   try {
	      if ( (nextLine = inReader.readLine()) ==null ) {
		 System.out.println("Trying to close");
		 inReader.close();
		 System.out.println("Closed");
		 nextLine = null;
	      }
	   } catch (IOException e) { System.out.println("Cannot read or close input stream");
		nextLine = null;
		e.printStackTrace();
		System.exit(12);
		}

		System.out.println("retreiving "+nextLine);
	      	return nextLine;
	}
	

	/**
	* Returns an int 
	* is unstable if it is not an int
	* MOre Microsoft release stuff
	*/
	public int getInt(){
		return Integer.valueOf(getNext()).intValue();
	}

	/**
	* Returns a double
	* is unstable if it is not a double
	* MOre Microsoft release stuff
	*/
	public double getDouble(){
		return Double.valueOf(getNext()).doubleValue();
	}
}
