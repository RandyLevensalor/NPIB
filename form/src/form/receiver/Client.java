package form.receiver;
//package SMTPClient;
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

/** author Dennis Seay
  * version 1.0
*/

public class Client
{
  static final String DISCONNECT   = "exit";
  Socket              clientSocket = null;
  PrintWriter         os           = null;
  BufferedReader      is           = null;
/** Constructor
  *@param ipAddress this can be DNS name also
  *@param port this is usually 25
*/ 
  public Client(String ipAddress,int port)
  {
    try
    {
      clientSocket = new Socket(ipAddress, port);
      os = new PrintWriter(clientSocket.getOutputStream(),true);
      is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
      connectedTo();
    } catch (UnknownHostException e)
    {
      System.err.println("Don't know about host: " + ipAddress);
    } catch (IOException e)
    {
      System.err.println("Couldn't get I/O for the connection to: " +
                               ipAddress);
    }
  }

/** connectTO
  * This will create a connection to the host specified in the constructor.
  * @return This function returns false if it was unable to connect
*/

  public boolean connectedTo()
  {
    String          returnData = "";
    StringTokenizer code       = null;
    try
    {
      returnData=is.readLine();
    }catch(IOException e)
    {
      return false;
    }

    code = new StringTokenizer(returnData);
    System.out.println(returnData);
    if(code.nextToken().equals("210"))
    {
      return true;
    }
    return false;
  }

/** helo
  * This function attempts to establish communitcation with the host
  * This function must be called upon connection
  * @return this function returns true if communication has been established.
*/
  public boolean helo()
  {
    String          returnData = "";
    StringTokenizer code       = null;
    try
    {
      os.println("HELO builder");
      returnData=is.readLine();
    }catch(IOException e)
    {
      return false;
    }

    code = new StringTokenizer(returnData);
    System.out.println(returnData);
    if(code.nextToken().equals("210"))
    {
      return true;
    }
    return false;
  }

/** sendFrom
  * This should be the return address (your e-mail address)
  * @return this function returns true if you are clear to send.
  * This must be executed along with sendTo before you call startData
*/
  public boolean sendFrom(String from)
  {
    String          returnData = "";
    StringTokenizer code       = null;

    try
    {
      os.println("MAIL FROM:" + from);
      returnData=is.readLine();
    }catch(IOException e)
    {
      return false;
    }

    code = new StringTokenizer(returnData);
    System.out.println(returnData);

    if(code.nextToken().equals("210"))
    {
      return true;
    }
    return false;
  }
/** sendTo
  * This should be the address of the person to receive the mail
  * @return this function returns true if you are clear to send.
  * This must be executed along with sendFrom before you call startData
*/

  public boolean sendTo(String to)
  {
    String          returnData = "";
    StringTokenizer code       = null;

    try
    {
      os.println("RCPT TO:" + to );
      returnData=is.readLine();
    }catch(IOException e)
    {
      return false;
    }

    code = new StringTokenizer(returnData);
    System.out.println(returnData);

    if(code.nextToken().equals("210"))
    {
      return true;
    }
    return false;
  }

/** quit
  * This function terminates communication with the host.
  * @return this function returns true if you quit.
*/

  public boolean quit()
  {
    String          returnData = "";
    StringTokenizer code       = null;

    try
    {
      os.println("Quit");
      returnData=is.readLine();
    }catch(IOException e)
    {
      return false;
    }

    code = new StringTokenizer(returnData);
    System.out.println(returnData);

    if(code.nextToken().equals("221"))
    {
      return true;
    }
    return false;
  }

/** data
  * This is the data/message you are sending. This function can be
  * called after sendFrom, sendTo and startData have been called.
  * This is a direct print eg if you typed "abc 123" abc 123 would be 
  * sent in the email message 
  * @return this function returns true if the data was sent
*/

  public boolean data(String data)
  {
    os.println(data);
    return true;
  }

/** startData
  * This function starts an email transfer
  * sendTo and sendFrom must be called before this function is invoked.
  * @return This function returns true if it is ok to send data.
*/

  public boolean startData()
  {
    String          returnData = "";
    StringTokenizer code       = null;

    try
    {
      os.println("DATA");
      returnData=is.readLine();
    }catch(IOException e)
    {
      return false;
    }

    code = new StringTokenizer(returnData);
    System.out.println(returnData);

    if(code.nextToken().equals("354"))
    {
      return true;
    }
    return false;

  }

/** endData
  * This function ends the data transfer.
  * @return this function returns true if you are no longer sending data.
*/
  public boolean endData()
  {
    String          returnData = "";
    StringTokenizer code       = null;

    try
    {
      os.println(".");
      returnData=is.readLine();
    }catch(IOException e)
    {
      return false;
    }

    code = new StringTokenizer(returnData);
    System.out.println(returnData);

    if(code.nextToken().equals("250"))
    {
      return true;
    }
    return false;

  }

/** cleanup
  * this cleans up the sockes
*/
  public void cleanup()
  {
    try
    {
      os.close();
      is.close();
      clientSocket.close();
    } catch (UnknownHostException e)
    {
      System.err.println("Trying to connect to unknown host: " + e);
    } catch (IOException e)
    {
    }
    System.exit(0);
  }
}

