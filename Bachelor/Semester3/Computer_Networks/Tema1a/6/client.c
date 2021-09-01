#include<unistd.h>
#include<stdio.h>
#include<string.h>
#include<arpa/inet.h>
#include<sys/types.h>
#include<sys/socket.h>

int main(){

	char sir[101];
	uint16_t dim_string,indici[100],dim_sir;

	int s;
	struct sockaddr_in server;

	s=socket(AF_INET,SOCK_STREAM,0);	
	if(s<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	memset(&server,0,sizeof(server));
	server.sin_addr.s_addr=inet_addr("127.0.0.1");
	server.sin_family=AF_INET;
	server.sin_port=htons(8888);

	if(connect(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la conectare server\n");
		return 1;
	}

	printf("Dati sir\n");
	gets(sir);
	dim_string=strlen(sir);

	printf("Trimit datele la server\n");
	dim_string=htons(dim_string);
	send(s,&dim_string,sizeof(dim_string),0);

	send(s,sir,strlen(sir)+1,0);

	printf("Primesc datele de la server\n");
	recv(s,&dim_sir,sizeof(dim_sir),MSG_WAITALL);
	dim_sir=ntohs(dim_sir);
	printf("Dimensiune sir indici: %hu\n",dim_sir);

	printf("Primesc sirul\n");
	for(int i=0;i<dim_sir;i++){
		recv(s,&indici[i],sizeof(indici[i]),0);
		indici[i]=ntohs(indici[i]);
		printf("%hu ",indici[i]);
	}
	printf("\n");
	close(s);
	return 0;
}
