/* Un client trimite unui server doua numere. Serverul va returna clientului suma celor doua numere. */
#include<stdio.h>
#include<arpa/inet.h>
#include<unistd.h>
#include<string.h>
#include<stdlib.h>

int main(){

	int s,c,l,i;
	struct sockaddr_in server,client;

	s=socket(AF_INET,SOCK_DGRAM,0);
	if(s<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	memset(&client,0,sizeof(client));
	memset(&server,0,sizeof(server));
	server.sin_addr.s_addr=inet_addr("0.0.0.0");
	server.sin_family=AF_INET;
	server.sin_port=htons(1234);

	if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la bind\n");
		return 1;
	}

	l=sizeof(client);
	while(1){
		printf("Astept clienti...\n");
		uint16_t a,b;
		recvfrom(s,&a,sizeof(a),MSG_WAITALL,(struct sockaddr*)&client,&l);
		recvfrom(s,&b,sizeof(b),MSG_WAITALL,(struct sockaddr*)&client,&l);

		a=ntohs(a);
		b=ntohs(b);

		printf("Am primit %hu si %hu\n",a,b);

		uint16_t suma = a+b;
		suma=htons(suma);
		
		sendto(s,&suma,sizeof(suma),0,(struct sockaddr*)&client,l);
		
	}

	close(s);
	return 0;
}
