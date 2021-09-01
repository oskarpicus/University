/*
Un client trimite unui server un sir de lungime cel mult 100 de caractere. Serverul va returna clientului acest sir oglindit (caracterele sirului in ordine inversa)
*/

import java.nio.ByteBuffer;
import java.net.*;
import java.util.Scanner;

public class Client{

	private static final int SIZEOFSHORT = 2;

	public static void main(String[] args) throws Exception{

		if(args.length !=2 ){
			System.out.println("Insuficiente argumente");
			return;
		}

		Scanner scanner=new Scanner(System.in);
		InetAddress addr=InetAddress.getByName(args[0]);
		int port=Integer.parseInt(args[1]);
		DatagramSocket socket=new DatagramSocket();

		System.out.println("Dati sir");
		String sir=scanner.nextLine();
		short dim=(short)sir.getBytes().length;
		
		//trimit dimensiunea
		ByteBuffer b1=ByteBuffer.allocate(SIZEOFSHORT);
		b1.putShort(dim);
		DatagramPacket sendDimP=new DatagramPacket(b1.array(),
			SIZEOFSHORT, addr,port);
		socket.send(sendDimP);

		//trimit sirul
		DatagramPacket sendSirP=new DatagramPacket(sir.getBytes(),
			dim,addr,port);
		socket.send(sendSirP);

		//primesc sirul
		byte[] resultArray=new byte[dim];
		DatagramPacket recvP=new DatagramPacket(resultArray,dim);	
		socket.receive(recvP);
		String oglindit=new String(recvP.getData());

		System.out.println(oglindit);
		socket.close();
	}
}
