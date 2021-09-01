#Un client trimite unui server un sir de caractere si un caracter.
#Serverul va returna clientului toate pozitiile pe care caracterul 
#primit se regaseste in sir.

import socket

def pozitii(s,c):
	rez=[]
	for i in range(len(s)):
		if s[i]==c:
			rez+=[str(i)]
	return rez

TCP_IP="127.0.0.1"
TCP_PORT=8888

s=socket.socket(socket.AF_INET,socket.SOCK_STREAM)

s.bind((TCP_IP,TCP_PORT))

s.listen(5)

while 1:
	conn,addr=s.accept()
	print "S-a conectat", addr

	text=conn.recv(50)
	caracter=conn.recv(1)
	print "S-a primit\n",text," ",c

	rezultat=pozitii(text,caracter)
	dim=str(len(rezultat))

	print "Trimit datele"

	conn.send(dim)
	for el in rezultat:
		conn.send(el)

conn.close()
