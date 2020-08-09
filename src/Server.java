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
	private int numberOfClients = 0;
	
	public Server(int port) {
		try {
			server = new ServerSocket(port);
			System.out.println("listening for clients at port 8080");
			// actively listen for connections
			while(true) {
				socket = server.accept();		
				// create a child process
				numberOfClients++;
				ChildProcess child = new ChildProcess(socket, numberOfClients);
				child.start();
			}
		} catch(IOException io) {
			System.out.println("cannot get input stream: try again later");
		}
		System.out.println("closing connection");
	}
	
	public class ChildProcess extends Thread {
		private Socket socket;
		private int id;
		DataInputStream in;
		DataOutputStream out;
		
		public ChildProcess(Socket socket, int id) {
			this.socket = socket;
			this.id = id;
			try {
				this.in = new DataInputStream(socket.getInputStream());
				this.out = new DataOutputStream(socket.getOutputStream());
			} catch(IOException io) {
				System.out.println("could not open input or output stream");
			}
		}
		
		// user enters get command
		public void putFile(String filename, DataOutputStream out) {
			System.out.println("putting file " + filename);
			File file; 
			file = new File(filename);
			if(file.exists() && !file.isDirectory()) {
				System.out.println("file found");
				try {
					out.writeUTF(filename + " found");
					BufferedReader reader = new BufferedReader(new FileReader(file));				
					String line;
					while( (line = reader.readLine()) != null) {
						out.writeUTF(line);
					}
					out.writeUTF("end");
					out.writeUTF("download complete");
					reader.close();
	 			} catch(IOException io) {	
					System.out.println("cannnot read the file");
				}
			} else {
				try {					
					out.writeUTF("file not found");
				} catch(IOException io) {
					System.out.println("output error");
				}
				System.out.println("file not found");
			}
		}
		
		// when user puts a file
		public void getFile(DataInputStream in, String filename) {
			try {
				String line;
				File file = new File("server_files\\" + filename);
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
		
		public void run() {
			System.out.println("client:#"+ this.id + " connected");
			// if input or output stream could not open
			if(this.in == null || this.out == null) {
				return;
			}
			String line;
			while(true) {
				try {
					line = in.readUTF();	
					Util.CMD cmd = Util.readCommand(line);
					String splits[] = line.split(" ");
					if(cmd == Util.CMD.GET) {
						putFile("server_files\\" + splits[1], this.out);
					} else if(cmd == Util.CMD.PUT) {
						line = in.readUTF();
						System.out.println(line);
						getFile(in, splits[1]);
						line = in.readUTF();
						System.out.println(line);
					} else if(cmd == Util.CMD.QUIT) {
						in.close();
						out.close();
						socket.close();
						System.out.println("client:#" + id +" disconnected");
						return;
					} else if(cmd == Util.CMD.ERROR) {
						System.out.println("please enter get or put <file-name> or quit");
					}
				} catch(IOException io) {
					System.out.println(io + "could not read from the client");
					return;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Server server = new Server(8080);
	}
}
