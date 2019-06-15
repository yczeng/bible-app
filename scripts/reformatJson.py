import json

kjv_json = open("/home/yczeng/Documents/bibleapp2/app/src/main/assets/kjv.json")
kjv_string = kjv_json.read()
kjv_data = json.loads(kjv_string)

result = {}
for i in kjv_data:
	if i["book_id"] not in result:
		result[i["book_id"]] = {}

	book = result[i["book_id"]]

	if i["chapter"] not in book:
		book[i["chapter"]] = {}

	chapter = book[i["chapter"]]

	chapter[i["verse"]] = i["text"]

f = open("app/src/main/assets/reformattedKJV.json", "w")
f.write(str(result))

print(result)
print(len(result))

print(result["Gen"][1][3])