package form.receiver;
import java.io.*;

public class ReceiverMailer {

	/**
	* the name of the local host, used when connecting to
	* the mail server
	*/
	static final String localHost = "localhost";

	/**
	* the port of the smtp Should always be 25
	*/
	static final int mailPort = 25;

	/**
	* this is the directory containing the mail
	* tempelates.
	*/
	static final String mailLoc = "/www/jwave/applets98/Receiver/commands/";

	/**
	* this is the suffix for the mail tempelate files
	*/
	static final String suffix = ".m";

	/**
	* The variable in the mail template file
	* which is replaced with the directory containing 
	* the generated data
	*/
	static final String replaceMe = "$1";

	public ReceiverMailer(String command, String dir, String email){
		FileReader f;
		try {
		f = new FileReader(mailLoc+command+suffix);
		BufferedReader r = new BufferedReader(f);
		String dataIn = "";
		String dataOut = "";
		while ((dataIn = r.readLine())!=null){
	 	   dataOut+=dataIn+"\n";
		}	
		System.out.println("Directory: "+dir);
		dataOut = parse(dataOut,dir);
		System.out.println("Parsed: "+dataOut);
		mail(email,dataOut);
		System.out.println("Should have been sent");
		} catch (FileNotFoundException e){
            		e.printStackTrace();
        	}
		catch (IOException e){
            		e.printStackTrace();
        	}
	}

	private String parse(String data, String dir){
		int curr =0;
		int last =0;
		String newData = "";

		curr = data.indexOf(replaceMe);
		while (curr != -1){
		   newData += data.substring(last, curr);
		   newData+=dir;
		   last = curr+replaceMe.length();
		   curr = data.indexOf(replaceMe,last);
		}
		return newData+data.substring(last);
	}

    public void mail(String email, String msg){
      Client c = new Client(localHost,mailPort);
        if(c.helo())
          System.out.println("OK - HELO");
        if(c.sendFrom("rlevensa@spelunker.sv.vt.edu"))
          System.out.println("OK - sendFrom");
        if(c.sendTo(email))
          System.out.println("OK - sendTo");
        if(c.startData())
          System.out.println("OK - startData");
        if(c.data(msg))
              System.out.println("OK - data 1");
        if(c.endData())
          System.out.println("OK - endData");
        if(c.quit())
          System.out.println("OK - quit");
    }

}	
