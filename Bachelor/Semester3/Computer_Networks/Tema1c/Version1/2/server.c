/*
Un client trimite unui server un numar. Serverul va returna clientului un boolean care sa indice daca numarul respective este prim sau nu.
*/

#include<stdlib.h>
#include<string.h>
#include<stdio.h>
#include<arpa/inet.h>

// se returneaza 1 daca e prim, 0 , altfel
short prim(short nr){
	short ok = 1;
	for(short i=2;i<=nr/2 && ok==1;i++){
		if(nr%i==0){
			ok=0;
		}
	}
	return ok;
}

//se da portul ca si parametru in linia de comanda
int main(int args,char** argv){

	int port = atoi(argv[1]);
	int s,c,l;
	struct sockaddr_in server,client;
	l=sizeof(client);

	if((s=socket(AF_INET,SOCK_DGRAM,0))<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	memset(&server,0,sizeof(server));
	memset(&client,0,sizeof(client));
	server.sin_addr.s_addr=inet_addr("0.0.0.0");
	server.sin_family=AF_INET;
	server.sin_port=htons(port);

	if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la bind\n");
	}

	while(1){
		printf("Astept clienti....\n");
		short numar;
		recvfrom(s,&numar,sizeof(numar),MSG_WAITALL,
			(struct sockaddr*)&client,&l);

		numar=ntohs(numar);
		printf("Am primit %hi\n",numar);
		short rezultat = prim(numar);
		printf("Rezultat = %hi\n",rezultat);
		rezultat=htons(rezultat);
		sendto(s,&rezultat,sizeof(rezultat),0,
			(struct sockaddr*)&client,sizeof(client));
	}

	return 0;
}
