package form.receiver;
import java.net.*;
import java.io.*;
import java.lang.Runtime;
import java.util.*;

public class ReceiverThread extends Thread {

	private static final String passwordFile = "/www/jwave/applets98/HMPass/commands/passwdfile";

	/**
	* The document root where the results will be posted
	*/
	static final String docRoot = "/www/jwave/output/";

	/**
	* The directory where all the commands are located
	* If an executable with the name of the given command does not
	* exist in this directory it will fail
	*/
	static final String commandDir = "/www/jwave/applets98/Receiver/commands/";

	private Socket socket = null;
	PrintWriter os = null;
	String fname;
	BufferedReader in =null;

	public ReceiverThread(Socket socket) {
		super("ReceiverThread");
		this.socket = socket;
	}

	public void run() {

		try {
			//opens a new buffered reader from the socket input stream
			in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
			os = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);


			//String used to read the lines of data
			String inputLine;

			//reads the first line of data This should contain the command
			inputLine = in.readLine();
			StringTokenizer s = new StringTokenizer(inputLine);
			//sets the command to the first token of the first line
			String command = s.nextToken();

			if ( s.hasMoreTokens() ){
				inputLine = s.nextToken();

				//check password
				if ( inputLine.equals("passwd"))
					if (!passwd(s))
						return;

			}

			String time = getTime();

			//change to input file
			String outDir = docRoot +command+time;
			os.println("output/"+command+time);


			//Checks for possible security holes in command
			if ((command.charAt(0) == '/') || (command.charAt(0) == '.') || (command.charAt(0) == '~') || (command.indexOf(";") > 0) ){
				System.out.println("invalid operation name : "+inputLine);
				return;
			}

			//creates the new directory where the files will be stored
			Process p = Runtime.getRuntime().exec("mkdir "+outDir);
			p.waitFor();

			//reads in the e-mail address
			String email = in.readLine();

			//reads the command args
			//these are passed to the shell script as parameters
			String args = in.readLine();

			PrintWriter outStream = null;
			String input = "";

			while (((input = in.readLine()) != null)&&(!input.equals("quit"))) {
				//for debuging purposes
				System.out.println(input);

				//starts a new file
				if (input.equals("***NEW***")){
					//closes old file if new one needed
					if (outStream != null)
						outStream.close();

					input = in.readLine();

					//for debuging purposes
					System.out.println(input);

					File f = new File(outDir+"/"+input);
					outStream = new PrintWriter(new FileOutputStream(f),true);
				} else outStream.println(input);
			}
			System.out.println("Closing outStream");
			outStream.close();
			System.out.println("Closing inStream");
			in.close();


			//os.println("http://www.jwave.vt.edu/output/"+command+time);
			System.out.println("Running "+commandDir+command+" "+args+" in "+outDir);
			//runs the associated script file
			Process n = Runtime.getRuntime().exec(commandDir+command+" "+outDir+" "+args);
			System.out.println("Ran"+commandDir+command+" in "+outDir);
			n.waitFor();

			os.close();
			//closes the socket
			socket.close();

			new ReceiverMailer(command, "/output/"+command+time,email);
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host:.");
		}
		catch (IOException e) {
			System.err.println("Having trouble sending e-mail");
		}
		catch (InterruptedException e){
			System.err.println("Having trouble sending email");
		}


	}

	private boolean passwd(StringTokenizer s) throws IOException{
		String user = s.nextToken();
		String password = s.nextToken();
		String input = " ";

		FileReader f = new FileReader(passwordFile);
		BufferedReader in = new BufferedReader(f);

		while (!input.equals(user)){
			input = in.readLine();
			s = new StringTokenizer(input);
			input = s.nextToken();
		}
		input = s.nextToken();
		if (!input.equals(password))
			return false;

		System.out.println("passwd : "+input);
		return true;
	}


	private String getTime(){
		Calendar calendar = new GregorianCalendar();
		String time = (calendar.get(Calendar.MONTH)+1)+"";
		time += "-"+calendar.get(Calendar.DAY_OF_MONTH);
		time += "-"+calendar.get(Calendar.YEAR);
		time += "-"+calendar.get(Calendar.HOUR_OF_DAY)+":";
		if (calendar.get(Calendar.MINUTE) < 10) time +="0";
		time += calendar.get(Calendar.MINUTE)+":";
		if (calendar.get(Calendar.MINUTE) < 10) time +="0";
		time += calendar.get(Calendar.SECOND);
		time += ":"+calendar.get(Calendar.MILLISECOND);

		return time;
	}

}
