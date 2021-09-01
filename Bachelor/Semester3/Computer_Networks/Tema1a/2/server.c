#include<arpa/inet.h>
#include<unistd.h>
#include<sys/types.h>
#include<sys/socket.h>
#include<string.h>
#include<stdio.h>

uint16_t nrSpatii(char* v){

	int nr=0;
	for(int i=0;i<strlen(v);i++)
		if(v[i]==' ')
			nr++;
	return nr;
}

void citesteSir(int c,char* sir,uint16_t dimensiune){
	for(int i=0;i<dimensiune+1;i++){
		recv(c,&sir[i],sizeof(char),0);
	}
}

int main(){

	int s,c,l;
	char sir[101];
	struct sockaddr_in server,client;

	s=socket(AF_INET,SOCK_STREAM,0);
	if(s<0){
		printf("Eroare la creare server\n");
		return 1;
	}

	server.sin_family=AF_INET;
	server.sin_port=htons(4444);
	server.sin_addr.s_addr=inet_addr("127.0.0.1");

	if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la server\n");
		return 1;
	}

	listen(s,5);

	l=sizeof(client);

	while(1){
		printf("Astept clienti\n");
		c=accept(s,(struct sockaddr*)&client,&l);
		printf("S-a conectat un client\n");

		uint16_t dimensiune;
		recv(c,&dimensiune,sizeof(dimensiune),0);
		dimensiune=ntohs(dimensiune);
		printf("Dimensiunea sirului este %hu\n",dimensiune);
	
//		recv(s,sir,dimensiune+1,0);
//		printf("Am primit sirul %s\n",sir);

//		for(int i=0;i<dimensiune+1;i++){
//			recv(c,&sir[i],sizeof(char),0);
//		}

		citesteSir(c,sir,dimensiune);

		uint16_t rezultat=nrSpatii(sir);
		printf("Rezultat = %hu\n",rezultat);
		rezultat=htons(rezultat);
		send(c,&rezultat,sizeof(rezultat),0);

		close(c);
	}

	close(s);
	return 0;
}
