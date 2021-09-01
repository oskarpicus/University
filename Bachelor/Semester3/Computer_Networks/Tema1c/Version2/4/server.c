/*
Un client trimite unui server un sir de lungime cel mult 100 de caractere. Serverul va returna clientului acest sir oglindit (caracterele sirului in ordine inversa)
*/

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<arpa/inet.h>
#include<unistd.h>
#define DIM 101

void oglindit(char* sir,uint16_t dim){
	for(int i=0;i<dim/2;i++){
		char aux=sir[i];
		sir[i]=sir[dim-1-i];
		sir[dim-1-i]=aux;
	}
}

int main(int argc,char** argv){

	if(argc != 2){
		printf("Insuficiente argumente\n");
		return 1;
	}

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
	server.sin_family=AF_INET;
	server.sin_port=htons(atoi(argv[1]));

	if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la bind\n");
		return 1;
	}

	while(1){
		printf("Astept clienti...\n");
		uint16_t dim;
		recvfrom(s,&dim,sizeof(dim),MSG_WAITALL,
			(struct sockaddr*)&client,&l);
		dim=htons(dim);
		char sir[DIM];
		recvfrom(s,sir,dim,MSG_WAITALL,(struct sockaddr*)&client,&l);
		sir[dim]='\0';

		oglindit(sir,dim);
		sendto(s,sir,dim,0,(struct sockaddr*)&client,l);
	}

	close(s);
	return 0;
}
