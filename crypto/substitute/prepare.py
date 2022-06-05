#!/usr/bin/python3
import codecs
import shutil
from string import ascii_lowercase
content = open('flag.txt').read()[:-1]

prefix = "The cat is a domestic species of small carnivorous mammal. It is the only domesticated species in the family Felidae and is often referred to as the domestic cat to distinguish it from the wild members of the family. A cat can either be a house cat, a farm cat or a feral cat. The latter ranges freely and avoids human contact. "
postfix = "Domestic cats are valued by humans for companionship and their ability to kill rodents. The cat is similar in anatomy to the other felid species. It has a strong flexible body, quick reflexes, sharp teeth and retractable claws adapted to killing small prey. Its night vision and sense of smell are well developed. "
content = prefix + content + postfix
coded = codecs.encode(content, 'rot_13')
print(coded)

lowercaseLetters = ascii_lowercase
inputDirectory = "pics/"
outputDirectory = "static/cats/"
extension = ".jpg"

mapping = {}
mapping[","] = "comma"
mapping["."] = "dot"
mapping["_"] = "underscore"
mapping[" "] = "space"
mapping["{"] = "left_bracket"
mapping["}"] = "right_bracket"
i = 1
for char in lowercaseLetters:
    mapping[char] = str(i)
    i += 1
i = 1
for char in coded:
    inputFileNameBase = mapping[char.lower()]
    shutil.copyfile(inputDirectory + inputFileNameBase + extension, outputDirectory + str(i) + extension)
    i += 1