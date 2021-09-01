/*
Un client trimite unui server un sir de lungime cel mult 100 de caractere si un caracter. Serverul va returna clientului toate pozitiile pe care caracterul primit se regaseste in sir.
*/

import java.util.Scanner;
import java.net.*;
import java.nio.ByteBuffer;

public class Client{


	public static void main(String[] args) throws Exception{

		Scanner scanner=new Scanner(System.in);
		DatagramSocket socket=new DatagramSocket();
		InetAddress addr=InetAddress.getByName("localhost");

		System.out.println("Dati sir");
		String sir=scanner.nextLine();

		System.out.println("Dati caracter");
		String caracter=scanner.nextLine();

		short dim=(short)sir.getBytes().length;
		ByteBuffer bb1=ByteBuffer.allocate(2);
		bb1.putShort(dim);
		DatagramPacket sendLungPack = new DatagramPacket(
			bb1.array(),2,addr,1234);
		socket.send(sendLungPack);

		DatagramPacket sendSirPack=new DatagramPacket(
			sir.getBytes(),dim,addr,1234);
		socket.send(sendSirPack);

		DatagramPacket sendCharPack=new DatagramPacket(
			caracter.getBytes(),1,addr,1234);
		socket.send(sendCharPack);

		//obtinem dimensiunea sirului rezultat
		byte[] recv_dim_sir_rez=new byte[2];
		DatagramPacket recvDimPack=new DatagramPacket(
			recv_dim_sir_rez,recv_dim_sir_rez.length);
		socket.receive(recvDimPack);
		short dim_sir_rez=ByteBuffer.wrap(recvDimPack.getData()).
			getShort();
	
		System.out.print("Numar pozitii"+dim_sir_rez+"\nPozitii: ");

		for(int i=0;i<dim_sir_rez;i++){
			byte[] array_poz=new byte[2];
			DatagramPacket recvPozPack=new DatagramPacket(
				array_poz,array_poz.length);
			socket.receive(recvPozPack);
			short poz=ByteBuffer.wrap(recvPozPack.getData()).
			getShort();
			System.out.print(poz+" ");
		}
		System.out.println("");
		socket.close();
	}

}
