#!/usr/bin/python3
from string import printable

encrypted = bytes.fromhex("f0c6e1f1e6f4c982dc81edd0cb85d7edc286d6edc086c081decbedc582c0d9c1cf")
for key in range(256):
	decrypted = ""
	isCorrect = True
	for byte in encrypted:
		decryptedChar = chr(byte^key)
		if decryptedChar not in printable:
			isCorrect = False
			break
		decrypted += decryptedChar
	if isCorrect:
		print("Possible flag:", decrypted, "for key", key)