#!/bin/sh
while(true); do
tcpserver -u 1000 -g 1000 -t 50 -RHl0 0.0.0.0 2438 python3 server.py
done
