/*
Un client trimite unui server doua siruri de caractere ordonate. Serverul va interclasa cele doua siruri si va returna clientului sirul rezultat interclasat.
*/

#include<stdio.h>
#include<arpa/inet.h>
#include<unistd.h>
#include<string.h>
#include<stdlib.h>
#define DIM 101

void citire_sir(int c,char sir[DIM*2]){
	uint16_t lungime;
	recv(c,&lungime,sizeof(lungime),MSG_WAITALL);
	lungime=ntohs(lungime);
	printf("Dimensiune = %hu\n",lungime);

	for(int i=0;i<lungime+1;i++){
		recv(c,&sir[i],sizeof(sir[i]),MSG_WAITALL);
	}
}

void trimite_sir(int c,char sir[DIM]){
	uint16_t lungime=strlen(sir);
	lungime=htons(lungime);
	send(c,&lungime,sizeof(lungime),0);
	for(int i=0;i<strlen(sir)+1;i++){
		send(c,&sir[i],sizeof(sir[i]),0);
	}
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
		printf("Eroare la bind\n");
		return 1;
	}

	char sir1[DIM],sir2[DIM],sir_rez[DIM*2];
	printf("Dati primul sir\n");
	scanf("%s",sir1);
	printf("Dati al doilea sir\n");
	scanf("%s",sir2);
	
	trimite_sir(s,sir1);
	trimite_sir(s,sir2);

	citire_sir(s,sir_rez);
	printf("%s\n",sir_rez);

	return 0;
}
