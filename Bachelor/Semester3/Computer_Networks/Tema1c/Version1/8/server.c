/*
Un client trimite unui server doua numere reprezentate pe doi octeti fara semn fiecare. Serverul va returna clientului cmmdc si cmmmc al numerelor primite.
*/

#include<stdio.h>
#include<unistd.h>
#include<arpa/inet.h>
#include<string.h>
#include<stdlib.h>

uint16_t cmmdc(uint16_t nr1,uint16_t nr2){
	uint16_t minim = ((nr1 < nr2 ) ? nr1 : nr2);	
	uint16_t rez=1;
	for(int i=1;i<=minim;i++){
		if(nr1%i==0 && nr2%i==0){
			rez=i;
		}
	}
	return rez;
}


int main(){

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
	server.sin_port=htons(1234);	

	if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la bind\n");
		return 1;
	}

	while(1){
		printf("Astept clienti...\n");
		uint16_t nr1,nr2;
		recvfrom(s,&nr1,sizeof(nr1),MSG_WAITALL,(struct sockaddr*)&client,&l);
		recvfrom(s,&nr2,sizeof(nr2),MSG_WAITALL,(struct sockaddr*)&client,&l);

		nr1=ntohs(nr1); nr2=ntohs(nr2);

		printf("Am primit %hhu %hhu\n",nr1,nr2);
	
		uint16_t div = cmmdc(nr1,nr2);
		uint16_t multiplu=(nr1*nr2)/div;

		printf("Div %hhu Mul %hhu\n",div,multiplu);

		div=htons(div); multiplu=htons(multiplu);

		sendto(s,&div,sizeof(div),0,(struct sockaddr*)&client,l);
		sendto(s,&multiplu,sizeof(multiplu),0,(struct sockaddr*)&client,l);

	}
	return 0;
}
