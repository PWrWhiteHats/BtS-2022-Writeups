# Solution

The flag: `BtSCTF{m1n3c24f7_15_fun}`

## Encryption

```
flag[0] == 109                                                 # m
flag[1] | 18 == 51                                             # 1
flag[2] >> 2 == 27                                             # n
flag[3] ^ 12 == 63                                             # 3
flag[4] << 5 == 3168                                           # c
(flag[5] + 19) ^ 33 == 100                                     # 2
(flag[6] - 14) << 2 == 152                                     # 4
flag[7] << 3 - flag[2] == 706                                  # f
flag[8] == 55                                                  # 7
flag[9] ^ 1382 == 1337                                         # _
flag[10] + flag[3] == 100                                      # 1
flag[11] & 1905 == 49                                          # 5
(flag[12] | 22) & 2022 == 70                                   # _
flag[13] + 42 == 144                                           # f
flag[14] - 2137 == -2020                                       # u
(((flag[15] + 114100107) | 5149) & 46) - 99111109 == -99111065 # n
```

## Solving

### Basic info

To understand the syntax, read about [datapacks](https://minecraft.fandom.com/wiki/Data_pack), [execute](https://minecraft.fandom.com/wiki/Commands/execute) and [scoreboard](https://minecraft.fandom.com/wiki/Commands/scoreboard).

`tick.mcfunction` controls the control flow in the app. The `cPdzfSjXlt` variable is the PC register. In fact, the functions order is not scrambled, it's just as it's been parsed.

`load.mcfunction` creates registers, temp arrays and RAM (line 256+). The RAM is constructed using [octrees](https://en.wikipedia.org/wiki/Octree) with 4 layers. It's used to store ASCII strings and player's input. Before each read/write the pointer is converted to octal, so that each digit represents one layer.

`input/` and `syscalls/` handle stdin/stdout.

Bitwise operations (OR/AND/XOR) are implemented by converting the variables to binary, calculating each bit and then converting back to decimal. Makes them really easy to spot.

Knowing all that, we can begin reversing it.

### Reversing

Let's take the first obfuscated function from tick: `app/psdtbyzhga`. It's the first character's check.

```
1. scoreboard players set btsctf cPdzfSjXlt 0

2. scoreboard players operation btsctf kzxgsiYNYa = btsctf TSZSLZfLkO
3. scoreboard players add btsctf kzxgsiYNYa

4. scoreboard players set btsctf JNjeLPTvbO 8
5. scoreboard players operation btsctf kzxgsiYNYa_0 = btsctf kzxgsiYNYa
6. scoreboard players operation btsctf kzxgsiYNYa_0 %= btsctf JNjeLPTvbO
7. scoreboard players operation btsctf kzxgsiYNYa /= btsctf JNjeLPTvbO
8. scoreboard players operation btsctf kzxgsiYNYa_1 = btsctf kzxgsiYNYa
9. scoreboard players operation btsctf kzxgsiYNYa_1 %= btsctf JNjeLPTvbO
10. scoreboard players operation btsctf kzxgsiYNYa /= btsctf JNjeLPTvbO
11. scoreboard players operation btsctf kzxgsiYNYa_2 = btsctf kzxgsiYNYa
12. scoreboard players operation btsctf kzxgsiYNYa_2 %= btsctf JNjeLPTvbO
13. scoreboard players operation btsctf kzxgsiYNYa /= btsctf JNjeLPTvbO
14. scoreboard players operation btsctf kzxgsiYNYa_3 = btsctf kzxgsiYNYa
15. scoreboard players operation btsctf kzxgsiYNYa_3 %= btsctf JNjeLPTvbO
16. scoreboard players operation btsctf kzxgsiYNYa /= btsctf JNjeLPTvbO

17. execute if score btsctf kzxgsiYNYa_3 matches 0 run data modify storage btsctf BzouXBsnGS1 set from storage btsctf MyjDJCPXPH[0]
18. execute if score btsctf kzxgsiYNYa_3 matches 1 run data modify storage btsctf BzouXBsnGS1 set from storage btsctf MyjDJCPXPH[1]
19. execute if score btsctf kzxgsiYNYa_3 matches 2 run data modify storage btsctf BzouXBsnGS1 set from storage btsctf MyjDJCPXPH[2]
20. execute if score btsctf kzxgsiYNYa_3 matches 3 run data modify storage btsctf BzouXBsnGS1 set from storage btsctf MyjDJCPXPH[3]
21. execute if score btsctf kzxgsiYNYa_3 matches 4 run data modify storage btsctf BzouXBsnGS1 set from storage btsctf MyjDJCPXPH[4]
22. execute if score btsctf kzxgsiYNYa_3 matches 5 run data modify storage btsctf BzouXBsnGS1 set from storage btsctf MyjDJCPXPH[5]
23. execute if score btsctf kzxgsiYNYa_3 matches 6 run data modify storage btsctf BzouXBsnGS1 set from storage btsctf MyjDJCPXPH[6]
24. execute if score btsctf kzxgsiYNYa_3 matches 7 run data modify storage btsctf BzouXBsnGS1 set from storage btsctf MyjDJCPXPH[7]
25. execute if score btsctf kzxgsiYNYa_2 matches 0 run data modify storage btsctf BzouXBsnGS2 set from storage btsctf BzouXBsnGS1[0]
26. execute if score btsctf kzxgsiYNYa_2 matches 1 run data modify storage btsctf BzouXBsnGS2 set from storage btsctf BzouXBsnGS1[1]
27. execute if score btsctf kzxgsiYNYa_2 matches 2 run data modify storage btsctf BzouXBsnGS2 set from storage btsctf BzouXBsnGS1[2]
28. execute if score btsctf kzxgsiYNYa_2 matches 3 run data modify storage btsctf BzouXBsnGS2 set from storage btsctf BzouXBsnGS1[3]
29. execute if score btsctf kzxgsiYNYa_2 matches 4 run data modify storage btsctf BzouXBsnGS2 set from storage btsctf BzouXBsnGS1[4]
30. execute if score btsctf kzxgsiYNYa_2 matches 5 run data modify storage btsctf BzouXBsnGS2 set from storage btsctf BzouXBsnGS1[5]
31. execute if score btsctf kzxgsiYNYa_2 matches 6 run data modify storage btsctf BzouXBsnGS2 set from storage btsctf BzouXBsnGS1[6]
32. execute if score btsctf kzxgsiYNYa_2 matches 7 run data modify storage btsctf BzouXBsnGS2 set from storage btsctf BzouXBsnGS1[7]
33. execute if score btsctf kzxgsiYNYa_1 matches 0 run data modify storage btsctf BzouXBsnGS3 set from storage btsctf BzouXBsnGS2[0]
34. execute if score btsctf kzxgsiYNYa_1 matches 1 run data modify storage btsctf BzouXBsnGS3 set from storage btsctf BzouXBsnGS2[1]
35. execute if score btsctf kzxgsiYNYa_1 matches 2 run data modify storage btsctf BzouXBsnGS3 set from storage btsctf BzouXBsnGS2[2]
36. execute if score btsctf kzxgsiYNYa_1 matches 3 run data modify storage btsctf BzouXBsnGS3 set from storage btsctf BzouXBsnGS2[3]
37. execute if score btsctf kzxgsiYNYa_1 matches 4 run data modify storage btsctf BzouXBsnGS3 set from storage btsctf BzouXBsnGS2[4]
38. execute if score btsctf kzxgsiYNYa_1 matches 5 run data modify storage btsctf BzouXBsnGS3 set from storage btsctf BzouXBsnGS2[5]
39. execute if score btsctf kzxgsiYNYa_1 matches 6 run data modify storage btsctf BzouXBsnGS3 set from storage btsctf BzouXBsnGS2[6]
40. execute if score btsctf kzxgsiYNYa_1 matches 7 run data modify storage btsctf BzouXBsnGS3 set from storage btsctf BzouXBsnGS2[7]
41. execute if score btsctf kzxgsiYNYa_0 matches 0 store result score btsctf FwSeoleidx run data get storage btsctf BzouXBsnGS3[0]
42. execute if score btsctf kzxgsiYNYa_0 matches 1 store result score btsctf FwSeoleidx run data get storage btsctf BzouXBsnGS3[1]
43. execute if score btsctf kzxgsiYNYa_0 matches 2 store result score btsctf FwSeoleidx run data get storage btsctf BzouXBsnGS3[2]
44. execute if score btsctf kzxgsiYNYa_0 matches 3 store result score btsctf FwSeoleidx run data get storage btsctf BzouXBsnGS3[3]
45. execute if score btsctf kzxgsiYNYa_0 matches 4 store result score btsctf FwSeoleidx run data get storage btsctf BzouXBsnGS3[4]
46. execute if score btsctf kzxgsiYNYa_0 matches 5 store result score btsctf FwSeoleidx run data get storage btsctf BzouXBsnGS3[5]
47. execute if score btsctf kzxgsiYNYa_0 matches 6 store result score btsctf FwSeoleidx run data get storage btsctf BzouXBsnGS3[6]
48. execute if score btsctf kzxgsiYNYa_0 matches 7 store result score btsctf FwSeoleidx run data get storage btsctf BzouXBsnGS3[7]

49. scoreboard players set btsctf ddoSCDzSad 109
50. scoreboard players set btsctf cPdzfSjXlt 23
51. execute if score btsctf FwSeoleidx = btsctf ddoSCDzSad run scoreboard players set btsctf cPdzfSjXlt 3
```

Line 1: Set PC to 0 - to stop executing if it's the end. \
Lines 2-3: Set the pointer to the correct position. \
Lines 4-16: Convert the pointer from decimal to octal. \
Lines 17-48: Using the octal representation of the pointer, read the character. \
Lines 49-51: If the character is 109, set PC to 3 (next character check), else set PC to 23 (quit).

So, the first character has to be 109, or 'm' in ASCII and if that's the case, the checking continues with `app/tdtxkgviwm`.
