/*
Un client trimite unui server un numar. Serverul va returna clientului un boolean care sa indice daca numarul respective este prim sau nu.
*/

#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<arpa/inet.h>
#include<string.h>

// 1 pt prim, 0 pt neprim
uint8_t prim(uint16_t nr){
	uint8_t rez=1;
	for(int i=2;i<=nr/2 && rez==1;i++){
		if(nr%i==0){
			rez=0;
		}
	}
	return rez;
}

int main(int args,char** argv){

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
		uint16_t nr;
		
		recvfrom(s,&nr,sizeof(nr),MSG_WAITALL,(struct sockaddr*)&client,&l);
		nr=ntohs(nr);
		printf("Am primit %hu\n",nr);
		uint8_t rez=prim(nr);
		sendto(s,&rez,sizeof(rez),0,(struct sockaddr*)&client,l);
	}

	close(s);
	return 0;
}
