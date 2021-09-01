/*
Un client trimite unui server doua siruri de numere. Serverul va returna clientului sirul de numere care se regaseste in primul sir dar nu se regasesc in al doilea.
*/

import java.util.Scanner;
import java.io.*;
import java.net.*;

public class Client{

	private static int[] citire_sir_tastatura(Scanner scanner){
		System.out.println("Dati numar elemente");
		int dim = Integer.parseInt(scanner.nextLine());
		int[] sir = new int[dim];
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

	private static int[] citeste_rezultat(DataInputStream in) throws Exception{
		int dimensiune=in.readShort();
		int[] sir = new int[dimensiune];
		for(int i=0;i<dimensiune;i++){
			sir[i]=in.readShort();
		}
		return sir;
	}

	public static void main(String[] args) throws Exception{

		Socket s = new Socket("127.0.0.1",8888);

		Scanner scanner = new Scanner(System.in);
		System.out.println("Pentru primul sir:");
		int[] sir1=citire_sir_tastatura(scanner);

		System.out.println("Pentru al doilea sir:");
		int[] sir2=citire_sir_tastatura(scanner);

		DataInputStream in=new DataInputStream(s.getInputStream());
		DataOutputStream out=new DataOutputStream(s.getOutputStream());

		trimite_sir(out,sir1);
		trimite_sir(out,sir2);

		int[] rez=citeste_rezultat(in);
		for(int element : rez){
			System.out.print(element+" ");
		}
		System.out.println("");
		s.close();
	}

} 
