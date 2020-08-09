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
		String line = "";
		getFile(in);
		putFile("client_files\\file.txt", out);
//		while(!line.equals("over")) {
//			try {
//				line = input.nextLine();
//				System.out.println("you said: " + line);
//				out.writeUTF(line);
//			} catch(IOException io) {
//				System.out.println("could not read line");
//			}
//		}
		try {
			input.close();
			out.close();
			socket.close();
		} catch(IOException io) {
			System.out.println("input error");
		}
	}
	
	public void getFile(DataInputStream in) {
		try {
			String line;
			File file = new File("client_files\\downloaded.txt");
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
	
	public static void main(String[] args) {
		Client client = new Client("127.0.0.1", 8080);
	}
}
