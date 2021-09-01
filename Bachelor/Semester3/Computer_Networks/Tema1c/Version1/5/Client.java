/*
Un client trimite unui server un numar reprezentat pe un octet fara semn. Serverul va returna clientului sirul divizorilor acestui numar.
*/

import java.net.*;
import java.util.Scanner;
import java.nio.ByteBuffer;

public class Client{


	public static void main(String[] args) throws Exception{

		byte[] input=new byte[1];

		Scanner scanner=new Scanner(System.in);
		System.out.println("Dati numar");
		input[0]=Byte.parseByte(scanner.nextLine());

		InetAddress addr=InetAddress.getByName("localhost");
		DatagramSocket socket=new DatagramSocket();
	
		DatagramPacket sendP=new DatagramPacket(input,1,addr,1234);
		socket.send(sendP);	

		byte[] dimArray=new byte[1];
		DatagramPacket recvLung=new DatagramPacket(dimArray,dimArray.length);
		socket.receive(recvLung);
		byte dimensiune = recvLung.getData()[0];
	
		System.out.println("Numar divizori: "+dimensiune);

		byte[] sirArray=new byte[dimensiune];
		DatagramPacket recvSir=new DatagramPacket(sirArray,sirArray.length);
		socket.receive(recvSir);

		for(byte b : recvSir.getData()){
			System.out.print(b+" ");
		}
		System.out.println("");
		socket.close();
	}
}
