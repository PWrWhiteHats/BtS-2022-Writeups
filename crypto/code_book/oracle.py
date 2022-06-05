#!/usr/bin/python2
from Crypto.Cipher import AES
from Crypto.Util.Padding import pad
from Crypto.Util.Padding import unpad
import base64
import json
import sys
import socket
import select
import threading
from thread import *

socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
flag = open("flag.txt").read()[:-1]

key = "LKJre90234sdfa%!"

def main():
    establishConnection()
    listenAndCreateNewSession()
    return

def establishConnection():
    host = sys.argv[1] 
    port = int(sys.argv[2])
    socket.bind((host, port))
    return

def listenAndCreateNewSession():
    max_connections = 50
    while True:
        socket.listen(max_connections)
        clientSocket, addr = socket.accept()
        example = Oracle(key, flag, clientSocket, addr)
    return

class Oracle:
    def __init__(self, key, flag, clientSocket, addr):
        self.blockSize = 16
        self.key = key
        self.flag = flag
        
        thread = threading.Thread(target=self.run, args=(clientSocket,addr))
        thread.daemon = True
        thread.start()

    def run(self, clientSocket, addr):
        clientSocket.setblocking(0)
        clientSocket.send("What is your name?\n")
        while True:
            ready = select.select([clientSocket], [], [], 2)
            if ready[0]:
                data = clientSocket.recv(4096)
                message = '{"username": "' + data[:-1] + '", "flag": "' + flag + '"}'
                messageClear = '{"username": "' + data[:-1] + '", "flag": "redacted for security reasons"}'
                aes = AES.new(self.key, AES.MODE_ECB)
                padded = pad(message, self.blockSize)
                encrypted = bytearray(aes.encrypt(padded))
                ciphertext = ''.join('{:02x}'.format(x) for x in encrypted)

                clientSocket.send("Here is your encrypted message:\n" + ciphertext + "\n")
                clientSocket.send("DEBUG MODE ON\nLOGGING USER INPUT " + messageClear)
                break
        clientSocket.close()

main()