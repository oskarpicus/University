/*
Un client trimite unui server un numar. Serverul va returna clientului un boolean care sa indice daca numarul respective este prim sau nu.
*/

import java.nio.ByteBuffer;
import java.net.*;
import java.util.Scanner;

public class Client{

	private static final int SIZEOFSHORT = 2;
	private static final int SIZEOFBYTE = 1;

	public static void main(String[] args) throws Exception{

		Scanner scanner=new Scanner(System.in);
		String adresa=args[0];
		int port=Integer.parseInt(args[1]);

		DatagramSocket socket=new DatagramSocket();
		InetAddress addr=InetAddress.getByName(adresa);

		System.out.println("Dati numar");
		short nr=Short.parseShort(scanner.nextLine());
		
		ByteBuffer b1=ByteBuffer.allocate(SIZEOFSHORT);
		b1.putShort(nr);
		DatagramPacket sendP=new DatagramPacket(b1.array(),SIZEOFSHORT,
			addr,port);
		socket.send(sendP);
	

		byte[] resultArray=new byte[SIZEOFBYTE];
		DatagramPacket recvP=new DatagramPacket(resultArray,SIZEOFBYTE);
		socket.receive(recvP);

		byte rez=ByteBuffer.wrap(recvP.getData()).get();
		if(rez==0){
			System.out.println("Nu e prim");
		}
		else{
			System.out.println("E prim");
		}
		socket.close();
	}
}
