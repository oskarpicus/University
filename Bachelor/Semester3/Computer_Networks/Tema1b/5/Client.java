/*
Un client trimite unui server un numar. Serverul va returna clientului sirul divizorilor acestui numar.
*/

import java.util.Scanner;
import java.io.*;
import java.net.*;

public class Client{

	public static void main(String[] args) throws Exception{

		Socket s= new Socket("127.0.0.1",8888);

		Scanner scanner=new Scanner(System.in);
		System.out.println("Dati numar pentru a obtine sirul div");
		int numar=Integer.parseInt(scanner.nextLine());

		DataInputStream in=new DataInputStream(s.getInputStream());
		DataOutputStream out=new DataOutputStream(s.getOutputStream());

		out.writeShort(numar);

		int lungime=in.readShort();
		for(int i=0;i<lungime;i++){
			int curent=in.readShort();
			System.out.print(curent+" ");
		}
		System.out.println();
		s.close();
	}
}

