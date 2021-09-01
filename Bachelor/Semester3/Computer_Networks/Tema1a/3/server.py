#Un client trimite unui server un sir de caractere. Serverul va returna 
#clientului acest sir oglindit (caracterele sirului in ordine inversa)

import socket

def oglindit(text):
	rez=""
	for c in text[::-1]:
		rez+=c
	return rez


TCP_IP="127.0.0.1"
TCP_PORT=8888

s=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
s.bind((TCP_IP,TCP_PORT))
s.listen(5)

while 1:

	cnt, adr = s.accept()
	print "A intrat ",adr
	
	text=cnt.recv(100)
	print "Am primit ",text

	rez=oglindit(text)
	cnt.send(rez)
	print "Am trimis ",rez

cnt.close()
