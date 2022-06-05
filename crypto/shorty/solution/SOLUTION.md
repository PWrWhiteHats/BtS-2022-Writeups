## Solution

Break the weak RSA key with any tool, then use it to decrypt symmetric encryption key for flag. When you have it, decrypt the flag using AES CBC with that decrypted key. (I created [solution.py](solution.py) file that does just that. You just need to break the key with something like RsaCtfTool or some website.) 