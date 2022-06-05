import random
unicode_dict= {
"a": "④",
"b": "ｂ",
"c": "ⅽ",
"d": "ḋ",
"e": "③",
"f": "ḟ",
"g": "ġ",
"h": "ḣ",
"i": "₁",
"j": "ⅉ",
"k": "ḱ",
"l": "ľ",
"m": "ṁ",
"n": "ň",
"o": "ô",
"p": "ṗ",
"q": "ｑ",
"r": "ȓ",
"s": "š",
"t": "ṫ",
"u": "ü",
"v": "ṿ",
"w": "ẃ",
"x": "ẍ",
"y": "ȳ",
"z": "ž",
"_": "＿",
"{": "｛",
"}": "｝",
"1": "₁",
"3": "③",
"4": "④",
"5": "⑤"
}

def change(a):
	return unicode_dict[a.lower()]
	
flag = open("../flag.txt").read()[:-1]

new_text = ""
for el in flag.lower():
	new_text += change(el)
print("Hidden flag in file:", new_text)

fr = open("data.txt","r")
data = fr.read()
fr.close()

data = list(data)
chunks = len(data)//len(flag)
for i,el in enumerate(flag):
	pos = i*chunks+random.randint(0,100)
	data[pos] = change(el)

fw = open("babyste","wb")
fw.write("".join(data).encode("utf-8"))
fw.close()






