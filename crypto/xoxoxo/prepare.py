#!/usr/bin/python3

key = 178
content = open('flag.txt').read()[:-1]

encrypted = bytearray()
for char in content:
	encrypted.append(ord(char)^key)
print(encrypted.hex())