/*
Un client trimite unui server un sir de lungime cel mult 100 de caractere si doua numere (fie acestea s, i, l). Serverul va returna clientului subsirul de lungime l a lui s care incepe la pozitia i.
*/

import java.net.*;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class Client{


	public static void main(String[] args) throws Exception{
		Scanner scanner = new Scanner(System.in);
		DatagramSocket socket=new DatagramSocket();
		InetAddress addr=InetAddress.getByName("localhost");

		System.out.println("Dati sirul");
		String sir=scanner.nextLine();

		System.out.println("Dati pe i si l");
		short i=Short.parseShort(scanner.nextLine());
		short l=Short.parseShort(scanner.nextLine());
		
		//trimit lungimea sirului
		short dim=(short)sir.getBytes().length;
		ByteBuffer bb1=ByteBuffer.allocate(2);
		bb1.putShort(dim);
		DatagramPacket sendDimP=new DatagramPacket(bb1.array(),
			bb1.array().length,addr,1234);
		socket.send(sendDimP);
		
		//trimit sirul
		DatagramPacket sendSirP=new DatagramPacket(sir.getBytes(),
			dim,addr,1234);
		socket.send(sendSirP);

		//trimit i
		ByteBuffer bb2=ByteBuffer.allocate(2);
		bb2.putShort(i);
		DatagramPacket sendiP=new DatagramPacket(bb2.array(),
			bb2.array().length,addr,1234);
		socket.send(sendiP);

		//trimit l
		ByteBuffer bb3=ByteBuffer.allocate(2);
		bb3.putShort(l);
		DatagramPacket sendlP=new DatagramPacket(bb3.array(),
			bb3.array().length,addr,1234);
		socket.send(sendlP);

		//primesc inapoi pe l
		byte[] receivelarr=new byte[2];
		DatagramPacket recvlP=new DatagramPacket(receivelarr,2);
		socket.receive(recvlP);
		l=ByteBuffer.wrap(recvlP.getData()).getShort();

		if(l==0){
			System.out.println("Nu exista astfel de subsir");
			socket.close();
			return;
		}

		//primesc sirul
		byte[] receivesirarr=new byte[l];
		DatagramPacket recvsirP=new DatagramPacket(receivesirarr,l);
		socket.receive(recvsirP);
		sir=new String(recvsirP.getData());
		System.out.println("Subsirul: "+sir);

		socket.close();
	}
}
