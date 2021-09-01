/*
Un client trimite unui server un sir de lungime cel mult 100 de caractere si un caracter. Serverul va returna clientului toate pozitiile pe care caracterul primit se regaseste in sir.
*/

#include<stdio.h>
#include<unistd.h>
#include<string.h>
#include<arpa/inet.h>
#include<stdlib.h>
#define DIM 101

//se returneaza dimensiunea sirului
uint16_t pozitiiCaracter(char* sir,uint16_t dim,char c,uint16_t pozitii[DIM]){
	uint16_t contor=0;
	for(int i=0;i<dim;i++){
		if(sir[i]==c)
			pozitii[contor++]=i;
	}
	return contor;
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
	server.sin_addr.s_addr=inet_addr("0.0.0.0");
	server.sin_port=htons(atoi(argv[1]));
	server.sin_family=AF_INET;

	if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la bind\n");
		return 1;
	}

	while(1){
		printf("Astept clienti..\n");
		uint16_t dim; char c; char sir[DIM]; uint16_t pozitii[DIM];
		
		recvfrom(s,&dim,sizeof(dim),MSG_WAITALL,(struct sockaddr*)&client,&l);
		dim=ntohs(dim);
		recvfrom(s,sir,dim,MSG_WAITALL,(struct sockaddr*)&client,&l);
		recvfrom(s,&c,sizeof(char),MSG_WAITALL,(struct sockaddr*)&client,&l);
		sir[dim]='\0';
		
		uint16_t contor=pozitiiCaracter(sir,dim,c,pozitii);

		contor=htons(contor);
		sendto(s,&contor,sizeof(contor),0,(struct sockaddr*)&client,l);
		contor=ntohs(contor);

		for(int i=0;i<contor;i++){
			pozitii[i]=htons(pozitii[i]);
			sendto(s,&pozitii[i],sizeof(pozitii[i]),0,
				(struct sockaddr*)&client,l);
		}
	}
	return 0;
}
