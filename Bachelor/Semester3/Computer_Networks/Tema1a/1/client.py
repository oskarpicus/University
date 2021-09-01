#Un client trimite unui server un sir de numere. Serverul va 
#returna clientului suma numerelor primite.

import socket

TCP_IP="127.0.0.1"
TCP_PORT=8888

def citesteNumere():
	l=[]
	while 1:
		print "Dati numar sau stop"
		date=raw_input()
		if date=="stop":
			break
		l.append(date)
	return l



l=citesteNumere()
dim=len(l)

s=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
s.connect((TCP_IP,TCP_PORT))

s.send(str(dim))
for el in l :
	s.send(el)

suma=s.recv(100)
print "Suma este ",suma

