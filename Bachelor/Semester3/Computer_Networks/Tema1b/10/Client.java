/*
Un client trimite unui server doua siruri de caractere. Serverul ii raspunde clientului cu caracterul care se regaseste de cele mai multe ori pe pozitii identice in cele doua siruri si cu numarul de aparitii ale acestui caracter.
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


	public static void main(String[] args) throws Exception{

		Socket s = new Socket("127.0.0.1",8888);

		Scanner scanner = new Scanner(System.in);
		System.out.println("Dati primul sir");
		String sir1 = scanner.nextLine();
		System.out.println("Dati al doilea sir");
                String sir2 = scanner.nextLine();

		DataOutputStream out=new DataOutputStream(s.getOutputStream());
		DataInputStream in=new DataInputStream(s.getInputStream());	

		trimite_sir(out,sir1);
		trimite_sir(out,sir2);
		out.flush();

		//primesc datele inapoi
		byte cmax=in.readByte();
		int frecvmax=in.readShort();
		System.out.println("Caracterul "+(char)cmax+" cu frecv "+frecvmax);

		s.close();

	}

}
