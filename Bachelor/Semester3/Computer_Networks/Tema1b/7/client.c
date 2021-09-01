/*
Un client trimite unui server un sir de caractere si doua numere (fie acestea s, i, l). Serverul va returna clientului subsirul de lungime l a lui s care incepe la pozitia i.
*/

#include<stdio.h>
#include<arpa/inet.h>
#include<string.h>
#include<unistd.h>
#include<stdlib.h>
#define DIM 101


void citire_rezultat(int c,char sir[DIM]){
	uint16_t dimensiune;
	recv(c,&dimensiune,sizeof(dimensiune),MSG_WAITALL);
	dimensiune=ntohs(dimensiune);

	if(dimensiune==0){
		recv(c,&sir[0],sizeof(sir[0]),MSG_WAITALL);
		return;
	}

	for(int i=0;i<dimensiune;i++){
		recv(c,&sir[i],sizeof(sir[i]),MSG_WAITALL);
	}

	sir[dimensiune]='\0';

}

void trimite_sir(int c,char sir[DIM]){
	uint16_t lungime=strlen(sir);
	lungime=htons(lungime);
	send(c,&lungime,sizeof(lungime),0);

	for(int i=0;i<strlen(sir)+1;i++){
		send(c,&sir[i],sizeof(sir[i]),0);
	}
}

void trimite_numar(int c,uint16_t numar){
	numar=htons(numar);
	send(c,&numar,sizeof(numar),0);
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
		printf("Eroare la conectare\n");
		return 1;
	}

	char sir[DIM];
	uint16_t i,l;
	printf("Dati sirul\n");
	scanf("%s",sir);
	printf("Dati i si l\n");
	scanf("%hu%hu",&i,&l);

	trimite_sir(s,sir);
	trimite_numar(s,i);
	trimite_numar(s,l);

	citire_rezultat(s,sir);
	printf("%s\n",sir);

	close(s);
	return 0;
}
