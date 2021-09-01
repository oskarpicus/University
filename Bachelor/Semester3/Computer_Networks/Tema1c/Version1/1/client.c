/*
Un client trimite unui server doua numere. Serverul va returna clientului suma celor doua numere.
*/

#include<stdio.h>
#include<unistd.h>
#include<string.h>
#include<arpa/inet.h>

int main(){

	int c;
	struct sockaddr_in server;
	int l =sizeof(server);

	if((c=socket(AF_INET,SOCK_DGRAM,0))<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	memset(&server,0,sizeof(server));
	server.sin_addr.s_addr = inet_addr("127.0.0.1");
	server.sin_family=AF_INET;
	server.sin_port=htons(1234);

	uint16_t a,b,suma;
	printf("Dati primul numar\n");
	scanf("%hu",&a);
	printf("Dati al doilea numar\n");
	scanf("%hu",&b);

	a=htons(a);
	b=htons(b);
	sendto(c,&a,sizeof(a),0,(struct sockaddr*)&server,sizeof(server));
	sendto(c,&b,sizeof(b),0,(struct sockaddr*)&server,sizeof(server));

	recvfrom(c,&suma,sizeof(suma),MSG_WAITALL,(struct sockaddr*)&server,&l);
	suma=ntohs(suma);

	printf("Suma = %hu\n",suma);
	
	close(c);

	return 0;
}
