/*
Un client trimite unui server un numar. Serverul va returna clientului sirul divizorilor acestui numar.
*/

#include<stdio.h>
#include<stdlib.h>
#include<arpa/inet.h>
#include<unistd.h>
#include<string.h>
#define DIM 100

//se returneaza dimensiunea sirului
uint16_t citire_sir(int s,uint16_t rez[DIM]){

	uint16_t dimensiune;
	recv(s,&dimensiune,sizeof(dimensiune),MSG_WAITALL);
	dimensiune=ntohs(dimensiune);
	
	for(int i=0;i<dimensiune;i++){
		recv(s,&rez[i],sizeof(rez[i]),MSG_WAITALL);
		rez[i]=ntohs(rez[i]);
	}

	return dimensiune;
}

int main(){

	int s;
	struct sockaddr_in server;
	
	if((s=socket(AF_INET,SOCK_STREAM,0))<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	memset(&server,0,sizeof(server));
	server.sin_addr.s_addr=inet_addr("127.0.0.1");
	server.sin_family=AF_INET;
	server.sin_port=htons(8888);

	if(connect(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la connect\n");
		return 1;
	}

	uint16_t numar,rez[DIM];
	printf("Dati numar pentru a obtine sirul de divizori\n");
	scanf("%hu",&numar);
	numar=htons(numar);
	send(s,&numar,sizeof(numar),0);

	numar=ntohs(numar);
	uint16_t dimensiune=citire_sir(s,rez);
	for(int i=0;i<dimensiune;i++){
		printf("%hu ",rez[i]);
	}
	printf("\n");

	return 0;
}
