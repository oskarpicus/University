/*
Un client trimite unui server un numar reprezentat pe un octet fara semn. Serverul va returna clientului sirul divizorilor acestui numar.
*/

#include<string.h>
#include<arpa/inet.h>
#include<stdlib.h>
#include<stdio.h>
#define DIM 101

int main(){

	int s,l;
	struct sockaddr_in server;
	l=sizeof(server);

	if((s=socket(AF_INET,SOCK_DGRAM,0))<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	memset(&server,0,sizeof(server));
	server.sin_family=AF_INET;
	server.sin_addr.s_addr=inet_addr("127.0.0.1");
	server.sin_port=htons(1234);

	uint8_t numar;
	printf("Dati numar\n");
	scanf("%hhu",&numar);

//	numar=htons(numar);
	sendto(s,&numar,sizeof(numar),0,(struct sockaddr*)&server,l);

	uint8_t dimensiune;
	recvfrom(s,&dimensiune,sizeof(dimensiune),MSG_WAITALL,
		(struct sockaddr*)&server,&l);	

	printf("Numar divizori: %hhu\n",dimensiune);

	uint8_t sir[DIM];
	recvfrom(s,sir,sizeof(sir[0])*dimensiune,MSG_WAITALL,
		(struct sockaddr*)&server,&l);

	for(int i=0;i<dimensiune;i++){
		printf("%hhu ",sir[i]);
	}
	printf("\n");
	return 0;
}
