/*
Un client trimite unui server un sir de lungime cel mult 100 de caractere si un caracter. Serverul va returna clientului toate pozitiile pe care caracterul primit se regaseste in sir.
*/

#include<stdio.h>
#include<arpa/inet.h>
#include<stdlib.h>
#include<unistd.h>
#include<string.h>
#define DIM 101

int main(){

	int s,l;
	struct sockaddr_in server;
	l=sizeof(server);

	if((s=socket(AF_INET,SOCK_DGRAM,0))<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	memset(&server,0,sizeof(server));
	server.sin_family=AF_INET;
	server.sin_addr.s_addr=inet_addr("0.0.0.0");
	server.sin_port=htons(1234);

	printf("Dati sir\n");
	char sir[DIM];
	gets(sir);

	uint16_t dim = strlen(sir);
	dim=htons(dim);
	sendto(s,&dim,sizeof(dim),0,(struct sockaddr*)&server,l);
	
	dim=ntohs(dim);

	sendto(s,sir,sizeof(char)*dim,0,(struct sockaddr*)&server,l);	

	printf("Dati caracter\n");
	char c;
	scanf("%c",&c);

	sendto(s,&c,sizeof(char),0,(struct sockaddr*)&server,l);

	// primim datele inapoi
	uint16_t dim_sir_rez;
	recvfrom(s,&dim_sir_rez,sizeof(dim_sir_rez),MSG_WAITALL,
		(struct sockaddr*)&server,&l);
	dim_sir_rez=ntohs(dim_sir_rez);
	
	printf("Pozitiile: ");
	for(int i=0;i<dim_sir_rez;i++){
		uint16_t x;
		recvfrom(s,&x,sizeof(x),MSG_WAITALL,(struct sockaddr*)&server,
			&l);
		x=ntohs(x);
		printf("%hu ",x);
	}
	printf("\n");
	close(s);
	return 0;
}
