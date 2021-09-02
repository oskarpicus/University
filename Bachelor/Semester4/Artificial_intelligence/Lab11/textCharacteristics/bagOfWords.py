def convert(dataInput: list, vocabulary: list) -> list:
    result = []
    for example in dataInput:
        currentResult = []
        example = example[0].lower().split(' ')
        for term in vocabulary:
            currentResult.append(example.count(term))
        result.append(currentResult)
    return result


def getVocabulary(dataInput: list) -> list:
    vocabulary = set()
    for example in dataInput:
        for phrase in example:
            for word in phrase.split(' '):
                word = word.lower().rstrip('\n.?!')
                if len(word) > 1:
                    vocabulary.add(word)
    return list(vocabulary)
