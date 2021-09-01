#Un client trimite unui server un sir de numere. Serverul va returna 
#clientului suma numerelor primite.

import socket

#TCP_IP="127.0.0.1"
TCP_IP="0.0.0.0"
TCP_PORT=8888

s=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
s.bind((TCP_IP,TCP_PORT))
s.listen(5)

while 1:
	ctr, adr=s.accept()
	print "S-a conectat ",adr

	dim=int(ctr.recv(100))
	suma=0
	for i in range(dim):
		suma+=int(ctr.recv(100))	

	ctr.send(str(suma))
	print "Am trimis ",suma

ctr.close()
print "O zi buna"
