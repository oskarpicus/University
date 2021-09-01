/*
Un client trimite unui server un sir de lungime cel mult 100 de caractere si doua numere (fie acestea s, i, l). Serverul va returna clientului subsirul de lungime l a lui s care incepe la pozitia i.
*/

#include<unistd.h>
#include<stdio.h>
#include<arpa/inet.h>
#include<stdlib.h>
#include<string.h>
#define DIM 101

int main(){

	int s,lung;
	struct sockaddr_in server;
	lung=sizeof(server);

	if((s=socket(AF_INET,SOCK_DGRAM,0))<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	memset(&server,0,sizeof(server));
	server.sin_family=AF_INET;
	server.sin_port=htons(1234);
	server.sin_addr.s_addr=inet_addr("127.0.0.1");

	printf("Dati sir\n");
	char sir[DIM];
	scanf("%s",sir);
	uint16_t dim = strlen(sir);
	
	printf("Dati i si l\n");
	uint16_t i,l;
	scanf("%hu%hu",&i,&l);

	dim=htons(dim);
	sendto(s,&dim,sizeof(dim),0,(struct sockaddr*)&server,sizeof(server));
	dim=ntohs(dim);
	sendto(s,sir,dim,0,(struct sockaddr*)&server,sizeof(server));

	i=htons(i); l=htons(l);
	sendto(s,&i,sizeof(i),0,(struct sockaddr*)&server,sizeof(server));
	sendto(s,&l,sizeof(l),0,(struct sockaddr*)&server,sizeof(server));

	// primesc inapoi datele

	recvfrom(s,&l,sizeof(l),MSG_WAITALL,(struct sockaddr*)&server,&lung);
	l=ntohs(l);

	if(l==0){
		printf("Nu exista astfel de subsir\n");
		goto exit;
	}

	recvfrom(s,sir,l,MSG_WAITALL,(struct sockaddr*)&server,&lung);
	sir[l]='\0';
	
	printf("Subsirul : %s\n",sir);

	exit:
		close(s);
		return 0;
}
