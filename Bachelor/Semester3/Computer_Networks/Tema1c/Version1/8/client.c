/*
Un client trimite unui server doua numere reprezentate pe doi octeti fara semn fiecare. Serverul va returna clientului cmmdc si cmmmc al numerelor primite.
*/

#include<stdio.h>
#include<arpa/inet.h>
#include<unistd.h>
#include<string.h>
#include<stdlib.h>

int main(){

	int s,l;
	struct sockaddr_in server;
	l=sizeof(server);

	if((s=socket(AF_INET,SOCK_DGRAM,0))<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	memset(&server,0,sizeof(server));
	server.sin_port=htons(1234);
	server.sin_family=AF_INET;
	server.sin_addr.s_addr=inet_addr("0.0.0.0");

	uint16_t nr1,nr2;
	printf("Dati cele 2 numere\n");
	scanf("%hu%hu",&nr1,&nr2);

	nr1=htons(nr1); nr2=htons(nr2);

	sendto(s,&nr1,sizeof(nr1),0,(struct sockaddr*)&server,l);
	sendto(s,&nr2,sizeof(nr2),0,(struct sockaddr*)&server,l);

	uint16_t div,mul;
	recvfrom(s,&div,sizeof(div),MSG_WAITALL,(struct sockaddr*)&server,&l);
	recvfrom(s,&mul,sizeof(mul),MSG_WAITALL,(struct sockaddr*)&server,&l);

	div=ntohs(div); mul=ntohs(mul);

	printf("Cmmdc = %hu\nCmmmc = %hu\n",div,mul);

	close(s);
	return 0;
}
