#Un client trimite unui server un sir de caractere. Serverul va 
#returna clientului numarul de caractere spatiu din sir.

import socket

text=str(raw_input("Dati text\n"))

TCP_PORT=8888
TCP_IP="127.0.0.1"

s=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
s.connect((TCP_IP,TCP_PORT))

s.send(text)

rez=s.recv(100)
print "Textul dat are ",rez," spatii"

