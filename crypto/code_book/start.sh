#!/bin/sh
ulimit -c 0
pip2 install pycryptodome
python2 oracle.py 0.0.0.0 4444