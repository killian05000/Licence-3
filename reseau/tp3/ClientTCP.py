import socket

HOST = '127.0.0.1'
PORT = 1234
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((HOST, PORT))

message='a'

while message != 'stop':
    message = input("quelle est le message :")
    s.send(message)

data = s.recv(1024)
s.close()
print 'Received', 'data'
