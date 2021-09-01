#include<stdlib.h>
#include<arpa/inet.h>
#include<sys/types.h>
#include<sys/socket.h>
#include<unistd.h>
#include<stdio.h>

int main(){

	int s;
	struct sockaddr_in adresa_server;

	s=socket(AF_INET,SOCK_STREAM,0);
	
	if(s<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	adresa_server.sin_family=AF_INET;
	adresa_server.sin_port=htons(1234);
	adresa_server.sin_addr.s_addr=inet_addr("10.0.2.15");

	if(connect(s,(struct sockaddr*)&adresa_server,sizeof(adresa_server))<0){
		printf("Eroare la conectare server\n");
		return 1;
	}

	uint16_t nr;
	printf("Dati numar elemente\n");
	scanf("%hu",&nr);

	nr=htons(nr);
	send(s,&nr,sizeof(nr),0);
	nr=ntohs(nr);

	uint16_t* v=malloc(nr*sizeof(uint16_t));
	printf("Dati numere\n");


	for(int i=0;i<nr;i++){
		scanf("%hu",&v[i]);
		v[i]=htons(v[i]);
		send(s,&v[i],sizeof(v[i]),0);
	}

	uint16_t suma;

	recv(s,&suma,sizeof(suma),0);
	suma=ntohs(suma);
	printf("Am primit: %hu\n",suma);
	
	free(v);
	close(s);
	return 0;
}
