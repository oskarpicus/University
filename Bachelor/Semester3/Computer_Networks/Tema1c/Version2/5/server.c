/*
Un client trimite unui server un numar reprezentat pe un octet fara semn. Serverul va returna clientului sirul divizorilor acestui numar.
*/

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<arpa/inet.h>
#include<unistd.h>
#define DIM 101

//se returneaza numar divizorilo
uint16_t sirDivizori(uint8_t nr,uint16_t sir[DIM]){
	uint16_t d = 0;
	for(int i=1;i<=nr/2;i++){
		if(nr%i==0){
			sir[d++]=i;
		}
	}
	sir[d++]=nr;
	return d;
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
	server.sin_port=htons(atoi(argv[1]));
	server.sin_family=AF_INET;
	server.sin_addr.s_addr=inet_addr("0.0.0.0");

	if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la bind\n");
		return 1;
	}

	while(1){
		printf("Astept clienti...\n");
		uint8_t nr;
		recvfrom(s,&nr,sizeof(nr),MSG_WAITALL,(struct sockaddr*)&client,
			&l);
	
		printf("Am primit %hhu\n",nr);

		uint16_t sir[DIM];
		uint16_t dim=sirDivizori(nr,sir);

		dim=htons(dim);
		sendto(s,&dim,sizeof(dim),0,(struct sockaddr*)&client,l);
		dim=ntohs(dim);

		for(int i=0;i<dim;i++){
			sir[i]=htons(sir[i]);
			sendto(s,&sir[i],sizeof(sir[i]),0,
				(struct sockaddr*)&client,l);
		}

	}

	return 0;
}
