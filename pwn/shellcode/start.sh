#!/bin/sh
ulimit -c 0
while(true); do
tcpserver -u 1000 -g 1000 -t 50 -RHl0 0.0.0.0 2440 /home/ctf/chall
done
