/*
Un client trimite unui server un sir de caractere. Serverul va returna clientului acest sir oglindit (caracterele sirului in ordine inversa).
*/

#include<arpa/inet.h>
#include<string.h>
#include<stdlib.h>
#include<stdio.h>
#include<unistd.h>
#include<string.h>

void citire_sir(int c,char sir[101],uint16_t dimensiune){

	for(int i=0;i<dimensiune+1;i++){
		recv(c,&sir[i],sizeof(sir[i]),MSG_WAITALL);
	}
}

void trimitere_sir(int c,char sir[101]){
	uint16_t dim=strlen(sir);
	dim=htons(dim);
	send(c,&dim,sizeof(dim),0);
	for(int i=0;i<strlen(sir)+1;i++){
		send(c,&sir[i],sizeof(sir[i]),0);
	}
}

int main(){

	int s;
	struct sockaddr_in server;

	if((s=socket(AF_INET,SOCK_STREAM,0))<0){
		printf("Ëroare la creare socket\n");
		return 1;
	}

	memset(&server,0,sizeof(server));
	server.sin_addr.s_addr=inet_addr("127.0.0.1");
	server.sin_family=AF_INET;
	server.sin_port=htons(8888);

	if(connect(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la conectare\n");
		return 1;
	}

	printf("Dati sir\n");
	char sir[101];
	scanf("%s",sir);
	trimitere_sir(s,sir);

	uint16_t dim=strlen(sir);
	citire_sir(s,sir,dim);

	printf("Rezultat : %s\n",sir);

	return 0;
}
