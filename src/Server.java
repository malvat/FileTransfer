import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Implements a server which is responsible for sending files
 * @author Anim Malvat
 *
 */
public class Server {
	private Socket socket = null;
	private ServerSocket server = null;
	private DataInputStream in = null;
	
	public Server(int port) {
		try {
			server = new ServerSocket(port);
			socket = server.accept();
			in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));		
			String line = "";
			while(!line.equals("over")) {
				line = in.readUTF();
				System.out.println(line);
			}
			socket.close();
			in.close();
		} catch(IOException io) {
			System.out.println("cannot get input stream: try again later");
		}
		System.out.println("closing connection");
	}
	public static void main(String[] args) {
		Server server = new Server(8080);
	}
}
