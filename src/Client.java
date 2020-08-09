import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	private Socket socket = null;
	private Scanner input = null;
	private DataOutputStream out = null;
	private DataInputStream in = null;
	
	public Client(String address, int port) {
		try {
			socket = new Socket(address, port);
			input = new Scanner(System.in);
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
		} catch(UnknownHostException u) {
			System.out.println("unknown host exception");
		} catch(IOException io) {
			System.out.println(io + "io exception");
		}
		String line;
		try {
			while(true) {
				System.out.println("please enter command: ");
				line = input.nextLine();
				out.writeUTF(line);
				Util.CMD cmd = Util.readCommand(line);
				String[] splits = line.split(" ");
				if(cmd == Util.CMD.GET) {
					// get a file from server
					line = in.readUTF();
					System.out.println(line);
					if(line.equals("file not found")) {
						continue;
					}
					getFile(in, splits[1]);
					line = in.readUTF();
					System.out.println(line);
				} else if(cmd == Util.CMD.PUT) {
					// send a file to server
					putFile("client_files\\" + splits[1], out);
				} else if(cmd == Util.CMD.QUIT) {
					// quit
					in.close();
					out.close();
					socket.close();
					System.out.println("closing connection");
					System.out.println("bye bye");
					return;
				} 
			}			
		} catch(IOException io) {
			System.out.println("input or ouput error after entering command");
			return;
		}
	}
	
	public void getFile(DataInputStream in, String filename) {
		try {
			String line;
			File file = new File("client_files\\" + filename);
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
	
	public void putFile(String filename, DataOutputStream out) {
		File file; 
		file = new File(filename);
		if(file.exists() && !file.isDirectory()) {
			try {
				out.writeUTF(filename + " found");
				System.out.println("uploading file");
				BufferedReader reader = new BufferedReader(new FileReader(file));				
				String line;
				while( (line = reader.readLine()) != null) {
					out.writeUTF(line);
				}
				out.writeUTF("end");
				out.writeUTF("download complete");
				System.out.println("upload complete");
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
			System.out.println(filename + " not found");
		}
	}
	
	public static void main(String[] args) {
		Client client = new Client("127.0.0.1", 8080);
	}
}
