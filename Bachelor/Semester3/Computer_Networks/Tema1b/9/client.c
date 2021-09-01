/*
Un client trimite unui server doua siruri de numere. Serverul va returna clientului sirul de numere care se regaseste in primul sir dar nu se regasesc in al doilea.
*/

#include<stdio.h>
#include<arpa/inet.h>
#include<unistd.h>
#include<stdlib.h>
#include<string.h>

//se va returna dimensiunea sirului citit
uint16_t citire_sir(uint16_t sir[101]){

	uint16_t dimensiune;
	printf("Dati cate numere doriti pentru sir\n");
	scanf("%hu",&dimensiune);
	printf("Dati cele %hu elemente\n",dimensiune);

	for(int i=0;i<dimensiune;i++){
		scanf("%hu",&sir[i]);
	}

	return dimensiune;
}

void trimitere_sir(int s,uint16_t dimensiune,uint16_t sir[101]){
	
	dimensiune=htons(dimensiune);
	send(s,&dimensiune,sizeof(dimensiune),0);

	dimensiune=ntohs(dimensiune);

	for(int i=0;i<dimensiune;i++){
		sir[i]=htons(sir[i]);
		send(s,&sir[i],sizeof(sir[i]),0);
		sir[i]=ntohs(sir[i]);
	}
}

int main(){

	int s;
	struct sockaddr_in server;

	if((s=socket(AF_INET,SOCK_STREAM,0))<0){
		printf("Eroare la creare socket\n");
		return 1;
	}	

	memset(&server,0,sizeof(server));

	server.sin_addr.s_addr=inet_addr("127.0.0.1");
	server.sin_family=AF_INET;
	server.sin_port=htons(8888);

	if(connect(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la bind\n");
		return 1;
	}

	uint16_t sir1[101],sir2[101];
	uint16_t dim1=citire_sir(sir1);
	uint16_t dim2=citire_sir(sir2);

	printf("Trimit datele\n");
	trimitere_sir(s,dim1,sir1);
	trimitere_sir(s,dim2,sir2);

	printf("Primesc rezultatul\n");

	uint16_t dim_rez,rez_curent;
	recv(s,&dim_rez,sizeof(dim_rez),MSG_WAITALL);
	dim_rez=ntohs(dim_rez);

	for(int i=0;i<dim_rez;i++){
		recv(s,&rez_curent,sizeof(rez_curent),MSG_WAITALL);
		rez_curent=ntohs(rez_curent);
		printf("%hu ",rez_curent);
	}
	printf("\n");
	return 0;
}
