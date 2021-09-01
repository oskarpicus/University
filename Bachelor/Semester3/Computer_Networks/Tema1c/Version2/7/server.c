/*
Un client trimite unui server un sir de lungime cel mult 100 de caractere si doua numere (fie acestea s, i, l). Serverul va returna clientului subsirul de lungime l a lui s care incepe la pozitia i.
*/

#include<stdio.h>
#include<string.h>
#include<unistd.h>
#include<arpa/inet.h>
#include<stdlib.h>
#define DIM 101

int main(int argc,char** argv){

	int s,lung;
	struct sockaddr_in server,client;
	lung=sizeof(client);

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
		uint16_t dim,i,l; char sir[DIM];
		
		recvfrom(s,&dim,sizeof(dim),MSG_WAITALL,
			(struct sockaddr*)&client,&lung);
		dim=ntohs(dim);
		recvfrom(s,sir,dim,MSG_WAITALL,(struct sockaddr*)&client,&lung);
		sir[dim]='\0';
		recvfrom(s,&i,sizeof(i),MSG_WAITALL,
			(struct sockaddr*)&client,&lung);
		i=ntohs(i);	
		recvfrom(s,&l,sizeof(l),MSG_WAITALL,
			(struct sockaddr*)&client,&lung);
		l=ntohs(l);

		printf("%s i=%hu l=%hu\n",sir,i,l);


		uint16_t lungSubsir = (i+l > dim) ? 0 : l;

		printf("Lung subsir = %hu\n",lungSubsir);

		lungSubsir=htons(lungSubsir);
		sendto(s,&lungSubsir,sizeof(lungSubsir),0,
			(struct sockaddr*)&client,lung);

		lungSubsir=ntohs(lungSubsir);
		sendto(s,sir+i,lungSubsir,0,(struct sockaddr*)&client,lung);
	}

	close(s);
	return 0;
}
