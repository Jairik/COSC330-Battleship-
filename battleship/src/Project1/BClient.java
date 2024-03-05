package Project1;

/*----------------------------------------------------------------------------------------------------------
  Authors: JJ McCauley & Will Lamuth 
  Creation Date: 3/3/24
  Last Update: 3/4/24
  Description: This class serves as the client object
-------------------------------------------------------------------------------------------------------------*/

//Necessary Libraries
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class BClient {
  private ObjectOutputStream output; // output stream to server
  private ObjectInputStream input; // input stream from server
  private String message = ""; // message from server
  private String serverIP; // host server for this application
  private Socket client; // socket to communicate with server

  /* Constructor to initialize the server IP address */
  BClient(String hostIP) {
    serverIP = hostIP;
  }

  /* Connect to server and process messages from server */
  public void runClient() {
    try {
      connectToServer(); //Create a socket to make a connection
      getStreams(); //Get the input and output streams
      processConnection(); //Process the connection
    }
    catch(EOFException e) {
      System.out.println("Client Terminated Connection");
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    finally {
      closeConnection();
    }
  }

  /* Connects to the server */
  private void connectToServer() throws IOException {
    client = new Socket(InetAddress.getByName(serverIP), 12345);
    System.out.println("Client has successfully connected to: " + client.getInetAddress().getHostAddress());
  }

  /* Get streams to send and receive data */
  private void getStreams() throws IOException {
    //set the output stream for objects
    output = new ObjectOutputStream(client.getOutputStream());
    output.flush(); //flush output buffer to send header information

    //set up input stream for objects
    input = new ObjectInputStream(client.getInputStream());

    System.out.println("Got IO Streams");
  }

  private void closeConnection() 
   {
      System.out.println("Closing Connection");
      try {
         output.close(); // close output stream
         input.close(); // close input stream
         client.close(); // close socket
      } 
      catch (IOException e) {
         e.printStackTrace();
      } 
   } 

   // send message to server
   private void sendData( String message )
   {
    // send object to server
      try  {
         output.writeObject( "CLIENT>>> " + message );
         output.flush(); // flush data to output
      } 
      catch (IOException ioException) {
         System.err.println("Error writing object");
      } 
   } 

   // process connection with server
   private void processConnection() throws IOException {
    // process messages sent from server  
    do { 
      // read message and display it
      try {
        message = ( String ) input.readObject(); // read new message
      } 
      catch ( ClassNotFoundException classNotFoundException ) 
      {
        System.err.println("Unknown object type received");
      } // end catch

      } while ( !message.equals( "SERVER>>> TERMINATE" ) );
   } // end method processConnection
}
