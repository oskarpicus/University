/*
Un client trimite unui server un sir de caractere. Serverul va returna clientului numarul de caractere spatiu din sir.
*/
#include<stdlib.h>
#include<unistd.h>
#include<arpa/inet.h>
#include<stdio.h>
#include<string.h>

int main(){

	int s;
	struct sockaddr_in server;

	if((s=socket(AF_INET,SOCK_STREAM,0))<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	memset(&server,0,sizeof(server));
	server.sin_addr.s_addr=inet_addr("127.0.0.1");
	server.sin_family=AF_INET;
	server.sin_port=htons(8888);

	if(connect(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la connect\n");
		return 1;
	}

	char v[101];
	printf("Dati sir\n");
	gets(v);

	uint16_t dim=strlen(v);
	dim=htons(dim);
	send(s,&dim,sizeof(dim)+1,0);
	send(s,v,strlen(v)+1,0);

	uint16_t rez=0;
	recv(s,&rez,sizeof(rez),MSG_WAITALL);
	rez=ntohs(rez);
	printf("Nr. spatii = %hu\n",rez);	

	return 0;
}
