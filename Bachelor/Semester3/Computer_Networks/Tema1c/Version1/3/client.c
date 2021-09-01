/*
Un client trimite unui server un sir de lugime cel mult 100 de caractere. Serverul va returna clientului numarul de caractere spatiu din sir.
*/

#include<arpa/inet.h>
#include<stdio.h>
#include<string.h>
#include<unistd.h>
#include<stdlib.h>
#define DIM 101


int main(){

	int c,l;
	struct sockaddr_in server;
	l=sizeof(server);

	if((c=socket(AF_INET,SOCK_DGRAM,0))<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	memset(&server,0,sizeof(server));
	server.sin_addr.s_addr = inet_addr("127.0.0.1");
	server.sin_family=AF_INET;
	server.sin_port=htons(1234);

	char sir[DIM];
	uint16_t dimensiune,rezultat;

	printf("Dati sirul\n");
	gets(sir);

	dimensiune=strlen(sir);
	dimensiune=htons(dimensiune);

	sendto(c,&dimensiune,sizeof(dimensiune),0,(struct sockaddr*)&server,l);
	sendto(c,sir,dimensiune+1,0,(struct sockaddr*)&server,l);

	recvfrom(c,&rezultat,sizeof(rezultat),MSG_WAITALL,(struct sockaddr*)&server,&l);
	rezultat = ntohs(rezultat);
	printf("Nr. caractere spatiu = %hu\n",rezultat);

	close(c);
	return 0;
}
