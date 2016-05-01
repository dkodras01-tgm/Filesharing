package kodras;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import decorator.MyFile;
import decorator.core.JavaFile;
import decorator.fileDecorator.AuthorEntfernen;
import decorator.fileDecorator.EinrueckungenKorregieren;
import decorator.fileDecorator.PackageEntfernen;

/**
 * Eine Klasse, die das gesamte Programm startet.
 * Es wird mit Hilfe der CLI-Klasse erkannt ob Server oder Client gestartet werden sollen
 * und wie das File dekoriert werden soll.
 * 
 * @author Dominik Kodras
 * @version 22.04.2016
 */
public class StartFileDecorator {
	private static File readyToSendFile;
	
	public static void main(String[] args) {
		MyCLI cli = new MyCLI(args);
		FileServer fs;
		FileClient fc;
		
		MyFile myFile = new JavaFile(cli.getPfad());
		
		// dekorieren je nach Auswahl der Optionen
		if(cli.getSC()=='s') {
			if(cli.getEK())
				myFile = new EinrueckungenKorregieren(myFile);
			if(cli.getAE())
				myFile = new AuthorEntfernen(myFile);
			if(cli.getPE())
				myFile = new PackageEntfernen(myFile);
		}
		
		System.out.println(myFile.getFileType());
		readyToSendFile = myFile.decorate();
		//fileToConsole();
		
		// Server oder Client wird gestartet
		try {
			if(cli.getSC()=='s') {
				fs = new FileServer();
				fs.run(cli, readyToSendFile);
			} else {
				if(cli.getSC()=='c') {
					fc = new FileClient();
					fc.run(cli);
				} else {
					System.out.println("Bitte geben Sie für die Option -sc entweder s oder c ein!");
					System.exit(1);
				}
			}
		} catch (IOException e) {
			System.out.println("Fehler beim Starten des Dienstes!");
			//e.printStackTrace();
		}
	}
	
	/**
	 * Eine Methode, die den Fileinhalt auf der Konsole ausgibt.
	 */
	public static void fileToConsole() {
		// File in String einlesen
		String fileContent = "";
		File f = null;
		try {
			f = readyToSendFile;
			fileContent = new Scanner(f).useDelimiter("END").next();
			
		} catch (FileNotFoundException e1) {
			System.out.println("ACHTUNG: File wurde nicht gefunden!");
			e1.printStackTrace();
		}
		System.out.println(fileContent);
	}
}