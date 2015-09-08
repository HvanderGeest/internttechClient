package internetClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	private static Socket socket;
	private static final String HOST="localhost" ;
	private static final int GATE_NUMBER=9004;

	public static void main(String[] args) throws UnknownHostException, IOException {
		boolean validUserName = false;
		Scanner usernamescanner = new Scanner(System.in);
		String userName = "";
		
		Client client= new Client();
		
		//Invoeren gebruikersnaam
		while (!validUserName) {
			System.out.println("voer een gebruikersnaam in");
			userName = usernamescanner.nextLine();
			if (!userName.isEmpty()) {
				validUserName = true;
			} else {
				System.out.println("ongeldige gebruikersnaam");
			}

		}
		
		socket = new Socket(HOST, GATE_NUMBER);

		
		ClientListeningThread thread = client.new ClientListeningThread(socket);
		thread.start();

		//zending the username
		OutputStream outputStream = socket.getOutputStream();
		PrintWriter writer = new PrintWriter(outputStream);
		writer.println(userName);
		writer.flush();
		System.out.println("om naar een persoon te wisperen type w:/<gebruikersnaam v/d persoon> <text>");
		
		//berichten inlezen en versturen
		while (true) {
			OutputStream outputStrm = socket.getOutputStream();
			String text = usernamescanner.nextLine();
			writer = new PrintWriter(outputStrm);
			writer.println(text);
			writer.flush();

		}

	}
	
	/**
	 * Thread die de ontvangen berichte afhandelt
	 */
	public class ClientListeningThread extends Thread {
		
		private Socket socket;
		public ClientListeningThread(Socket socket){
			this.socket = socket;
		}
		
		public void run() {
			while (true) {
				try {
					InputStream inputStream = socket.getInputStream();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(inputStream));
					System.out.println(reader.readLine());

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		}

	}

}
