package lecture2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		String host = "localhost";
		int port = 4244; 
		
		Socket socket = new Socket(host, port);
		

		Scanner scan = new Scanner(System.in);
		
		InputStream inpStream = socket.getInputStream();
		OutputStream outStream = socket.getOutputStream();
		
		BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(outStream));
		BufferedReader rd = new BufferedReader(new InputStreamReader(inpStream));
		
		while (true) {
			System.out.print("> ");
			String line = scan.nextLine();
			wr.write(line);
			wr.newLine();
			wr.flush();

			String output = null;
			while ((output = rd.readLine()) != null) {
				System.out.println(output);
				if (!rd.ready())
					break;
			}

			if(line.equals("QUIT"))
				break;
		}
		
		System.out.println("Client terminating.");
		

		inpStream.close();
		outStream.close();
		scan.close();
		socket.close();
	}

}
