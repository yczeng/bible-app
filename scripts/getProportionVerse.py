import json

kjv_json = open("/home/yczeng/Documents/bibleapp2/app/src/main/assets/kjv.json")
kjv_string = kjv_json.read()
kjv_data = json.loads(kjv_string)

result = {}
temp_book_result = []

book_name = ""
for i in kjv_data:
	if i["book_id"] != book_name:

		if i["book_id"] not in result:
			result[i["book_id"]] = {}
		book = result[i["book_id"]]

		# all the processing for entire book should happen here
		chapter_name = -1
		chap_lens = {}
		temp_chapter_len = 0
		for each_chapter_blah in temp_book_result:
			# rest of total characters in a chapter count
			if each_chapter_blah["chapter"] != chapter_name:
				if chapter_name != -1:
					chap_lens[chapter_name] = temp_chapter_len
				temp_chapter_len = 0
				chapter_name = each_chapter_blah["chapter"]

			elif each_chapter_blah["chapter"] == chapter_name:
				temp_chapter_len += len(each_chapter_blah["text"])

		chap_lens[chapter_name] = temp_chapter_len

		# print(chap_lens)

		
		length_passed_so_far = 0
		chapter_name = -1
		for each_chapter_blah in temp_book_result:
			if each_chapter_blah["chapter"] != chapter_name:
				length_passed_so_far = 0
				chapter_name = each_chapter_blah["chapter"]

			# this means that it doesn't exist yet
			# i.e. chapter 1. len(book) = 0, so need to append
			# print(len(book), i["chapter"], book)

			if len(book) < each_chapter_blah["chapter"]:
				book[str(each_chapter_blah["chapter"])] = [round(length_passed_so_far / chap_lens[each_chapter_blah["chapter"]], 4)]
			else:
				# this grabs the array of string verses
				book[str(each_chapter_blah["chapter"])].append(round(length_passed_so_far / chap_lens[each_chapter_blah["chapter"]], 4))

			length_passed_so_far += len(each_chapter_blah["text"])


		temp_book_result = []
		book_name = i["book_id"]
		temp_book_result.append(i)

	elif i["book_id"] == book_name:
		temp_book_result.append(i)


f = open("app/src/main/assets/reformattedKjv3.json", "w")
f.write(str(result))

# print(result)
print((result))


# {"chapter":20,"verse":10,"text":"Then the disciples went away again unto their own home.","book_id":"John","book_name":"John"}
print(result["John"]["20"][9])