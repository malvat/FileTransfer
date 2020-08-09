import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Implements a server which is responsible for sending files
 * @author Anim Malvat
 *
 */
public class Server {
	private Socket socket = null;
	private ServerSocket server = null;
	private DataInputStream in = null;
	private DataOutputStream out = null;
	public enum CMD {PUT, GET, QUIT, ERROR};
	public Server(int port) {
		try {
			server = new ServerSocket(port);
			System.out.println("listening at port 8080");
			socket = server.accept();
			System.out.println("connection accepted");
			in = new DataInputStream(socket.getInputStream());	
			out = new DataOutputStream(socket.getOutputStream());
			String line = "";
			System.out.println("getting input from socket");
			putFile("server_files\\file.txt", out);
			getFile(in);
//			while(!line.equals("over")) {
//				line = in.readUTF();
//				System.out.println(line);
//			}
			socket.close();
			in.close();
		} catch(IOException io) {
			System.out.println("cannot get input stream: try again later");
		}
		System.out.println("closing connection");
	}
	
	public CMD readCommand(String command) {
		if(command.equals("quit")) {
			// return quit the process
			return CMD.QUIT;
		} else {
			String[] splits = command.split(" ");
			if(splits[0].equals("put")) {
				// put file
				return CMD.PUT;
			} else if(splits[0].equals("get")) {
				// get file
				return CMD.GET;
			} else {
				// some error
				return CMD.ERROR;
			}
		}
	}
	
	// user enters get command
	public void putFile(String filename, DataOutputStream out) {
		System.out.println("putting file");
		File file; 
		file = new File(filename);
		if(file.exists() && !file.isDirectory()) {
			System.out.println("file found");
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));				
				String line;
				while( (line = reader.readLine()) != null) {
					out.writeUTF(line);
				}
				out.writeUTF("end");
				reader.close();
 			} catch(IOException io) {	
				System.out.println("cannnot read the file");
			}
		} 
	}
	
	public void getFile(DataInputStream in) {
		try {
			String line;
			File file = new File("server_files\\downloaded.txt");
			BufferedWriter fileOutput = new BufferedWriter(new FileWriter(file));
			while(true) { 
				line = in.readUTF();
				if(line.equals("end")) {
					break;
				}
				fileOutput.write(line + "\n");
			}			
			fileOutput.close();
		} catch(IOException io) {
			System.out.println("input error");
		}
	}
	
	public static void main(String[] args) {
		Server server = new Server(8080);
	}
}
