/*
Un client trimite unui server doua siruri de numere. Serverul va returna clientului sirul de numere comune celor doua siruri primite.
*/

#include<stdio.h>
#include<arpa/inet.h>
#include<stdlib.h>
#include<string.h>
#include<unistd.h>
#define DIM 100

//se returneaza dimensiunea sirului
uint16_t citire_sir(int c,uint16_t sir[DIM]){
	uint16_t lungime;
	recv(c,&lungime,sizeof(lungime),MSG_WAITALL);
	lungime=ntohs(lungime);

	for(int i=0;i<lungime;i++){
		recv(c,&sir[i],sizeof(sir[i]),MSG_WAITALL);
		sir[i]=ntohs(sir[i]);
	}

	return lungime;
}

//se retuneaza 1 daca apare, 0, altfel
int apare(uint16_t sir[DIM],uint16_t dim,uint16_t element){
	int ok=0;
	for(int i=0;i<dim && ok==0;i++){
		ok= (sir[i]==element) ? 1 : 0;
	}
	return ok;
}

//se retunreaza dimensiunea sirului rezultat
uint16_t prelucrare(uint16_t sir1[DIM],uint16_t dim1,uint16_t sir2[DIM],uint16_t dim2,uint16_t sir_rez[DIM]){
	
	uint16_t dim_rez=0;
	for(int i=0;i<dim1;i++){ //parcurg primul sir
		if(apare(sir2,dim2,sir1[i])==1){
			sir_rez[dim_rez++]=sir1[i];
		}
	}

	return dim_rez;
}

void trimite_sir(int c,uint16_t sir[DIM],uint16_t dim){
	dim=htons(dim);
	send(c,&dim,sizeof(dim),0);
	dim=ntohs(dim);
	for(int i=0;i<dim;i++){
		sir[i]=htons(sir[i]);
		send(c,&sir[i],sizeof(sir[i]),0);
	}
}

void deservire_client(int c){
	uint16_t dim1,dim2,sir1[DIM],sir2[DIM],sir_rez[DIM],dim_rez;
	dim1=citire_sir(c,sir1);
	dim2=citire_sir(c,sir2);

	dim_rez=prelucrare(sir1,dim1,sir2,dim2,sir_rez);

	//trimit datele
	trimite_sir(c,sir_rez,dim_rez);

	close(c);
}

int main(){

	int s,c,l;
	struct sockaddr_in server,client;
	l=sizeof(client);

	if((s=socket(AF_INET,SOCK_STREAM,0))<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	memset(&server,0,sizeof(server));
	memset(&client,0,sizeof(client));
	server.sin_addr.s_addr=inet_addr("0.0.0.0");
	server.sin_family=AF_INET;
	server.sin_port=htons(8888);

	if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la bind\n");
		return 1;
	}

	listen(s,5);

	while(1){
		printf("Astept clienti...\n");
		c=accept(s,(struct sockaddr*)&client,&l);
		printf("S-a conectat un client\n");
		if(fork()==0){
			deservire_client(c);
			exit(0);
		}
	}

	return 0;
}
