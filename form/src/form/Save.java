/**
 * The class responsible for opening the config file
 * @author Randy Levensalor
 * @version 2.0b1
 */

package form;

import java.io.*;
import java.applet.Applet;
import java.awt.*;
import java.net.*;

public class Save implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * The writer for the output stream to the file
	 */
	private PrintWriter outWriter = null;


	private BufferedReader in = null;
	public Save(){
	}

	public boolean open(){
		FileDialog fd = new FileDialog(new Frame(),"Save",FileDialog.SAVE);
		fd.setVisible(true);
		if (fd.getDirectory()!=null){
			String fileName = fd.getDirectory()+fd.getFile();
			openFile(fileName);
			return true;
		}
		else return false;
	}

	/**
	 *@param String fileLocation Contains the location of
	 * .npib file to be opened
	 **/
	public Save (String fileLocation,Applet applet){

		//checks if is via ftp
		if (fileLocation.startsWith("ftp://")){
			//opens the file on the server
		}
		else if (fileLocation.startsWith("socket://")){
			try {
				String serverName = "localhost";
				if (applet != null && applet.getDocumentBase().getHost() != ""){
					serverName = applet.getDocumentBase().getHost();
				}
				Socket socket = new Socket(serverName,5000);
				outWriter =     new PrintWriter(socket.getOutputStream(), true);
				in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
			}
			catch(IOException e) { System.out.println("Cannot connect to server");
			
			}
		}
		else {  //a local file
			openFile(fileLocation);
		}

	}

	public void openFile(String fileName){
		FileWriter fw = null;
		try {
			fw = new FileWriter(fileName);
			outWriter = new PrintWriter(fw,true);
		} catch (IOException e) { System.out.println("Cannot open file "+fileName); }
	}

	public void println(int nextLine){
		outWriter.println(nextLine+"");
		System.out.println("writing "+nextLine);
	}

	public void println(double nextLine){
		outWriter.println(nextLine+"");
		System.out.println("writing "+nextLine);
	}

	public void println(String nextLine){
		if ( nextLine != null ) {
			outWriter.println(nextLine);
		}
		System.out.println("writing "+nextLine);
	}

	public void print(String nextLine){
		if ( nextLine != null ) {
			outWriter.print(nextLine);
		}
		System.out.println("writing "+nextLine);
	}


	public String close(){
		String ret = null;
		System.out.println("getting URL");
		try{
			ret = in.readLine();
			System.out.println("url is "+ret);
			//in.close();
		} catch(java.io.IOException e){System.err.println("Can't get url");}
		catch(java.lang.NullPointerException e){System.err.println("no url");}
		outWriter.close();
		return ret;
	}
}
