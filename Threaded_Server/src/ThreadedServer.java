import java.net.ServerSocket;
import java.net.Socket;

public class ThreadedServer
{
	final static int PortAddress = 5555;
	public static void main(String[] args)
	{
		try
		{

			ServerSocket socketServer = new ServerSocket(PortAddress); // port Number to listen for the clients
			System.out.println("Listening on Port  " + PortAddress);
			while(true)
			{
				Socket connection = socketServer.accept(); // waits here until connection is obtained and creates object when a client gets connected
				System.out.println("Client connected from: " + connection.getLocalAddress());
				WorkerThread newThread = new WorkerThread(connection, args[0]); // creates an object for the thread of class
				Thread thread = new Thread(newThread, "Client");       // new thread created with arguments of "runnable object" and "name"
				thread.start(); 									   // initiates the running of created thread
			}
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}

	}

}
