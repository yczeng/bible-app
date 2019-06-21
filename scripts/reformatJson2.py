import json

kjv_json = open("/home/yczeng/Documents/bibleapp2/app/src/main/assets/kjv.json")
kjv_string = kjv_json.read()
kjv_data = json.loads(kjv_string)

result = {}
for i in kjv_data:
	if i["book_id"] not in result:
		result[i["book_id"]] = {}

	# this is an array of arrays of strings
	# each array in book is a chapter's verses
	book = result[i["book_id"]]

	# this means that it doesn't exist yet
	# i.e. chapter 1. len(book) = 0, so need to append
	# print(len(book), i["chapter"], book)
	if len(book) < i["chapter"]:
		book[str(i["chapter"])] = [i["text"]]

	# chapter already exists
	else:
		# this grabs the array of string verses
		book[str(i["chapter"])].append(i["text"])

f = open("app/src/main/assets/reformattedKjv3.json", "w")
f.write(str(result))

# print(result)
print(len(result))


# {"chapter":20,"verse":10,"text":"Then the disciples went away again unto their own home.","book_id":"John","book_name":"John"}
print(result["John"]["20"][9])