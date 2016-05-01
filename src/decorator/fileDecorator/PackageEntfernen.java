package decorator.fileDecorator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Scanner;

import decorator.MyFile;

/**
 * Eine Klasse, die einen Dekorierer darstellt.
 * In diesem Fall wird das Package aus dem File entfernt.
 * 
 * @author Dominik Kodras
 * @version 22.04.2016
 */
public class PackageEntfernen extends Decorator {
	MyFile file;
	
	public PackageEntfernen(MyFile file) {
		this.file = file;
	}
	
	@Override
	public String getFileType() {
		return file.getFileType() + ", Package entfernen";
	}

	@Override
	public File decorate() {
		String fileContent = "";
		File f = null;
		
		try {
			f = file.decorate();
//			Scanner scanner = new Scanner(f);
//			fileContent = scanner.useDelimiter("END").next();
//			scanner.close();
			readFile(f.getPath());
			
		} catch (FileNotFoundException e1) {
			System.out.println("ACHTUNG: File wurde nicht gefunden!");
			//e1.printStackTrace();
		} catch (NoSuchElementException e1) {
			System.out.println("ACHTUNG: File wurde nicht gefunden!");
			//e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fileContent = fileContent.replaceAll("\\s*(package){1}\\s+[\\w.]+;{1}", "");
		
		File fi = new File("file");
		try (FileOutputStream fop = new FileOutputStream(fi)) {
			byte[] contentInBytes = fileContent.getBytes();
			fop.write(contentInBytes);
			fop.flush();
			fop.close();
		} catch (IOException e) {
			System.out.println("Fehler beim Schreiben ins File!");
			//e.printStackTrace();
		}
		return fi;
	}
	
	public static String readFile(String fileName) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(fileName));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}
}