/*
Un client trimite unui server un numar reprezentat pe un octet fara semn. Serverul va returna clientului sirul divizorilor acestui numar.
*/

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Scanner;

public class Client{

	private static final int SIZEOFBYTE = 1;
	private static final int SIZEOFSHORT = 2;

	public static void main(String[] args) throws Exception{

		Scanner scanner=new Scanner(System.in);
		String adresa=args[0];
		int port=Integer.parseInt(args[1]);

		InetAddress addr=InetAddress.getByName(adresa);
		DatagramSocket socket=new DatagramSocket();

		System.out.println("Dati numar");
		short nr=Short.parseShort(scanner.nextLine());

		ByteBuffer bb1=ByteBuffer.allocate(SIZEOFBYTE);
		bb1.put((byte)nr);
		DatagramPacket sendNrP=new DatagramPacket(bb1.array(),
			SIZEOFBYTE,addr,port);
		socket.send(sendNrP);

		System.out.println("Am trimis numarul");

		// primesc dimensiunea sirului
		byte[] resultDimArray=new byte[SIZEOFSHORT];
		DatagramPacket recvDimP=new DatagramPacket(resultDimArray,
			SIZEOFSHORT);
		socket.receive(recvDimP);

		System.out.println("aici");

		short dim=ByteBuffer.wrap(recvDimP.getData()).getShort();

		System.out.println("Nr. divizori: "+dim+"\nDivizorii");

		for(int i=0;i<dim;i++){
			byte[] resultArray=new byte[SIZEOFSHORT];
			DatagramPacket recvP=new DatagramPacket(resultArray,
				SIZEOFSHORT);
			socket.receive(recvP);
			
			short div=ByteBuffer.wrap(recvP.getData()).getShort();
			System.out.print(div+" ");
		}
		System.out.println();

		socket.close();
	}
}
