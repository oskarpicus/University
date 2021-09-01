#include<unistd.h>
#include<stdio.h>
#include<string.h>
#include<arpa/inet.h>
#include<sys/types.h>
#include<sys/socket.h>

uint16_t sirIndiciSpatii(char* sir,uint16_t* indici){
	uint16_t contor=0;
	for(uint16_t i=0;i<strlen(sir);i++)
		if(sir[i]==' ')
			indici[contor++]=i;
	return contor;
}

void trimitereSir(int c,uint16_t* indici,uint16_t dimensiune){
	uint16_t temporar=htons(dimensiune);
	printf("Trimit dimensiunea sirului %hu\n",dimensiune);
	send(c,&temporar,sizeof(temporar),0); //trimit dimensiunea sirului
	printf("Trimit sirul cu indici\n");
	for(int i=0;i<dimensiune;i++){ //trimit elementele sirului
		indici[i]=htons(indici[i]);
		send(c,&indici[i],sizeof(indici[i]),0);
	}
}

void citireSir(int c,char* sir,uint16_t dimensiune){
	for(int i=0;i<dimensiune+1;i++)
		recv(c,&sir[i],sizeof(char),MSG_WAITALL);
}

int main(){
	int s,c;
	struct sockaddr_in server,client;

	s=socket(AF_INET,SOCK_STREAM,0);
	if(s<0){
		printf("Eroare la creare socket\n");
		return 1;
	
	}

	memset(&server,0,sizeof(server));
	memset(&client,0,sizeof(client));

	server.sin_family=AF_INET;
	server.sin_port=htons(8888);
	server.sin_addr.s_addr=inet_addr("127.0.0.1");

	if(bind(s,(struct sockaddr*)&server,sizeof(server))<0){
		printf("Eroare la bind\n");
		return 1;
	}

	listen(s,5);
	int l=sizeof(client);
	
	uint16_t dim_string,indici[100],dim_sir;
	char sir[100];

	while(1){
		printf("Astept clienti....\n");
		c=accept(s,(struct sockaddr*)&client,&l);
		printf("S-a conectat un client\n");

		printf("Citesc dimensiunea string-ului\n");
		recv(c,&dim_string,sizeof(dim_string),MSG_WAITALL);
		dim_string=ntohs(dim_string);
		printf("Am citit %hu\n",dim_string);		

		printf("Citesc string-ul\n");
		citireSir(c,sir,dim_string);

		dim_sir=sirIndiciSpatii(sir,indici);
		printf("Dimensiune sir indici: %hu\n",dim_sir);

		printf("Trimit datele la client\n");
		trimitereSir(c,indici,dim_sir);

		close(c);		
	}

	return 0;
}
