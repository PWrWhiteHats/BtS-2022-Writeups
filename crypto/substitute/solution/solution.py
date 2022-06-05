#!/usr/bin/python3
import shutil
from string import ascii_lowercase
from os import listdir
from os.path import isfile, join
from filecmp import cmp

cryptoCatsPath = "../static/cats/"
justCatsPath = "../static/some_cats/"
otherPicsPath = "other_pics/"

justCatsFiles = [f for f in listdir(justCatsPath) if isfile(join(justCatsPath, f))]
otherPicsFiles = [f for f in listdir(otherPicsPath) if isfile(join(otherPicsPath, f))]

files = []
for file in justCatsFiles:
	files.append(justCatsPath + file)
for file in otherPicsFiles:
	files.append(otherPicsPath + file)

mapping = {}
mapping["comma"] = ","
mapping["dot"] = "."
mapping["underscore"] = "_"
mapping["space"] = " "
mapping["left_bracket"] = "{"
mapping["right_bracket"] = "}"
i = 1
for char in ascii_lowercase:
    mapping[str(i)] = char
    i += 1

text = ""
for number in range(1, 1090):
	filePath = cryptoCatsPath + str(number) + ".jpg"
	for baseFile in files:
		if cmp(filePath, baseFile):
			fileName = baseFile.split("/")[-1].split(".")[0]
			text += mapping[fileName]
			break
print(text)