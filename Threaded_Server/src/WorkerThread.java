import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.Runnable; 
import java.net.Socket;

public class WorkerThread implements Runnable
{
				private Socket connection;	
				private String path;
 public WorkerThread(Socket connection, String Path)
 {
	            this.connection = connection; 			// Global object directed to private object in this class
	            path = Path;
 }

public void runfromhere()
  {
	  try
		{
		InputStreamReader inputstreamreader = new InputStreamReader(connection.getInputStream()); // reads the Input from connected client
		BufferedReader bufferreader = new BufferedReader(inputstreamreader); // Buffers the input data
		DataOutputStream dataoutputstream = new DataOutputStream(connection.getOutputStream()); // writes data to the connected client
		String fileName = bufferreader.readLine();                                              //attribute to store the File name
		System.out.println("Requested a file named " + "\"" + fileName + "\"");
//		String publicRoot = "C:\\Server\\";  // Trying to give source folder from command Arguments
		String publicRoot = path;
		File requestedFile = new File(publicRoot+"\\"+fileName);
		
		if((requestedFile.exists()) == true)							// Existence of requested file is checked
		{
			System.out.println("Requested File Exists");
			dataoutputstream.writeInt(1);								// Indicating the client that requested file is available
			byte[] arrayoffile = new byte[(int)requestedFile.length()]; // allocating bytes according to the size of file
			FileInputStream fileinputstream = new FileInputStream(requestedFile); // reading bytes from a file <String Provided>
			BufferedInputStream buffer = new BufferedInputStream(fileinputstream); // buffer to hold the read input
		    buffer.read(arrayoffile,0,arrayoffile.length); 		// writes into the allocated location from the buffer
		    //outp.writeUTF(Integer.toString(arrayoffile.length));
		    dataoutputstream.writeLong(arrayoffile.length);     // Sends to the client about the size of the file
		    System.out.println("File Size sent to Client");    
		    OutputStream outputStream = connection.getOutputStream();   // accepts the input and sends to the client waiting on the socket
		    System.out.println("Sending " + fileName + " of size (" + arrayoffile.length + "bytes )");
		    {
		    outputStream.write(arrayoffile,0,arrayoffile.length);  // Writes to the Client on the requested file
		    outputStream.flush(); // Any information left in buffer should be written to the output
			}
		    System.out.println("Done....!");
		 }
		else
		{
			System.out.println(requestedFile + " Not Available");
			//dataoutputstream.writeUTF("Sorry" + requestedFile + "File is not Available With the Server");
			dataoutputstream.writeInt(0); // Indicating Client that Requested File is not Available
		}
	}
	  catch(Exception e)
	  {
		  e.printStackTrace();
	  }
  }
  @Override
  public void run()      // reaches here when a start is initiated
  {
  	this.runfromhere(); // direct to working part of the server 
  }
}
