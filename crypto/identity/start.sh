#!/bin/sh
while(true); do
tcpserver -u 1000 -g 1000 -t 50 -RHl0 0.0.0.0 2439 python3 chal.py
done
