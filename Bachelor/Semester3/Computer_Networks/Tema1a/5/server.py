#Un client trimite unui server un numar. Serverul va returna 
#clientului sirul divizorilor acestui numar.

import socket

def sirDivizori(nr):
	rez=[] # lista de string-uri cu divizorii
	for i in range(1,nr//2+1):
		if nr%i==0:
			rez.append(str(i))
	rez.append(str(nr))
	return rez


TCP_IP="127.0.0.1"
TCP_PORT=8891

s=socket.socket(socket.AF_INET,socket.SOCK_STREAM)

s.bind((TCP_IP,TCP_PORT))

s.listen(5)

while 1:
	conn,addr=s.accept()
	print "S-a conectat" , addr
	
	nr=conn.recv(10)
	print "Am citit", nr
	rezultat=sirDivizori(int(nr))
	print "Am calculat", rezultat

	conn.send(str(len(rezultat)))
	print "Am trimis ",str(len(rezultat))
	print "Trimit lista\n"
	for e in rezultat:
		print e
		conn.send(str(e))


conn.close()

