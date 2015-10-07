package lecture2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
	static volatile boolean stopServer = false;
	public static String directory;
	private static Map<String, Recipe> xmlRecipes = new HashMap<>();

	public static void main(String[] args) throws Exception {
		ServerSocket srvSocket = new ServerSocket(4244);
		
		try { 
			directory = args[0];
		}
		catch (Exception e) {
			directory = "./";
		}

		loadRecipes();
		
		while (!stopServer) {
			System.out.println("Waiting for a client");
			Socket client = srvSocket.accept();
			(new ServerThread(client)).start();
		}

		System.out.println("Server socket closing.");
		srvSocket.close();
	}



	private static class ServerThread extends Thread {

		private Socket client;

		public ServerThread(Socket client) {
			this.client = client;
		}

		public void run() {
			try {
				System.out.println("Client connected.");
				InputStream is = client.getInputStream();
				OutputStream os = client.getOutputStream();

				BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(
						os));
				BufferedReader rd = new BufferedReader(
						new InputStreamReader(is));

				while (true) {
					String[] args = rd.readLine().split(" ");

					switch (args[0]) {
					case "SEARCH":
						System.out.println("- search");
						if(args.length > 1)
							wr.write(searchingWords(args));
						else
							wr.write("Missing argument!\n");
						wr.flush();
						break;

					case "GET":
						System.out.println("- get");
						wr.write(getXmlFile(args));
						wr.flush();
						break;

					case "QUIT":
						System.out.println("- get");
						client.close();
						break;

					default:
						System.out.println("- unknown command " + args[0]);
						wr.write("Fail. Command '" + args[0] + "' not found.");
						wr.newLine();
						wr.flush();
					}

				}
				

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
				
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static void loadRecipes() throws IOException, Exception {
		File dir = new File(directory);
		File[] xmlFiles = null;
		DOMRecipeReaderWriter dom = new DOMRecipeReaderWriter();

		// http://stackoverflow.com/questions/16143494/search-directory-for-any-xml-file
		if (dir.isDirectory()) {
			xmlFiles = dir.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File folder, String name) {
					return name.toLowerCase().endsWith(".xml");
				}
			});
		}
		Recipe rcp;
		for (File f : xmlFiles) {
			rcp = dom.loadRecipe(new FileInputStream(f));
			xmlRecipes.put(rcp.getName(), rcp);
		}

	}

	private static String getXmlFile(String[] words) throws Exception {
		DOMRecipeReaderWriter dom = new DOMRecipeReaderWriter();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		for (String name : xmlRecipes.keySet()) {
			if(containsAll(words, name)){
				dom.storeRecipe(out, xmlRecipes.get(name));
				String xml = new String(out.toByteArray(),"UTF-8");
				out.close();
				return xml;
			}
		}
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <notfound />\n";
	}

	private static String searchingWords(String[] words) {
		StringBuilder result = new StringBuilder();
		
		for (String name : xmlRecipes.keySet()) {
			if(containsAll(words, name)){
				result.append(name);
				result.append("\n");
			}
		}
		if(result.length() == 0){
			return "Nothing!\n";
		}

		return result.toString();
	}
	
	private static boolean containsAll(String[] words, String name){
		boolean occurrence = true;
		for (int i = 1; i < words.length; i++) {
			if (!name.toLowerCase().contains(words[i].toLowerCase()))
				occurrence = false;
		}
		
		if (occurrence)
			return true;
		
		return false;
	}

}
