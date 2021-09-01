/*
Un client trimite unui server un numar. Serverul va returna clientului un boolean care sa indice daca numarul respective este prim sau nu.
*/

import java.nio.ByteBuffer;
import java.net.*;
import java.util.Scanner;

public class Client{

	//se da adresa si portul ca si argumente in linia de comanda
	public static void main(String[] args) throws Exception{

		int port = Integer.parseInt(args[1]);
		InetAddress addr = InetAddress.getByName(args[0]);

		DatagramSocket socket = new DatagramSocket();
		
		Scanner scanner=new Scanner(System.in);

		System.out.println("Dati numar");
		short nr=(short)Integer.parseInt(scanner.nextLine());

		ByteBuffer bb=ByteBuffer.allocate(2);
		bb.putShort(nr);
		DatagramPacket sendP=new DatagramPacket(bb.array(),2,addr,port);
		socket.send(sendP);
		
		byte[] result=new byte[2];
		DatagramPacket receiveP=new DatagramPacket(result,2);
		socket.receive(receiveP);

		short rezultat=ByteBuffer.wrap(receiveP.getData()).getShort();
		if(rezultat==1){
			System.out.println("Este prim");
		}
		else{
			System.out.println("Nu este prim");
		}
		socket.close();
	}
}
