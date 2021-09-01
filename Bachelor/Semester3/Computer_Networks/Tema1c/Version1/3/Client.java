/*
Un client trimite unui server un sir de lugime cel mult 100 de caractere. Serverul va returna clientului numarul de caractere spatiu din sir.
*/

import java.util.Scanner;
import java.nio.ByteBuffer;
import java.net.*;

public class Client{


	public static void main(String[] args) throws Exception{

		Scanner scanner=new Scanner(System.in);
		InetAddress addr=InetAddress.getByName("localhost");
		DatagramSocket socket=new DatagramSocket();

		System.out.println("Dati sirul");
		String sir=scanner.nextLine();
		short lungime=(short)sir.getBytes().length;

		ByteBuffer bb1=ByteBuffer.allocate(2);
		bb1.putShort(lungime);
		
		DatagramPacket packLung=new DatagramPacket(bb1.array(),
			2,addr,1234);
		socket.send(packLung);

		sir=sir.concat("0");
		DatagramPacket packSir=new DatagramPacket(sir.getBytes(),
			lungime+1,addr,1234);
		socket.send(packSir);
		

		byte[] receiveData=new byte[2];
		DatagramPacket packRecv=new DatagramPacket(receiveData,
			receiveData.length);		
		socket.receive(packRecv);

		short result=ByteBuffer.wrap(packRecv.getData()).getShort();
		System.out.println("Nr. caractere spatiu= "+result);
		socket.close();
	}
}
