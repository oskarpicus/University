/*
Un client trimite unui server un sir de lungime cel mult 100 de caractere. Serverul va returna clientului acest sir oglindit (caracterele sirului in ordine inversa)
*/

import java.util.Scanner;
import java.net.*;
import java.nio.ByteBuffer;

public class Client{

	public static void main(String[] args) throws Exception{

		Scanner scanner=new Scanner(System.in);
		InetAddress addr=InetAddress.getByName("localhost");
		DatagramSocket socket=new DatagramSocket();

		System.out.println("Dati sir");
		String sir=scanner.nextLine();

		short lung=(short)sir.getBytes().length;
		
		ByteBuffer bb=ByteBuffer.allocate(2);
		bb.putShort(lung);
		DatagramPacket packLung=new DatagramPacket(bb.array(),2,
			addr,1234);
		socket.send(packLung);

		System.out.println("Am trimis dimensiune");

		DatagramPacket packSir=new DatagramPacket(sir.getBytes(),
			lung,addr,1234);
		socket.send(packSir);

		byte[] receiveData=new byte[lung];
		DatagramPacket receivePack=new DatagramPacket(receiveData,
			receiveData.length);
		socket.receive(receivePack);

		String rez=new String(receivePack.getData());

		System.out.println("Oglindit : "+rez);

		socket.close();
	}
}
