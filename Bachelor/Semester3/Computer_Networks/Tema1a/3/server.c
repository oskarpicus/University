#include<sys/types.h>
#include<sys/socket.h>
#include<unistd.h>
#include<arpa/inet.h>
#include<stdio.h>
#include<string.h>

void trimiteSir(int c,char* sir){
	for(int i=0;i<strlen(sir)+1;i++){
		send(c,&sir[i],sizeof(char),0);
	}	
}

void citesteSir(int c,char* sir,uint16_t dimensiune){
	for(int i=0;i<dimensiune+1;i++){
		recv(c,&sir[i],sizeof(char),0);
	}
}

void oglindit(char* sir){
	for(int i=0;i<strlen(sir)/2;i++){
		char aux=sir[i];
		sir[i]=sir[strlen(sir)-i-1];
		sir[strlen(sir)-i-1]=aux;
	}
}

int main(){

	int s,c;
	struct sockaddr_in client,server;

	memset(&server,0,sizeof(server));
	memset(&client,0,sizeof(client));

	s=socket(AF_INET,SOCK_STREAM,0);
	if(s<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	server.sin_family=AF_INET;
	server.sin_port=htons(1239);
	server.sin_addr.s_addr=inet_addr("127.0.0.1");

	if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la bind\n");
		return 1;
	}

	listen(s,5);

	int l=sizeof(client);

	uint16_t dimensiune;
	char sir[101];

	while(1){
		printf("Astept clienti.......\n");
		c=accept(s,(struct sockaddr*)&client,&l);
		printf("S-a conectat un client\n");	
	
		recv(c,&dimensiune,sizeof(dimensiune),0);
		dimensiune=ntohs(dimensiune);
		printf("Am primit dimensiunea %hu\n",dimensiune);
		
		citesteSir(c,sir,dimensiune);
		printf("Am primit sirul %s\n",sir);

		oglindit(sir);
		printf("Trimit oglinditul: %s\n",sir);
		trimiteSir(c,sir);
//		send(s,sir,strlen(sir)+1,0);

		close(c);
	}

	return 0;
}
