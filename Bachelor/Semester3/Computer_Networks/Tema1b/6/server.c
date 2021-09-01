/*
Un client trimite unui server un sir de caractere si un caracter. Serverul va returna clientului toate pozitiile pe care caracterul primit se regaseste in sir.
*/

#include<stdio.h>
#include<arpa/inet.h>
#include<stdlib.h>
#include<unistd.h>
#include<string.h>
#define DIM 100

void citire_sir(int c,char sir[DIM]){
	uint16_t dimensiune;
	recv(c,&dimensiune,sizeof(dimensiune),MSG_WAITALL);
	dimensiune=ntohs(dimensiune);
	printf("Dimensiune = %hu\n",dimensiune);

	for(int i=0;i<dimensiune+1;i++){ // +1 pt ca citim si terminatorul
		recv(c,&sir[i],sizeof(sir[i]),MSG_WAITALL);
	}

	printf("Sirul = %s\n",sir);
}

//se returneaza dimensiunea sirului
uint16_t procesare(char sir[DIM],char caracter,uint16_t rezultat[DIM]){
	uint16_t lungime=0;
	for(int i=0;i<strlen(sir);i++){
		if(sir[i]==caracter)
			rezultat[lungime++]=i;
	}
	return lungime;
}

void trimite_sir(int c,uint16_t sir[DIM],uint16_t lungime){
	lungime=htons(lungime);
	send(c,&lungime,sizeof(lungime),0);
	lungime=ntohs(lungime);

	for(int i=0;i<lungime;i++){
		sir[i]=htons(sir[i]);
		send(c,&sir[i],sizeof(sir[i]),0);
	}
}

void deservire_client(int c){
	char sir[DIM];
	citire_sir(c,sir);

	char caracter;
	recv(c,&caracter,sizeof(caracter),MSG_WAITALL);

	uint16_t rezultat[DIM];
	uint16_t lungime=procesare(sir,caracter,rezultat);

	trimite_sir(c,rezultat,lungime);	

	close(c);
}

int main(){

	int s,c,l;
	struct sockaddr_in server,client;

	if((s=socket(AF_INET,SOCK_STREAM,0))<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	memset(&server,0,sizeof(server));
	memset(&client,0,sizeof(client));
	server.sin_addr.s_addr=inet_addr("127.0.0.1");
	server.sin_family=AF_INET;
	server.sin_port=htons(8889);

	if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la bind\n");
		return 1;
	}

	listen(s,5);
	l=sizeof(client);
	
	while(1){
		printf("Astept clienti....\n");
		c=accept(s,(struct sockaddr*)&client,&l);
		printf("S-a conectat un client\n");
		if(fork()==0){
			deservire_client(c);
			exit(0);
		}
	}
	close(s);
	return 0;
}
