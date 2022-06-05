with open("babyste","rb") as f:
	data = f.read().decode("utf-8")
	data = list(data)
	new_text = ""
	for el in data:
		if not el.isascii():
			new_text += el
print(new_text)



