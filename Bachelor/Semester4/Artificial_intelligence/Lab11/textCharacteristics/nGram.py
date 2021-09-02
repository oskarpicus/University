def convert(dataInput: list, vocabulary: list) -> list:
    result = []
    for example in dataInput:
        currentResult = []
        example = example[0].lower()
        for term in vocabulary:
            currentResult.append(example.count(term))
        result.append(currentResult)
    return result


def getVocabulary(dataInput: list, n: int) -> list:
    vocabulary = set()
    for example in dataInput:
        for phrase in example:
            words = phrase.split(' ')
            for index in range(0, len(words), n):
                miniPhrase = ""

                i = 0
                while i < n and index + i < len(words):
                    miniPhrase += ' ' + words[index + i]
                    i += 1

                miniPhrase = miniPhrase.lower().rstrip('\n.?!').replace(",", "").strip()
                if len(miniPhrase) > 1:
                    vocabulary.add(miniPhrase)
    return list(vocabulary)