/*
Un client trimite unui server un sir de numere. Serverul va returna clientului suma numerelor primite.
*/

#include<stdio.h>
#include<arpa/inet.h>
#include<unistd.h>
#include<string.h>
#include<stdlib.h>

int nr_procese=0;

void deservire_client(int c){
	uint16_t dimensiune,curent,suma=0;
	recv(c,&dimensiune,sizeof(dimensiune),MSG_WAITALL);
	dimensiune=ntohs(dimensiune);
	for(int i=0;i<dimensiune;i++){
		recv(c,&curent,sizeof(curent),MSG_WAITALL);
		curent=ntohs(curent);
		suma+=curent;
	}
	suma=htons(suma);
	send(c,&suma,sizeof(suma),0);
	close(c);
}

int main(){

	int s,c;
	struct sockaddr_in server,client;

	if((s=socket(AF_INET,SOCK_STREAM,0))<0){
		printf("Eroare la creare socket\n");
		return 1;
	}	

	memset(&server,0,sizeof(server));
	memset(&client,0,sizeof(client));
	server.sin_family=AF_INET;
	server.sin_port=htons(1234);
	server.sin_addr.s_addr=inet_addr("127.0.0.1");

	if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la bind\n");
		return 1;
	}

	listen(s,5);

	while(1){
		printf("Se asteapta clienti...\n");
		int l=sizeof(client);
		c = accept(s,(struct sockaddr*)&client,&l);
		printf("S-a conectat un client\n");

		if(fork()==0){
			deservire_client(c);
			nr_procese++;
			exit(0);	
		}
	}

	return 0;
}
