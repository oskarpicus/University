/*
Un client trimite unui server doua siruri de caractere. Serverul ii raspunde clientului cu caracterul care se regaseste de cele mai multe ori pe pozitii identice in cele doua siruri si cu numarul de aparitii ale acestui caracter.
*/
#include<arpa/inet.h>
#include<stdio.h>
#include<string.h>
#include<map>
#include<utility>
#include<sys/types.h>
#include<sys/socket.h>
#include<unistd.h>
using namespace std;

std::pair<char,uint16_t> deservire_client(char* s1,char* s2){
	int i=0;
	map<char,uint16_t> dict;
	while(s1[i]!='\0' && s2[i]!='\0'){
		if(s1[i]==s2[i])
			dict[s1[i]]++;
		i++;
	}
	//calculam maximul
	char cmax='\0';
	uint16_t maxim=0;
	for(std::pair<char,uint16_t> element : dict){
		if(element.second > maxim){
			cmax=element.first;
			maxim=element.second;
		}
	}
	return std::make_pair(cmax,maxim);
}

void citireSir(int c,char sir[101],uint16_t dimensiune){
	for(int i=0;i<dimensiune+1;i++){
		recv(c,&sir[i],sizeof(char),MSG_WAITALL);
		printf("Am citit caracterul %c\n",sir[i]);
	}
}

int main(){

	int s;
	uint16_t dim1,dim2;
	char sir1[101],sir2[101];
	struct sockaddr_in server,client;

	if((s=socket(AF_INET,SOCK_STREAM,0))<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	memset(&server,0,sizeof(server));
	memset(&client,0,sizeof(client));
	server.sin_family=AF_INET;
	server.sin_port=htons(1234);
	server.sin_addr.s_addr=inet_addr("127.0.0.1");

	if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la bind\n");
		return 1;
	}
	listen(s,5);
	socklen_t l=sizeof(client);

	while(1){
		printf("Astept clienti....\n");
		int c=accept(s,(struct sockaddr*)&client,&l);
		printf("S-a conectat un client\n");

		printf("Citest datele\n");
		recv(c,&dim1,sizeof(dim1),MSG_WAITALL);
		dim1=ntohs(dim1);
//		citireSir(c,sir1,dim1);

		printf("Am citit %hu %s\n",dim1,sir1);

		recv(c,&dim2,sizeof(dim2),MSG_WAITALL);
		dim2=ntohs(dim2);
//		citireSir(c,sir2,dim2);

		printf("Am citit %hu %s\n",dim2,sir2);

		citireSir(c,sir1,dim1);
		citireSir(c,sir2,dim2);


		std::pair<char,uint16_t> rezultat=deservire_client(sir1,sir2);

		printf("Trimit datele: %c %hu\n",rezultat.first,rezultat.second);

		char caracter=rezultat.first;
		uint16_t frecventa=rezultat.second;

		frecventa=htons(frecventa);
		send(c,&caracter,sizeof(caracter),0);
		send(c,&frecventa,sizeof(frecventa),0);

		close(c);
	}

	return 0;
}
