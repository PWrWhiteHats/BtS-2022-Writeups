def xor(text, key):
  kl = len(key)
  i = 0
  enc = ""
  for t in text:
    enc +=chr(t ^ key[i])
    i = (i+1) % kl
  return bytes(enc, encoding="utf-8")

xkey = b"XORthekey"
secret = b"\x1a;\x017<#\x107\x15!\x10\x1b+\x00Q\x1fV&,'a\x07[:X\x1dJ\x07)c\x18[P\x16"
print(xor(secret,xkey))