#Un client trimite unui server un sir de caractere. Serverul va returna 
#clientului acest sir oglindit (caracterele sirului in ordine inversa)

import socket

TCP_IP="127.0.0.1"
TCP_PORT=8888

text=raw_input("Dati text:\n")

s=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
s.connect((TCP_IP,TCP_PORT))

s.send(text)

rez=s.recv(100)
print "Rezultat:\n",rez

