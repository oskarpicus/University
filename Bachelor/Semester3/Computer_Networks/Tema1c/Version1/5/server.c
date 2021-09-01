/*
Un client trimite unui server un numar reprezentat pe un octet fara semn. Serverul va returna clientului sirul divizorilor acestui numar.
*/

#include<string.h>
#include<stdio.h>
#include<unistd.h>
#include<arpa/inet.h>
#include<stdlib.h>
#define DIM 101

//se returneaza dimensiunea sirului
uint8_t sirDivizori(uint8_t numar,uint8_t sir[DIM]){

	uint8_t j=0;
	for(int i=1;i<=numar/2;i++){
		if(numar%i==0){
			sir[j++]=i;
		}
	}
	sir[j++]=numar;
	return j;
}

int main(){

	int s,l;
	struct sockaddr_in server,client;
	l=sizeof(server);	

	if((s=socket(AF_INET,SOCK_DGRAM,0))<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	memset(&server,0,sizeof(server));
	memset(&client,0,sizeof(client));
	server.sin_family=AF_INET;
	server.sin_port=htons(1234);
	server.sin_addr.s_addr=inet_addr("0.0.0.0");

	if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la bind\n");
		return 1;
	}

	while(1){
		printf("Astept clienti...\n");
		uint8_t numar;
	
		recvfrom(s,&numar,sizeof(numar),MSG_WAITALL,
			(struct sockaddr*)&client,&l);	
	//	numar=ntohs(numar);
		printf("Am primit %hhu\n",numar);

		uint8_t sir[DIM];
		uint8_t dimensiune = sirDivizori(numar,sir);

	//	dimensiune=htons(dimensiune);
		sendto(s,&dimensiune,sizeof(dimensiune),0,
			(struct sockaddr*)&client,l);

	//	dimensiune=ntohs(dimensiune);
	
		sendto(s,sir,dimensiune*sizeof(sir[0]),0,
			(struct sockaddr*)&client,l);
	}
	return 0;
}
