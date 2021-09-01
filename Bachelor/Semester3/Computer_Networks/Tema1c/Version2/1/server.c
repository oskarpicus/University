/*
Un client trimite unui server doua numere. Serverul va returna clientului suma celor doua numere.
*/

#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<arpa/inet.h>
#include<string.h>

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
	server.sin_addr.s_addr=inet_addr("0.0.0.0");
	server.sin_family=AF_INET;
	server.sin_port=htons(atoi(argv[1]));

	if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la bind\n");
		return 1;
	}

	while(1){
		printf("Astept clienti..\n");
		uint16_t nr1,nr2;

		recvfrom(s,&nr1,sizeof(nr1),MSG_WAITALL,(struct sockaddr*)&client,&l);
		recvfrom(s,&nr2,sizeof(nr2),MSG_WAITALL,(struct sockaddr*)&client,&l);
	
		nr1=ntohs(nr1);
		nr2=ntohs(nr2);
		printf("Am primit %hu si %hu\n",nr1,nr2);

		nr1+=nr2;
		nr1=htons(nr1);
		sendto(s,&nr1,sizeof(nr1),0,(struct sockaddr*)&client,l);

	}
	close(s);
	return 0;
}
