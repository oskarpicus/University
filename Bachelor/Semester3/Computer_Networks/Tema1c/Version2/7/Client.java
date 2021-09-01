/*
Un client trimite unui server un sir de lungime cel mult 100 de caractere si doua numere (fie acestea s, i, l). Serverul va returna clientului subsirul de lungime l a lui s care incepe la pozitia i.
*/

import java.util.Scanner;
import java.nio.ByteBuffer;
import java.net.*;

public class Client{

	private final static int SIZEOFSHORT = 2;

	private static void sendShort(DatagramSocket socket,InetAddress addr,int port, short nr) throws Exception{

		ByteBuffer bb=ByteBuffer.allocate(SIZEOFSHORT);
		bb.putShort(nr);
		DatagramPacket sendP=new DatagramPacket(bb.array(),
			SIZEOFSHORT,addr,port);
		socket.send(sendP);

	}
	public static void main(String[] args) throws Exception{
		Scanner scanner=new Scanner(System.in);
		String adresa=args[0];
		int port=Integer.parseInt(args[1]);

		InetAddress addr=InetAddress.getByName(adresa);
		DatagramSocket socket=new DatagramSocket();

		System.out.println("Dati sirul, i si l");
		String sir=scanner.nextLine();
		short dim=(short)sir.getBytes().length;

		short i=Short.parseShort(scanner.nextLine());
		short l=Short.parseShort(scanner.nextLine());

		sendShort(socket,addr,port,dim);
		
		DatagramPacket sendSirP=new DatagramPacket(sir.getBytes(),
			dim,addr,port);
		socket.send(sendSirP);

		sendShort(socket,addr,port,i);
		sendShort(socket,addr,port,l);

		//primesc lungimea subsirului
		byte[] resultDimArray=new byte[SIZEOFSHORT];
		DatagramPacket recvDimP=new DatagramPacket(
			resultDimArray,SIZEOFSHORT);		
		socket.receive(recvDimP);
		short lungSubsir=ByteBuffer.wrap(recvDimP.getData()).getShort();

		System.out.println("Lung = "+lungSubsir);

		if(lungSubsir==0){
			System.out.println("Nu exista subsir");
			socket.close(); return;			
		}

		byte[] resultSirArray=new byte[lungSubsir];
		DatagramPacket recvP=new DatagramPacket(
			resultSirArray,lungSubsir);
		socket.receive(recvP);
		String subsir=new String(recvP.getData());

		System.out.println(subsir);

		socket.close();
	}

}
