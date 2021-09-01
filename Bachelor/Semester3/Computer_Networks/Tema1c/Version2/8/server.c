/*
Un client trimite unui server doua numere reprezentate pe doi octeti fara semn fiecare. Serverul va returna clientului cmmdc si cmmmc al numerelor primite.
*/

#include<stdio.h>
#include<string.h>
#include<unistd.h>
#include<arpa/inet.h>
#include<stdlib.h>

uint16_t cmmdc(uint16_t x,uint16_t y){

	uint16_t minim = (x<y) ? x : y;
	uint16_t rez;
	for(int i=1;i<=minim;i++)
		if(x%i==0 && y%i==0)
			rez=i;
	return rez;
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
	server.sin_addr.s_addr=inet_addr("0.0.0.0");
	server.sin_family=AF_INET;

	if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la bind\n");
		return 1;
	}

	while(1){
		printf("Astept clienti..\n");
		uint16_t x,y;
		
		recvfrom(s,&x,sizeof(x),MSG_WAITALL,(struct sockaddr*)&client,&l);
		recvfrom(s,&y,sizeof(y),MSG_WAITALL,(struct sockaddr*)&client,&l);

		x=ntohs(x); y=ntohs(y);
	
		printf("Am primit %hu %hu\n",x,y);

		uint16_t rez1=cmmdc(x,y);
		uint16_t rez2=x*y / rez1;

		rez1=htons(rez1); rez2=htons(rez2);
		sendto(s,&rez1,sizeof(rez1),0,(struct sockaddr*)&client,l);
		sendto(s,&rez2,sizeof(rez2),0,(struct sockaddr*)&client,l);
	}
	close(s);
	return 0;
}
