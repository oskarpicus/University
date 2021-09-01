/*
Un client trimite unui server doua siruri de caractere. Serverul ii raspunde clientului cu caracterul care se regaseste de cele mai multe ori pe pozitii identice in cele doua siruri si cu numarul de aparitii ale acestui caracter.
*/

#include<stdio.h>
#include<arpa/inet.h>
#include<sys/types.h>
#include<sys/socket.h>
#include<unistd.h>
#include<string.h>

void trimitereSir(int s,char sir[101]){
	printf("Am ajuns la trimitere sir %s\n",sir);
	for(int i=0;i<=strlen(sir);i++){
		send(s,&sir[i],sizeof(sir[i]),0);
	}
}

int main(){
	int s;
	struct sockaddr_in server;

	s=socket(AF_INET,SOCK_STREAM,0);
	if(s<0){
		printf("Eroare la creare socket\n");
		return 1;
	}

	memset(&server,0,sizeof(server));
	server.sin_family=AF_INET;
	server.sin_port=htons(1234);
	server.sin_addr.s_addr=inet_addr("127.0.0.1");

	if(connect(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la conectare socket\n");
		return 1;
	}

	char sir1[101],sir2[101];
        printf("Dati sirul 1\n");
        scanf("%s",sir1);
        printf("Dati sirul 2\n");
        scanf("%s",sir2);

        printf("Am citit %s si %s\n",sir1,sir2);


	printf("Trimit datele la server\n");
	uint16_t dim1=strlen(sir1),dim2=strlen(sir2);
	printf("Dim1 = %hu ; Dim2 = %hu\n",dim1,dim2);
	dim1=htons(dim1);
	dim2=htons(dim2);

	send(s,&dim1,sizeof(dim1),0);
//	send(s,sir1,strlen(sir1)+1,0);

	send(s,&dim2,sizeof(dim2),0);
//	send(s,sir2,strlen(sir2)+1,0);	

//	send(s,sir1,strlen(sir1)+1,0);
//	send(s,sir2,strlen(sir2)+1,0);

	
	trimitereSir(s,sir1);
	trimitereSir(s,sir2);

	printf("Am terminat cu trimiterea sirurilor\n");

	char cmax; uint16_t frecv;
	recv(s,&cmax,sizeof(cmax),MSG_WAITALL);
	recv(s,&frecv,sizeof(frecv),MSG_WAITALL);
	frecv=ntohs(frecv);

	printf("Caracterul %c cu frecventa %hu\n",cmax,frecv);

	return 0;
}
