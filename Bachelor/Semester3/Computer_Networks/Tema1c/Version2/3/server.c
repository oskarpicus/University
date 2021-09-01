/*
Un client trimite unui server un sir de lugime cel mult 100 de caractere. Serverul va returna clientului numarul de caractere spatiu din sir.
*/

#include<stdio.h>
#include<arpa/inet.h>
#include<stdlib.h>
#include<string.h>
#include<unistd.h>
#define DIM 101

uint16_t nrSpatii(char* sir,uint16_t dim){
	uint16_t rez=0;
	for(int i=0;i<dim;i++)
		rez= (sir[i]==' ') ? rez+1 : rez;
	return rez;
}

int main(int argc,char** argv){

	int s,l;
	struct sockaddr_in server,client;
	l=sizeof(client);

	if((s=socket(AF_INET,SOCK_DGRAM,0))<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	memset(&server,0,sizeof(server));
	memset(&client,0,sizeof(client));
	server.sin_port=htons(atoi(argv[1]));
	server.sin_family=AF_INET;
	server.sin_addr.s_addr=inet_addr("0.0.0.0");

	if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la bind\n");
		return 1;
	}

	while(1){
		printf("Astept clienti..\n");
		char sir[DIM];
		uint16_t dim;

		recvfrom(s,&dim,sizeof(dim),MSG_WAITALL,(struct sockaddr*)&client,&l);
		dim=ntohs(dim);
		recvfrom(s,sir,sizeof(char)*dim,MSG_WAITALL,
			(struct sockaddr*)&client,&l);
		sir[dim]='\0';

		char ip[INET_ADDRSTRLEN];
		struct sockaddr_in* adresa=&client;
		struct in_addr adresa_ip=adresa->sin_addr;
		inet_ntop(AF_INET,&adresa_ip,ip,INET_ADDRSTRLEN);
		printf("S-a conectat %s\n",ip);

		uint16_t rez = nrSpatii(sir,dim);
		rez=htons(rez);
		sendto(s,&rez,sizeof(rez),0,(struct sockaddr*)&client,l);
	}

	return 0;
}
