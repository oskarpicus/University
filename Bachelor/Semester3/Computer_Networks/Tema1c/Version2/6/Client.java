/*
Un client trimite unui server un sir de lungime cel mult 100 de caractere si un caracter. Serverul va returna clientului toate pozitiile pe care caracterul primit se regaseste in sir.
*/

import java.nio.ByteBuffer;
import java.net.*;
import java.util.Scanner;

public class Client{

	private static final int SIZEOFSHORT = 2;

	public static void main(String[] args) throws Exception{
		Scanner scanner=new Scanner(System.in);
		int port=Integer.parseInt(args[1]);
		String adresa=args[0];

		InetAddress addr=InetAddress.getByName(adresa);
		DatagramSocket socket=new DatagramSocket();

		System.out.println("Dati sir");
		String sir=scanner.nextLine();
		short dim=(short)sir.getBytes().length;

		System.out.println("Dati caracter");
		String caracter=scanner.nextLine();

		//trimit dimensiunea sirului
		ByteBuffer bb1=ByteBuffer.allocate(SIZEOFSHORT);
		bb1.putShort(dim);
		DatagramPacket sendDimP=new DatagramPacket(bb1.array(),
			bb1.array().length,addr,port);
		socket.send(sendDimP);

		//trimit sirul
		DatagramPacket sendSirP=new DatagramPacket(sir.getBytes(),
			dim,addr,port);
		socket.send(sendSirP);

		//trimit caracterul
		DatagramPacket sendCharP=new DatagramPacket(caracter.getBytes(),
			1,addr,port);
		socket.send(sendCharP);

		//primesc numarul de pozitii
		byte[] resultDimArray=new byte[SIZEOFSHORT];
		DatagramPacket recvDimP=new DatagramPacket(resultDimArray,
			SIZEOFSHORT);		
		socket.receive(recvDimP);
		//short contor=ByteBuffer.wrap(recvDimP.getData()).getShort();
		short contor=ByteBuffer.wrap(resultDimArray).getShort();
		
		System.out.println("Caracterul "+caracter+" apare in "+sir+
			" pe pozitiile:"); 

		for(int i=0;i<contor;i++){
			byte[] resultPozArray=new byte[SIZEOFSHORT];
			DatagramPacket recvPozP=new DatagramPacket(
				resultPozArray,SIZEOFSHORT);
			socket.receive(recvPozP);
			short poz=ByteBuffer.wrap(recvPozP.getData()).getShort();
			System.out.print(poz+" ");
		}
		System.out.println();
		socket.close();
	}
}




