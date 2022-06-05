#!/usr/bin/python2

from Crypto.Util.Padding import pad
import nclib
import base64
from os import urandom
from string import printable

#ip = "ctf.whitehats.pwr.edu.pl"
ip = "127.0.0.1"
#port = 30204
port = 4444

blockSize = 16

def askOracle(question):
    flag = True
    while(flag):
        try:
            flag = False
            nc = nclib.Netcat(connect=(ip, port))
            nc.recv_until("?\n")
            nc.send(question)
            response = nc.recv_all()
            nc.close()
            return response.split("\n")[1]
        except Exception as inst:
            print inst
            flag = True

prefix = (blockSize - 14) * "A" # number of bytes to pad the beginning
middle = ', "flag": "BtSC'

flag = "BtSC"
blockNumber = 1
blockHexEncodedSize = 2*blockSize
charsFound = 0
charsFoundInBlock = 0

while flag[-1] != "}":
    name = prefix
    name += middle
    howManyToPad = blockSize - charsFoundInBlock - 1
    variableBlock = howManyToPad * "C"
    testflag = False
    for char in printable:
    	newName = name + char + variableBlock + "\n"
    	response = askOracle(newName)
    	firstBlock = response[blockHexEncodedSize:blockHexEncodedSize*2]
    	secondBlock = response[(blockNumber+2)*blockHexEncodedSize:(blockNumber+3)*blockHexEncodedSize]
    	if firstBlock == secondBlock:
    		flag += char
    		testflag = True
    		middle = middle[1:] + char
    		charsFoundInBlock += 1
    		if charsFoundInBlock >= blockSize:
    			charsFoundInBlock = 0
    			blockNumber += 1
    		charsFound += 1
    		break
    if not testflag:
    	break
    print(flag)
