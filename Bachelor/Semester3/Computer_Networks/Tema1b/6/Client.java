/*
Un client trimite unui server un sir de caractere si un caracter. Serverul va returna clientului toate pozitiile pe care caracterul primit se regaseste in sir.
*/

import java.util.Scanner;
import java.io.*;
import java.net.*;

public class Client{

	private static void trimite_sir(DataOutputStream out,String sir) throws Exception{

		out.writeShort(sir.getBytes().length);
		for(byte b : sir.getBytes()){
			out.writeByte(b);
		}

		out.writeByte(0); //terminatorul
	}

	private static int[] citire_rezultat(DataInputStream in) throws Exception{
		int lungime=in.readShort();
		int[] rez= new int[lungime];
		for(int i=0;i<lungime;i++){
			rez[i]=in.readShort();
		}
		return rez;
	}

	public static void main(String[] args) throws Exception{

		Socket s = new Socket("127.0.0.1",8889);

		Scanner scanner = new Scanner(System.in);
		System.out.println("Dati sirul");
		String sir = scanner.nextLine();
		System.out.println("Dati caracterul");
		byte caracter = scanner.nextLine().getBytes()[0];
	
		DataInputStream in=new DataInputStream(s.getInputStream());
		DataOutputStream out=new DataOutputStream(s.getOutputStream());

		trimite_sir(out,sir);
		out.writeByte(caracter); //trimit caracterul
		out.flush();

		//primesc rezultatul
		int[] rezultat = citire_rezultat(in);
		for(int element : rezultat){
			System.out.print(element+" ");
		}
		System.out.println("");

		s.close();
	}

}
