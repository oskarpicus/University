/*
Un client trimite unui server doua numere. Serverul va returna clientului suma celor doua numere.
*/
import java.nio.ByteBuffer;
import java.util.Scanner;
import java.io.*;
import java.net.*;

public class Client{

	public static void main(String[] args) throws Exception {

		short nr1,nr2;
		Scanner scanner = new Scanner(System.in);

		InetAddress addr = InetAddress.getByName("localhost");

		DatagramSocket socket = new DatagramSocket();

		System.out.println("Dati primul numar");
		nr1=(short)Integer.parseInt(scanner.nextLine());
		System.out.println("Dati al doilea numar");
		nr2=(short)Integer.parseInt(scanner.nextLine());

		ByteBuffer bb1 = ByteBuffer.allocate(2);
		bb1.putShort((short)nr1);
		System.out.println("S-a convertit primul");
		DatagramPacket packetNr1 = new DatagramPacket(bb1.array(),2,addr,1234);
		socket.send(packetNr1);

		ByteBuffer bb2 = ByteBuffer.allocate(2);
                bb2.putShort((short)nr2);
                DatagramPacket packetNr2 = new DatagramPacket(bb2.array(),2,addr,1234);
                socket.send(packetNr2);
		

		byte[] receiveData = new byte[2];
		DatagramPacket receivePacket = new DatagramPacket(receiveData,2);
	
		socket.receive(receivePacket);
		short suma = ByteBuffer.wrap(receivePacket.getData()).getShort();	
		System.out.println("Suma = "+suma);

		socket.close();
	}

}
