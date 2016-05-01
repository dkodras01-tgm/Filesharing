package kodras;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Eine Klasse, die einen Server startet und auf einen Client wartet.
 * Wenn sich ein Client am richtigen Port angemeldet hat, schickt der Server das dekorierte File an den Client.
 * 
 * @author Dominik Kodras
 * @version 22.04.2016
 */
public class FileServer {

	/**
	 * Eine Methode, die den Server startet und auf einen Client wartet.
	 * Anschliessend schickt der Server dem Client das dekorierte File.
	 * 
	 * @param die Kommandozeilenparameter
	 * @param das dekorierte File, dass geschickt werden soll
	 * @throws IOException
	 */
	public void run(MyCLI cli, File file) throws IOException {
		int SOCKET_PORT = 0;
		String FILE_TO_SEND = "";
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		OutputStream os = null;
		ServerSocket servsock = null;
		Socket sock = null;
		
		System.out.println("Server: " + cli.getIP() + ", Port: " + cli.getPort());
		SOCKET_PORT = Integer.parseInt(cli.getPort());
		FILE_TO_SEND = cli.getPfad();
		
		try {
			servsock = new ServerSocket(SOCKET_PORT);
			while (true) {
				System.out.println("Warten auf Client...");
				try {
					sock = servsock.accept();
					System.out.println("Connection angenommen: " + sock);
					
					// File senden
//					File myFile = new File(FILE_TO_SEND);
					File myFile = file;
					byte[] mybytearray = new byte[(int) myFile.length()];
					fis = new FileInputStream(myFile);
					bis = new BufferedInputStream(fis);
					bis.read(mybytearray, 0, mybytearray.length);
					os = sock.getOutputStream();
					System.out.println("Sendet " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
					os.write(mybytearray, 0, mybytearray.length);
					os.flush();
					System.out.println("Fertig.");
//				} catch(NullPointerException e) {
//					System.out.println("File ist leer!");
				} finally {
					if (bis != null)
						bis.close();
					if (os != null)
						os.close();
					if (sock != null)
						sock.close();
				}
			}
		} finally {
			if (servsock != null)
				servsock.close();
		}
	}
}