/*
Un client trimite unui server un numar. Serverul va returna clientului sirul divizorilor acestui numar.
*/

#include<stdio.h>
#include<stdlib.h>
#include<arpa/inet.h>
#include<unistd.h>
#include<string.h>
#define DIM 100

//se returneaza dimensiunea
uint16_t sir_divizori(uint16_t numar,uint16_t rez[DIM]){
	uint16_t contor=0;
	for(int i=1;i<=numar/2;i++){
		if(numar%i==0){
			rez[contor++]=i;
		}
	}
	rez[contor++]=numar;
	printf("Numar divizori = %hu\n",contor);
	return contor;
}

void deservire_client(int c){
	uint16_t numar,rez[DIM];
	recv(c,&numar,sizeof(numar),MSG_WAITALL);
	numar=ntohs(numar);	

	printf("Am primit %hu\n",numar);

	uint16_t dimensiune=sir_divizori(numar,rez);
	dimensiune=htons(dimensiune);	
	send(c,&dimensiune,sizeof(dimensiune),0);

	dimensiune=ntohs(dimensiune);

	for(int i=0;i<dimensiune;i++){
		rez[i]=htons(rez[i]);
		send(c,&rez[i],sizeof(rez[i]),0);
	}

	close(c);
}

int main(){

	int s,c,l;
	struct sockaddr_in server,client;
	
	if((s=socket(AF_INET,SOCK_STREAM,0))<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	memset(&server,0,sizeof(server));
	memset(&client,0,sizeof(client));
	server.sin_addr.s_addr=inet_addr("127.0.0.1");
	server.sin_family=AF_INET;
	server.sin_port=htons(8888);

	if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la bind\n");
		return 1;
	}

	listen(s,5);
	l=sizeof(client);

	while(1){
		printf("Astept clienti....\n");
		c=accept(s,(struct sockaddr*)&client,&l);
		printf("S-a conectat un client\n");
		if(fork()==0){
			deservire_client(c);
			exit(0);
		}
	}

	return 0;
}
