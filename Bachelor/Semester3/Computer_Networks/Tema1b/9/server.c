/*
Un client trimite unui server doua siruri de numere. Serverul va returna clientului sirul de numere care se regaseste in primul sir dar nu se regasesc in al doilea.
*/

#include<stdio.h>
#include<arpa/inet.h>
#include<unistd.h>
#include<stdlib.h>
#include<string.h>

//se va returna dimensiunea sirului
uint16_t citire_sir(int c,uint16_t sir[101]){
	uint16_t dimensiune;
	recv(c,&dimensiune,sizeof(dimensiune),MSG_WAITALL);
	dimensiune=ntohs(dimensiune);
	printf("Dimensiune = %hu\n",dimensiune);

	for(int i=0;i<dimensiune;i++){
		recv(c,&sir[i],sizeof(sir[i]),MSG_WAITALL);
		sir[i]=ntohs(sir[i]);
		printf("Am primit %hu\n",sir[i]);
	}
	return dimensiune;
}

//se returneaza 1 daca element apare in sir, 0, altfel
int apare(uint16_t sir[101],uint16_t dim,uint16_t element){
	uint16_t ok=0;
	for(int i=0;i<dim && ok==0;i++){
		if(sir[i]==element)
			ok=1;
	}
	return ok;
}

void deservire_client(int c){
	uint16_t sir1[101],sir2[101],rezultat[101];
	uint16_t dim1,dim2,dim_rez=0;

	printf("1)   ");
	dim1=citire_sir(c,sir1);
	printf("2)   ");
	dim2=citire_sir(c,sir2);

	for(int i=0;i<dim1;i++) //parurgem primul sir
	{	
		if(apare(sir2,dim2,sir1[i])==0){
			rezultat[dim_rez++]=sir1[i];
		}
	}

	//trimit rezultatele
	dim_rez=htons(dim_rez);
	send(c,&dim_rez,sizeof(dim_rez),0);

	dim_rez=ntohs(dim_rez);
	for(int i=0;i<dim_rez;i++){
		rezultat[i]=htons(rezultat[i]);
		send(c,&rezultat[i],sizeof(rezultat[i]),0);
	}
	close(c);
}

int main(){

	int s,c,l;
	struct sockaddr_in server,client;

	if((s=socket(AF_INET,SOCK_STREAM,0))<0){
		printf("Eroare la creare socket\n");
		return 1;
	}	

	memset(&server,0,sizeof(server));
	memset(&client,0,sizeof(client));
	l=sizeof(client);

	server.sin_addr.s_addr=inet_addr("127.0.0.1");
	server.sin_family=AF_INET;
	server.sin_port=htons(8888);

	if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la bind\n");
		return 1;
	}

	listen(s,5);
	printf("Pot primi clienti\n");

	while(1){
		c=accept(s,(struct sockaddr*)&client,&l);
		printf("S-a conectat un client\n");

		if(fork()==0){
			deservire_client(c);
			exit(0);
		}

	}

	return 0;
}
