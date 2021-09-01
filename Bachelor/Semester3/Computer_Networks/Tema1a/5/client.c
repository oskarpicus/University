#include<string.h>
#include<unistd.h>
#include<arpa/inet.h>
#include<sys/types.h>
#include<stdio.h>
#include<sys/socket.h>

int main(){

	int s;
	struct sockaddr_in server;

	memset(&server,0,sizeof(server));

	s=socket(AF_INET,SOCK_STREAM,0);
	if(s<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	server.sin_addr.s_addr=inet_addr("127.0.0.1");
	server.sin_port=htons(1234);
	server.sin_family=AF_INET;

	if(connect(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la conectare\n");
		return 1;
	}

	uint16_t nr,dimensiune,v[100];

	printf("Dati numar\n");
	scanf("%hu",&nr);

	printf("Trimit numarul la server\n");
	nr=htons(nr);
	send(s,&nr,sizeof(nr),0);

	printf("Primesc dimensiunea sirului\n");
	recv(s,&dimensiune,sizeof(dimensiune),0);
	dimensiune=ntohs(dimensiune);

	printf("Primesc sirul divizorilor\n");
	for(int i=0;i<dimensiune;i++){
		recv(s,&v[i],sizeof(v[i]),0);
		v[i]=ntohs(v[i]);
		printf("%hu ",v[i]);
	}
	printf("\n");
	return 0;
}
