def xor(text, key):
  kl = len(key)
  i = 0
  enc = ""
  for t in text:
    enc +=chr(t ^ key[i])
    i = (i+1) % kl
  return bytes(enc, encoding="utf-8")

xkey = b"\x02\x03\x04\x05\x05\x04\x03\x02"
flag = open("../flag.txt").read()[:-1]
secret = bytearray(flag, "UTF-8")
print(xor(secret,xkey))
