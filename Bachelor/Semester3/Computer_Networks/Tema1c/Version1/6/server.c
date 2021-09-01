/*
Un client trimite unui server un sir de lungime cel mult 100 de caractere si un caracter. Serverul va returna clientului toate pozitiile pe care caracterul primit se regaseste in sir.
*/

#include<stdio.h>
#include<arpa/inet.h>
#include<stdlib.h>
#include<unistd.h>
#include<string.h>
#define DIM 101

uint16_t pozitii(char sir[DIM],char c,uint16_t pozC[DIM]){
	uint8_t j=0;
	int dim=strlen(sir);
	for(int i=0;i<dim;i++){
		if(sir[i]==c){
			pozC[j++]=i;
		}
	}
	return j;
}

int main(){

	int s,l;
	struct sockaddr_in server,client;
	l=sizeof(client);

	if((s=socket(AF_INET,SOCK_DGRAM,0))<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	memset(&client,0,sizeof(client));
	memset(&server,0,sizeof(server));
	server.sin_family=AF_INET;
	server.sin_addr.s_addr=inet_addr("0.0.0.0");
	server.sin_port=htons(1234);

	if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la bind\n");
		return 1;
	}

	while(1){
		printf("Astept clienti...\n");
		uint16_t dimensiune;
		recvfrom(s,&dimensiune,sizeof(dimensiune),MSG_WAITALL,
			(struct sockaddr*)&client,&l);
		dimensiune=ntohs(dimensiune);

		printf("Dimensiune sir = %hu\n",dimensiune);

		char sir[DIM];
		recvfrom(s,sir,sizeof(char)*dimensiune,MSG_WAITALL,
			(struct sockaddr*)&client,&l);
		sir[dimensiune]='\0';

		printf("Sir = %s\n",sir);

		char c;
		recvfrom(s,&c,sizeof(char),MSG_WAITALL,(struct sockaddr*)&client,
			&l);

		printf("Caracter = %c\n",c);

		uint16_t pozC[DIM];
		uint16_t dim_sir_rez = pozitii(sir,c,pozC);

		dim_sir_rez=htons(dim_sir_rez);
		sendto(s,&dim_sir_rez,sizeof(dim_sir_rez),0,
			(struct sockaddr*)&client,l);
		
		dim_sir_rez=ntohs(dim_sir_rez);
		for(int i=0;i<dim_sir_rez;i++){
			printf("Trimit poz %hu\n",pozC[i]);
			pozC[i]=htons(pozC[i]);
			sendto(s,&pozC[i],sizeof(pozC[i]),0,
				(struct sockaddr*)&client,l);
		}

	}
	return 0;
}
