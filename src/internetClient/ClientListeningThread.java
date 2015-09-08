package internetClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

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
