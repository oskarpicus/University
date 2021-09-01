#Un client trimite unui server un sir de caractere. Serverul va 
#returna clientului numarul de caractere spatiu din sir.

import socket

def nrSpatii(text):
	contor=0
	for c in text:
		if c==' ':
			contor+=1
	return contor

TCP_PORT=8888
TCP_IP="127.0.0.1"

s=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
s.bind((TCP_IP,TCP_PORT))
s.listen(1)

while 1:
	
	ctr, adr = s.accept()
	print "S-a conectat ",adr
	
	text=ctr.recv(100)
	print "Am primit ",text

	if not text:
		break

	nr=nrSpatii(text)
	ctr.send(str(nr))


ctr.close()
print "O zi buna"
