#Un client trimite unui server un numar. Serverul va returna
#clientului sirul divizorilor acestui numar.

import socket

TCP_IP="127.0.0.1"
TCP_PORT=8891

nr=input("Dati numar\n")

s=socket.socket(socket.AF_INET,socket.SOCK_STREAM)

s.connect((TCP_IP,TCP_PORT))

s.send(str(nr))

dim=s.recv(10)
print "Am primit ",dim,"\n"
for i in range(int(dim)):
	x=s.recv(10)
	print x,"\n"


