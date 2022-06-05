import socket, base64, hashlib, pgpy

server_ip = "127.0.0.1"
server_port = 12345

def process_string(data_type, txt):
    if data_type == "base64":
        return str(base64.b64encode(txt.encode("utf-8")), "utf-8")
    if data_type == "sha1":
        return hashlib.sha1(txt.encode()).hexdigest()
    if data_type == "sha256":
        return hashlib.sha256(txt.encode()).hexdigest()
    if data_type == "md5":
        return hashlib.md5(txt.encode()).hexdigest()

def pgp_encrypt(pubkey, txt):
    key = pgpy.PGPKey()
    key.parse(pubkey)
    message = pgpy.PGPMessage.new(txt)
    enc_message = key.pubkey.encrypt(message)
    return str(enc_message)

s = socket.socket()		

s.connect((server_ip, server_port))

#hello
print(s.recv(1024).decode())

# DATA
data = s.recv(1024).decode().splitlines()
txt = process_string(data[0], data[1])
s.send(txt.encode())

# pubkey
s.recv(1024).decode()
pubkey = s.recv(1024).decode()
encrypted_msg = pgp_encrypt(pubkey, txt)
s.send(encrypted_msg.encode())
print(s.recv(1024).decode())


s.close()