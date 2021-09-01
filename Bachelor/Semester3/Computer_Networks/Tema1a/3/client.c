#include<sys/types.h>
#include<sys/socket.h>
#include<unistd.h>
#include<arpa/inet.h>
#include<stdio.h>
#include<string.h>

void citireSir(int c,char* sir){
	uint16_t dim=strlen(sir);
	for(int i=0;i<dim+1;i++){
		recv(c,&sir[i],sizeof(char),0);
	}
}

int main(){

	int s;
	struct sockaddr_in server;

	memset(&server,0,sizeof(server));
	s=socket(AF_INET,SOCK_STREAM,0);
	
	if(s<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	server.sin_family=AF_INET;
	server.sin_port=htons(1239);
	server.sin_addr.s_addr=inet_addr("127.0.0.1");

	if(connect(s,(struct sockaddr*)&server,sizeof(server))){
		printf("Eroare la conectare server\n");
		return 1;
	}

	char sir[101];
	uint16_t dimensiune;

	printf("Dati sirul\n");
	gets(sir);
	dimensiune=strlen(sir);

	printf("Trimit dimensiunea sirului\n");
	dimensiune=htons(dimensiune);
	send(s,&dimensiune,sizeof(dimensiune),0);

	printf("Trimit sirul\n");
	send(s,sir,strlen(sir)+1,0);
	dimensiune=ntohs(dimensiune);	

	printf("Primesc inapoi sirul\n");
	citireSir(s,sir);
	printf("Sirul oglindit este: %s\n",sir);

	close(s);
	return 0;
}
