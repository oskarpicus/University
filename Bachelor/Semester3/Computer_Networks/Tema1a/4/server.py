# Un client trimite unui server doua siruri de caractere ordonate. 
#Serverul va interclasa cele doua siruri si va
# returna clientului sirul rezultat interclasat.

import socket

def interclasare(sir1,sir2):
	sir3=[]
	list1=list(sir1)
	list2=list(sir2)
	curent_i=0
	curent_j=0

	while curent_i<len(list1) and curent_j<len(list2) :
		if list1[curent_i] < list2[curent_j]:
			sir3+=[list1[curent_i]]
			curent_i+=1
		else:
			sir3+=[list2[curent_j]]
			curent_j+=1

	while curent_i<len(list1):
		sir3+=[list1[curent_i]]
		curent_i+=1

	while curent_j<len(list2):
		sir3+=[list2[curent_j]]
		curent_j+=1

	gol=""
	return gol.join(sir3)


TCP_IP="127.0.0.1"
TCP_PORT=8888

s=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
s.bind((TCP_IP,TCP_PORT))

s.listen(5)

while 1:
	conn,addr = s.accept()
	print "S-a conectat", addr

	sir1=conn.recv(30)
	sir2=conn.recv(30)

	rez=interclasare(sir1,sir2)
	conn.send(rez)

conn.close()


