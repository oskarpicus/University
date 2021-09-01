/*
Un client trimite unui server doua numere. Serverul va returna clientului suma celor doua numere.
*/

import java.nio.ByteBuffer;
import java.net.*;
import java.util.Scanner;

public class Client{

	public static void main(String[] args) throws Exception{

		Scanner scanner=new Scanner(System.in);
		String adresa=args[0];
		int port=Integer.parseInt(args[1]);

		InetAddress addr=InetAddress.getByName(adresa);
		DatagramSocket socket=new DatagramSocket();

		System.out.println("Dati cele 2 numere");
		short nr1=Short.parseShort(scanner.nextLine());
		short nr2=Short.parseShort(scanner.nextLine());

		ByteBuffer b1=ByteBuffer.allocate(2);
		b1.putShort(nr1);
		DatagramPacket sendNr1=new DatagramPacket(b1.array(),
			b1.array().length,addr,port);
		socket.send(sendNr1);

		ByteBuffer b2=ByteBuffer.allocate(2);
		b2.putShort(nr2);
		DatagramPacket sendNr2=new DatagramPacket(b2.array(),
			b2.array().length,addr,port);
		socket.send(sendNr2);

		byte[] resultArray=new byte[2];
		DatagramPacket recvP=new DatagramPacket(resultArray,
			resultArray.length);
		socket.receive(recvP);
		short suma=ByteBuffer.wrap(recvP.getData()).getShort();	

		System.out.println("Suma = "+suma);

		socket.close();
	}

}
