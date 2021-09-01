# Un client trimite unui server doua siruri de caractere ordonate.
#Serverul va interclasa cele doua siruri si va
# returna clientului sirul rezultat interclasat.

import socket

TCP_IP="127.0.0.1"
TCP_PORT=8888


sir1=raw_input("Dati primul sir ")
sir2=raw_input("Dati al doilea sir ")

s=socket.socket(socket.AF_INET,socket.SOCK_STREAM)

s.connect((TCP_IP,TCP_PORT))

s.send(sir1)
s.send(sir2)

rez=s.recv(60)
print rez

