package form.receiver;
import java.net.*;
import java.io.*;

public class Receiver {
	public static final int defaultPort = 5000;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        boolean listening = true;
			int port = -1;

		if (args.length > 0) 
			port = getInt(args[0]);

		if (port < 0) port = defaultPort;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: "+port);
            System.exit(-1);
        }

        while (listening)
	    new ReceiverThread(serverSocket.accept()).start();

        serverSocket.close();
    }

    public static int getInt(String str){
    	try {
            int ret =  (new Integer(str)).intValue();
            return ret;
            } catch (NumberFormatException e){
             System.out.println(str+" is not an integer");
            }
            return -1;
        }

}
