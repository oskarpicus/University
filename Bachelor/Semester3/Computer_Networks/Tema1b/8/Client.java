/*
Un client trimite unui server doua siruri de numere. Serverul va returna clientului sirul de numere comune celor doua siruri primite.
*/

import java.util.Scanner;
import java.io.*;
import java.net.*;

public class Client{

	private static int[] citire_sir_tastatura(Scanner scanner){
		System.out.println("Dati dimensiunea sirului");
		int dim=Integer.parseInt(scanner.nextLine());
		int[] sir=new int[dim];
		System.out.println("Dati cele "+dim+" numere");
		for(int i=0;i<dim;i++){
			sir[i]=Integer.parseInt(scanner.nextLine());
		}
		return sir;
	}

	private static void trimite_sir(DataOutputStream out,int[] sir) throws Exception{
		out.writeShort(sir.length);
		for(int element : sir){
			out.writeShort(element);
		}
	}

	private static void citeste_rezultat(DataInputStream in) throws Exception{
		int dim=in.readShort();
		for(int i=0;i<dim;i++){
			int curent=in.readShort();
			System.out.print(curent+" ");
		}
		System.out.println("");
}

	public static void main(String[] args) throws Exception{

		Socket s = new Socket("127.0.0.1",8888);

		Scanner scanner = new Scanner(System.in);
		System.out.println("Pentru 1:");
		int[] sir1=citire_sir_tastatura(scanner);
		System.out.println("Pentru 2:");
                int[] sir2=citire_sir_tastatura(scanner);

		DataInputStream in=new DataInputStream(s.getInputStream());
		DataOutputStream out=new DataOutputStream(s.getOutputStream());

		trimite_sir(out,sir1);
		trimite_sir(out,sir2);
		out.flush();

		citeste_rezultat(in);

		s.close();
	}

}
