/*
Un client trimite unui server un numar. Serverul va returna clientului un boolean care sa indice daca numarul respective este prim sau nu.
*/

#include<unistd.h>
#include<string.h>
#include<stdio.h>
#include<stdlib.h>
#include<arpa/inet.h>

// se da adresa si portul din linia de comanda
int main(int args,char** argv){

	int s,l;
	struct sockaddr_in server;
	l=sizeof(server);

	if((s=socket(AF_INET,SOCK_DGRAM,0))<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	memset(&server,0,sizeof(server));
	server.sin_family=AF_INET;
	server.sin_port=htons(atoi(argv[2]));
	server.sin_addr.s_addr=inet_addr(argv[1]);

	short numar;
	printf("Dati numar\n");
	scanf("%hi",&numar);

	printf("Am citit %hi\n",numar);
	numar=htons(numar);
	sendto(s,&numar,sizeof(numar),0,(struct sockaddr*)&server,l);

	short rezultat;	
	recvfrom(s,&rezultat,sizeof(rezultat),MSG_WAITALL,
		(struct sockaddr*)&server,&l);

	rezultat=ntohs(rezultat);
	printf("Rezultat = %hi\n",rezultat);
	if(rezultat==0){
		printf("Nu este prim\n");
	}
	else{
		printf("Este prim\n");
	}

	close(s);
	return 0;
}
