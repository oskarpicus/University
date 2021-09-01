/*
Un client trimite unui server un sir de lungime cel mult 100 de caractere si doua numere (fie acestea s, i, l). Serverul va returna clientului subsirul de lungime l a lui s care incepe la pozitia i.
*/

#include<arpa/inet.h>
#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<string.h>
#define DIM 101

int main(){

	int s,lung;
	struct sockaddr_in server,client;
	lung=sizeof(client);

	if((s=socket(AF_INET,SOCK_DGRAM,0))<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	memset(&server,0,sizeof(server));
	memset(&client,0,sizeof(client));
	server.sin_family=AF_INET;
	server.sin_addr.s_addr=inet_addr("0.0.0.0");
	server.sin_port=htons(1234);

	if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la bind\n");
		return 1;
	}

	while(1){
		printf("Astept clienti...\n");
		uint16_t dim;
		recvfrom(s,&dim,sizeof(dim),MSG_WAITALL,(struct sockaddr*)&client
			,&lung);
		dim=ntohs(dim);
		
		char sir[DIM];
		recvfrom(s,sir,dim,MSG_WAITALL,(struct sockaddr*)&client,&lung);
		sir[dim]='\0';

		uint16_t i,l;
		recvfrom(s,&i,sizeof(i),MSG_WAITALL,
			(struct sockaddr*)&client,&lung);
		recvfrom(s,&l,sizeof(l),MSG_WAITALL,
			(struct sockaddr*)&client,&lung);
		i=ntohs(i);
		l=ntohs(l);

		if(dim<i+l){ // nu exista un astfel de subsir
			l=0;
		}
	
		l=htons(l);	
		sendto(s,&l,sizeof(l),0,(struct sockaddr*)&client,lung);

		if(l==0) 
			continue;

		l=ntohs(l);
		sendto(s,sir+i,l,0,(struct sockaddr*)&client,lung);

	}

	return 0;
}
