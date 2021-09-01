/*Un client trimite unui server doua numere reprezentate pe doi octeti fara semn fiecare. Serverul va returna clientului cmmdc si cmmmc al numerelor primite.
*/

import java.net.*;
import java.util.Scanner;
import java.nio.ByteBuffer;

public class Client{

	private static final int SIZEOFSHORT = 2;
	

	public static void main(String[] args) throws Exception{
		Scanner scanner=new Scanner(System.in);
		String adresa=args[0];
		int port=Integer.parseInt(args[1]);

		InetAddress addr=InetAddress.getByName(adresa);
		DatagramSocket socket=new DatagramSocket();

		System.out.println("Dati cele 2 numere");
		int x=Integer.parseInt(scanner.nextLine());
		int y=Integer.parseInt(scanner.nextLine());

		System.out.println("Am citit "+x+" "+y);

		//trimit datele
		ByteBuffer bb1=ByteBuffer.allocate(SIZEOFSHORT);
		bb1.putShort((short)x);
		DatagramPacket pk1=new DatagramPacket(bb1.array(),
			bb1.array().length,addr,port);
		socket.send(pk1);

		System.out.println("Am trimis x");

		ByteBuffer bb2=ByteBuffer.allocate(SIZEOFSHORT);
                bb2.putShort((short)y);
                DatagramPacket pk2=new DatagramPacket(bb2.array(),
                        bb2.array().length,addr,port);
                socket.send(pk2);

		System.out.println("Am trimis y");
		//primesc datele
		byte[] resArray1=new byte[SIZEOFSHORT];
		DatagramPacket pk3=new DatagramPacket(resArray1,SIZEOFSHORT);			  socket.receive(pk3);
		short cmmdc=ByteBuffer.wrap(pk3.getData()).getShort();

		byte[] resArray2=new byte[SIZEOFSHORT];
                DatagramPacket pk4=new DatagramPacket(resArray2,SIZEOFSHORT);
		socket.receive(pk4);
                short cmmmc=ByteBuffer.wrap(pk4.getData()).getShort();

		System.out.println("Cmmdc = "+Short.toUnsignedInt(cmmdc)+" Cmmmc = "+Short.toUnsignedInt(cmmmc));

		socket.close();
	}

}
