#include<arpa/inet.h>
#include<sys/types.h>
#include<unistd.h>
#include<sys/socket.h>
#include<stdio.h>
#include<string.h>

int main(){

	int s;
	struct sockaddr_in server;

	s=socket(AF_INET,SOCK_STREAM,0);

	if(s<0){
		printf("Eroare la creare socket\n");
		return 1;
	}
	
	server.sin_family=AF_INET;
	server.sin_port=htons(4444);
	server.sin_addr.s_addr=inet_addr("127.0.0.1");

	if(connect(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la conectare\n");
		return 1;
	}

	char sir[101];
	uint16_t dimensiune;

	printf("Dati sirul\n");
	gets(sir);
	dimensiune=strlen(sir);

	//trimit dimensiunea stringului
	printf("Trimit dimensiunea stringului\n");
	uint16_t temporar=htons(dimensiune);
	send(s,&temporar,sizeof(temporar),0);

	//trimit sirul - includem si terminatorul
	printf("Trimit sirul %s\n",sir);
	send(s,sir,strlen(sir)+1,0);

	uint16_t rezultat;
	recv(s,&rezultat,sizeof(rezultat),0);
	rezultat=ntohs(rezultat);
	printf("Sirul are %hu caractere\n",rezultat);

	close(s);
	return 0;
}
