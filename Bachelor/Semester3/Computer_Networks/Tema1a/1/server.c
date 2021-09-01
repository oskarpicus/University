#include<string.h>
#include<sys/types.h>
#include<sys/socket.h>
#include<unistd.h>
#include<arpa/inet.h>
#include<stdio.h>

int main(){

	int s;
	struct sockaddr_in server,client;

	s=socket(AF_INET,SOCK_STREAM,0);
	if(s<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	memset(&client,0,sizeof(client));
	memset(&server,0,sizeof(server));
	server.sin_port=htons(1234);
	server.sin_family=AF_INET;
	server.sin_addr.s_addr=INADDR_ANY;
	
	if(bind(s,(struct sockaddr*)& server,sizeof(server))<0){
		printf("Eroare la bind\n");
		return 1;
	}

	listen(s,5);

	while(1){

		int l=sizeof(client);
		int c=accept(s,(struct sockaddr*)&client,&l);
		
		printf("S-a conectat un client\n");

		uint16_t dim;
		recv(c,&dim,sizeof(dim),0);
	
		dim=ntohs(dim);
		printf("Am primit lungimea vectorului: %hu\n",dim);

		uint16_t suma=0;

		for(int i=0;i<dim;i++){
			uint16_t x;
			recv(c,&x,sizeof(x),0);
			x=ntohs(x);
			printf("Am primit %hu\n",x);
			suma+=x;
		}

		suma=htons(suma);
		send(c,&suma,sizeof(suma),0);

		close(c);
	}

	return 0;
}
