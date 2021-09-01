/*
Un client trimite unui server un nume de calculator sub forma unui sir de caractere de lungime cel mult 100. Serverul va returna clientului un numar reprezentat pe 4 octeti fara semn ce reprezinta adresa IP a calculatorului respectiv. Clientul va afisa corespunzator adresa IP (in format zecimal cu “.”) sau un mesaj de eroare daca numele calculatorului nu poate fi translatat intr-o adresa IP.
*/

import java.net.*;
import java.nio.ByteBuffer;

public class Client{

	public static void main(String[] args) throws Exception{

		DatagramSocket socket=new DatagramSocket();
		InetAddress addr=InetAddress.getByName("localhost");

		String c="a";
		DatagramPacket sendcP=new DatagramPacket(c.getBytes(),
			c.getBytes().length,addr,1234);
		socket.send(sendcP);

		byte[] receiveData=new byte[4];
		DatagramPacket recvP=new DatagramPacket(receiveData,4);
		socket.receive(recvP);

		int rez=ByteBuffer.wrap(recvP.getData()).getInt();
		System.out.println("Rez = "+rez);

		ByteBuffer bb1=ByteBuffer.allocate(4);
		bb1.putInt(rez);
		byte[] result=bb1.array();
		
		InetAddress addr_back=InetAddress.getByAddress(result);
		String[] rezAr=addr_back.toString().split("\\.");
		rezAr[0]=rezAr[0].substring(1);
		for(int i=rezAr.length-1;i>=0;i--){
			if(i>0)
				System.out.print(rezAr[i]+".");
			else
				System.out.print(rezAr[i]);
		}	
		System.out.println();
		socket.close();
	}
}
