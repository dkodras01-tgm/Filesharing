package kodras;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Eine Klasse, die einen Client startet und sich mit dem angegebenen Server verbindet.
 * Anschließend empfängt der Client das dekorierte File vom Server.
 * 
 * @author Dominik Kodras
 * @version 22.04.2016
 */
public class FileClient {

	public final static int FILE_SIZE = 6022386;
	// TODO praram File
	public void run(MyCLI cli) throws IOException {
		int SOCKET_PORT = 0;
		String SERVER = "";
		String FILE_TO_RECEIVED = "";
		int bytesRead;
		int current = 0;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		Socket sock = null;
		
		System.out.println("Server: " + cli.getIP() + ", Port: " + cli.getPort());
		SERVER = cli.getIP();
		SOCKET_PORT = Integer.parseInt(cli.getPort());
		FILE_TO_RECEIVED = cli.getPfad(); //
		
		try {
			sock = new Socket(SERVER, SOCKET_PORT);
			System.out.println("Connecting...");

			// File empfangen
			byte[] mybytearray = new byte[FILE_SIZE];
			InputStream is = sock.getInputStream();
			fos = new FileOutputStream(FILE_TO_RECEIVED); // TODO File uebergeben
			bos = new BufferedOutputStream(fos);
			bytesRead = is.read(mybytearray, 0, mybytearray.length);
			current = bytesRead;

			do {
				bytesRead = is.read(mybytearray, current, (mybytearray.length - current));
				if (bytesRead >= 0)
					current += bytesRead;
			} while (bytesRead > -1);

			bos.write(mybytearray, 0, current);
			bos.flush();
			System.out.println("File " + FILE_TO_RECEIVED + " downloaded (" + current + " bytes read)");
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Das angegebene File ist leer!");
			System.exit(1);
		} finally {
			if (fos != null)
				fos.close();
			if (bos != null)
				bos.close();
			if (sock != null)
				sock.close();
		}
	}

}