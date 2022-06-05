# Solution

flag: `BtSCTF{0gRe5_4Re_L1Ke_c0N7a1NEr_1m4ge5_7Hey_h4vE_l4YER5}`

## How to solve it?

What you need to know for this challenge, is that containers are built of layers. Those layers are just *nix files which are merged into one image. You can use tools like `dive` to analyze it layer by layer but there is another way. Docker images can be saved as tar archive. Us it for your advantage:
```
docker save -o chall.tar challenge:latest
tar -xvf chall.tar
for d in `find ./ -mindepth 1 -type d`; do echo $d; cd $d; tar -xvf layer.tar; cd ..; done
grep -R "BtSCTF" ./ 2>/dev/null
```



