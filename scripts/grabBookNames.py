import json

kjv_json = open("/home/yczeng/Documents/bibleapp2/app/src/main/assets/kjv.json")
kjv_string = kjv_json.read()
kjv_data = json.loads(kjv_string)

book_names = []
for i in kjv_data:
	if i["chapter"] not in book_names:
		book_names.append(i["chapter"])

print(book_names)