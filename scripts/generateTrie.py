import json, re

kjv_json = open("/home/zackpi/Documents/Work/bible-app/app/src/main/assets/kjv.json")
kjv_string = kjv_json.read()
kjv_data = json.loads(kjv_string)

trie = {"pl": []}
for verse_data in kjv_data:
	verse = verse_data["text"].split(" ")
	char_pos = 0

	for mixed_word in verse:
		word = re.sub("[!@#$,.?;:'()-]", "", mixed_word.lower())
		if len(word) == 0:
			continue

		# Traverse a trie branch
		_trie = trie
		for char in word:
			if char in _trie:
				_trie = _trie[char]
			else:
				_trie[char] = {"pl": []}
				_trie = _trie[char]

		_trie["pl"].append({"b": verse_data["book_id"], "c": verse_data["chapter"], "v": verse_data["verse"], "i": char_pos})
		#print(word, {"book_id": verse_data["book_id"], "chapter": verse_data["chapter"], "verse": verse_data["verse"], "query_pos": char_pos})

		char_pos += len(mixed_word) + 1

outfile = open("app/src/main/assets/searchTrie.json", "w")
outfile.write(str(trie))
