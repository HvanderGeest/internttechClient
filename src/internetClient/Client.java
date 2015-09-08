package internetClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	private static Socket socket;
	public static void main(String[] args) throws UnknownHostException, IOException {
		boolean validUserName = false;
		Scanner usernamescanner = new Scanner(System.in);
		String userName = "";
		while(!validUserName){
			System.out.println("voer een gebruikersnaam in");
			userName = usernamescanner.nextLine();
			if(!userName.isEmpty()){
				validUserName = true;
			} else {
				System.out.println("ongeldige gebruikersnaam");
			}
			
		}
		socket = new Socket("localhost", 9004);
		
		ClientListeningThread thread = new ClientListeningThread(socket);
		thread.start();
		
		OutputStream outputStream = socket.getOutputStream();
		PrintWriter writer = new PrintWriter(outputStream);
		writer.println(userName);
		writer.flush();
		System.out.println("om naar een persoon te wisperen type w:/<gebruikersnaam v/d persoon> <text>");
		while(true){
			OutputStream outputStrm = socket.getOutputStream();
			String text = usernamescanner.nextLine();
			writer = new PrintWriter(outputStrm);
			writer.println( text );
			writer.flush();
			
		}


	}

}
