/*
Un client trimite unui server doua siruri de caractere. Serverul ii raspunde clientului cu caracterul care se regaseste de cele mai multe ori pe pozitii identice in cele doua siruri si cu numarul de aparitii ale acestui caracter.
*/

#include<iostream>
#include<arpa/inet.h>
#include<unistd.h>
#include<string.h>
#include<stdlib.h>
#include<map>
#include<utility>
#define DIM 101
using namespace std;

pair<char,uint16_t> prelucrare(char sir1[DIM],char sir2[DIM]){

	map<char,uint16_t> dictionar;

	for(int i=0; i<strlen(sir1) && i<strlen(sir2) ; i++){
		if(sir1[i]==sir2[i]){
			dictionar[sir1[i]]++;
		}
	}

	//cautam frecventa maxima in dictionar
	char cmax='\0'; uint16_t frecvmax=0;
	for( pair<char,uint16_t> pereche : dictionar ) {
		if(pereche.second > frecvmax){
			frecvmax=pereche.second;
			cmax=pereche.first;
		}
	}

	return make_pair(cmax,frecvmax);
}

void citire_sir(int c,char sir[DIM]){
	uint16_t dimensiune;
	recv(c,&dimensiune,sizeof(dimensiune),MSG_WAITALL);
	dimensiune=ntohs(dimensiune);

	for(int i=0;i<dimensiune+1;i++){ // + 1 pt ca citim si terminatorul
		recv(c,&sir[i],sizeof(sir[i]),MSG_WAITALL);
	}

}

void deservire_client(int c){

	char sir1[DIM],sir2[DIM];
	citire_sir(c,sir1);
	citire_sir(c,sir2);

	pair<char,uint16_t> pereche = prelucrare(sir1,sir2);
	
	//trimit datele
	char cmax = pereche.first;
	uint16_t frecvmax=pereche.second;
	send(c,&cmax,sizeof(cmax),0);
	frecvmax=htons(frecvmax);
	send(c,&frecvmax,sizeof(frecvmax),0);

	close(c);
}

int main(){

	int s,c;
	struct sockaddr_in server,client;	
	socklen_t l=sizeof(client);

	if((s=socket(AF_INET,SOCK_STREAM,0))<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	memset(&client,0,sizeof(client));
	memset(&server,0,sizeof(server));
	server.sin_addr.s_addr=inet_addr("127.0.0.1");
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

	close(s);
	return 0;
}
