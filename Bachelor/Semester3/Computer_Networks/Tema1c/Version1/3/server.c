/*
Un client trimite unui server un sir de lugime cel mult 100 de caractere. Serverul va returna clientului numarul de caractere spatiu din sir.
*/

#include<arpa/inet.h>
#include<stdio.h>
#include<string.h>
#include<unistd.h>
#include<stdlib.h>
#define DIM 101

uint16_t nrCaractereSpatiu(char* sir){

	uint16_t total =0;
	for(int i=0;i<strlen(sir);i++){
		if(sir[i]==' '){
			total++;
		}
	}
	return total;
}

int main(){

	int s,l;
	struct sockaddr_in server,client;
	l=sizeof(client);

	if((s=socket(AF_INET,SOCK_DGRAM,0))<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	memset(&server,0,sizeof(server));
	memset(&client,0,sizeof(client));
	server.sin_addr.s_addr = inet_addr("0.0.0.0");
	server.sin_family=AF_INET;
	server.sin_port=htons(1234);

	if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la bind\n");
		return 1;
	}

	while(1){
		printf("Astept clienti...\n");
		uint16_t dimensiune;
		char sir[DIM];
		
		recvfrom(s,&dimensiune,sizeof(dimensiune),MSG_WAITALL,(struct sockaddr*)&client,&l);

		dimensiune=ntohs(dimensiune);
		recvfrom(s,sir,dimensiune+1,MSG_WAITALL,(struct sockaddr*)&client,&l);

		uint16_t rezultat = nrCaractereSpatiu(sir);
		rezultat = htons(rezultat);	
		sendto(s,&rezultat,sizeof(rezultat),0,(struct sockaddr*)&client,l);
	}

	close(s);
	return 0;
}
