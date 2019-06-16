import json

kjv_json = open("/home/yczeng/Documents/bibleapp2/app/src/main/assets/kjv.json")
kjv_string = kjv_json.read()
kjv_data = json.loads(kjv_string)

result = {}
for i in kjv_data:
	if i["book_id"] not in result:
		result[i["book_id"]] = 0

	if i["chapter"] >= result[i["book_id"]]:
		result[i["book_id"]] = i["chapter"]

f = open("app/src/main/assets/chapterNum.json", "w")
f.write(str(result))

# print(result)
print(len(result))