/*
Un client trimite unui server un sir de lugime cel mult 100 de caractere. Serverul va returna clientului numarul de caractere spatiu din sir.
*/

import java.nio.ByteBuffer;
import java.util.Scanner;
import java.net.*;

public class Client{

	private final static int SIZEOFSHORT = 2;

	public static void main(String[] args) throws Exception{

		Scanner scanner = new Scanner(System.in);
		String adresa=args[0];
		int port=Integer.parseInt(args[1]);

		InetAddress addr=InetAddress.getByName(adresa);
		DatagramSocket socket=new DatagramSocket();

		System.out.println("Dati sir");
		String sir=scanner.nextLine();

		short dim=(short)sir.getBytes().length;
		ByteBuffer bb1=ByteBuffer.allocate(SIZEOFSHORT);
		bb1.putShort(dim);
		DatagramPacket sendDimP=new DatagramPacket(bb1.array(),
			SIZEOFSHORT,addr,port);
		socket.send(sendDimP);

		DatagramPacket sendSirP=new DatagramPacket(sir.getBytes(),
			dim,addr,port);
		socket.send(sendSirP);

		byte[] resultArray=new byte[SIZEOFSHORT];
		DatagramPacket recvP=new DatagramPacket(resultArray,SIZEOFSHORT);			
		socket.receive(recvP);

		short rez=ByteBuffer.wrap(recvP.getData()).getShort();
		System.out.println("Numar spatii: "+rez);
		socket.close();
	}

}
