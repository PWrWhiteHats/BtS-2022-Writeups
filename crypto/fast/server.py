import socket, random, base64, hashlib, pgpy
from pgpy.constants import PubKeyAlgorithm, KeyFlags, HashAlgorithm, SymmetricKeyAlgorithm, CompressionAlgorithm
from time import time
from os import fork

def genKeys():
    '''
    Generate PGP keys
    '''
    key = pgpy.PGPKey.new(PubKeyAlgorithm.RSAEncryptOrSign, 2048)
    uid = pgpy.PGPUID.new('BTS CTF')
    key.add_uid(uid, usage={KeyFlags.EncryptCommunications},
                hashes=[HashAlgorithm.SHA256, HashAlgorithm.SHA384, HashAlgorithm.SHA512, HashAlgorithm.SHA224],
                ciphers=[SymmetricKeyAlgorithm.AES256, SymmetricKeyAlgorithm.AES192, SymmetricKeyAlgorithm.AES128],
                compression=[CompressionAlgorithm.ZLIB, CompressionAlgorithm.BZ2, CompressionAlgorithm.ZIP, CompressionAlgorithm.Uncompressed])
    return key

def process_string(data_type, txt):
    '''
    Process string using given algorithm
    '''
    if data_type == "base64":
        return str(base64.b64encode(txt.encode("utf-8")), "utf-8")
    if data_type == "sha1":
        return hashlib.sha1(txt.encode()).hexdigest()
    if data_type == "sha256":
        return hashlib.sha256(txt.encode()).hexdigest()
    if data_type == "md5":
        return hashlib.md5(txt.encode()).hexdigest()

# listening network port
listen_port = 12345
data_types = ["md5", "sha1", "sha256", "base64"]

with open("static/pantadeusz.txt", "r") as f:
    lines = f.read().splitlines()

with open("flag.txt", "r") as f:
    flag = f.read()

s = socket.socket()		
s.bind(("0.0.0.0", listen_port))		
s.listen()

while True:
    line = random.choice(lines)
    c, addr = s.accept()
    if(fork()):
        continue
    print(f"Connection from {addr}")
    data_type = random.choice(data_types)
    correct_answer = process_string(data_type, line)
    c.send("[ BTS 2022 ] \"Fast\" challenge\nYou will get encoding/hashing type and text string in separate lines. Process this text using given algorithm.\nYou have only 2 seconds to do that. 3..2..1..go\n".encode())
    from time import sleep
    sleep(1)
    c.send(f"{data_type}\n{line}".encode())
    start = time()
    answer = c.recv(1024).decode()
    end = time()
    duration = end-start
    if duration >= 2:
        c.send("Too late, try again.".encode())
        c.close()
        continue
    if answer != correct_answer:
        c.send("Wrong answer. Bye!".encode())
        c.close()
        continue
    key = genKeys()
    c.send("Now encrypt it using this public key:\n".encode())
    c.send(str(key.pubkey).encode())
    start = time()
    txt = c.recv(2048).decode()
    end = time()
    duration = end-start
    if duration >= 2:
        c.send("Too late, try again.".encode())
        c.close()
        continue
    encrypted_msg = pgpy.PGPMessage.from_blob(txt)
    decrypted_msg = str(key.decrypt(encrypted_msg).message)
    if decrypted_msg == correct_answer:
        c.send(f"Here is the flag: {flag}".encode())
    else:
        c.send("Wrong answer".encode())
    c.close()
    quit()
