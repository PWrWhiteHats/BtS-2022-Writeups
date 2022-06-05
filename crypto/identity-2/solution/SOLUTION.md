# Solution

## Vulnerability

The same one as Identity because of wrong server side implementation and:

Value `x` has the same value twice indicating reuse of ephemeral.

## Exploit
r = random value chosen by client  
c = random value chosen by server  
From pcap we know values of `x, y, c`.  


  
Client sends to server `x` and `y = r + priv_key * c`.  
If client reuses value `r`(they do in [tcp-stream 1](tcp.stream_eq_1.png) and [tcp-stream 2](tcp.stream_eq_2.png)) we can recover their key by solving system of equations.  
`y1 = r + priv_key * c1 (mod prime_q)`  
`y2 = r + priv_key * c2 (mod prime_q)`  
substract eq2 from eq1  
`y1 - y2 = r + priv_key * c1 - r - (priv_key * c1)`  

`y1 - y2 = priv_key * c1 - priv_key * c2 `  

`y1 - y2 = priv_key * (c1 - c2)`  

`priv_key = (y1 - y2) / (c1 - c2) (mod prime_q)`  

to win the flag we connect to server to receive `c` and then send `x, y`  
`x = generator ** r (mod prime_q)`  
`y = r + priv_key * c (mod prime_q)`  
  
[solve.py](solve.py)
