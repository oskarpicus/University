#include<string.h>
#include<sys/types.h>
#include<sys/socket.h>
#include<stdio.h>
#include<arpa/inet.h>
#include<unistd.h>

void trimitereSir(int c,uint16_t v[100],uint16_t dimensiune){
	for(int i=0;i<dimensiune;i++){
		v[i]=htons(v[i]);
		send(c,&v[i],sizeof(v[i]),0);
	}
}

//se returneaza dimensiunea sirului
uint16_t sirDivizori(uint16_t nr,uint16_t v[100]){
	uint16_t contor=0;
	for(int i=1;i<=nr/2;i++)
		if(nr%i==0)
			v[contor++]=i;
	v[contor++]=nr;
	return contor;	
}

int main(){

	int s,c;
	struct sockaddr_in client,server;

	memset(&client,0,sizeof(client));
	memset(&server,0,sizeof(server));

	s=socket(AF_INET,SOCK_STREAM,0);
	if(s<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	server.sin_family=AF_INET;
	server.sin_port=htons(1234);
	server.sin_addr.s_addr=inet_addr("127.0.0.1");

	if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la bind\n");
		return 1;
	}

	listen(s,5);
	int l=sizeof(client);

	uint16_t nr,v[100],dimensiune;

	while(1){
		printf("Astept clienti.....\n");
		c=accept(s,(struct sockaddr*)&client,&l);
		printf("S-a conectat un client\n");

		recv(c,&nr,sizeof(nr),MSG_WAITALL);
		nr=ntohs(nr);
		printf("Am primit %hu\n",nr);

		dimensiune=sirDivizori(nr,v);
		printf("Trimit dimensiunea sirului\n");
		uint16_t temporar=htons(dimensiune);
		send(c,&temporar,sizeof(temporar),0);

		printf("Trimit sirul\n");
		trimitereSir(c,v,dimensiune);

		close(c);

	}

	return 0;
}
