/*
Un client trimite unui server un sir de numere. Serverul va returna clientului suma numerelor primite.

*/

import java.io.*;
import java.net.*;
import java.util.Vector;

public class Client{

	private static BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));

	public static int[] citesteVector() throws Exception{
		System.out.println("Dati cate numere insumati");
		int dimensiune = Integer.parseInt(reader.readLine());
		int[] v = new int[dimensiune];
		System.out.println("Dati numerele");
		for(int i=0;i<dimensiune;i++){
			v[i]=Integer.parseInt(reader.readLine());
		}
		return v;
	}

	public static void main(String[] args)throws Exception {

		Socket c=new Socket("127.0.0.1",1234);
		int[] v = citesteVector();

		DataInputStream socketIn=new DataInputStream(c.getInputStream());
		DataOutputStream socketOut=new DataOutputStream(c.getOutputStream());

		socketOut.writeShort(v.length);
		for(int e : v)
			socketOut.writeShort(e);
		socketOut.flush();

		int suma = socketIn.readUnsignedShort();
		System.out.println("Suma = "+suma);

		reader.close();
		c.close();
	}

}
