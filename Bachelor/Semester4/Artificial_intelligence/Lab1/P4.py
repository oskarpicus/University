def singleWords(sequence):
    """
    Method for finding the words that appear only once in a string
    :param sequence: string
    :return: list of words that appear only once in sequence
    """
    words = {}
    result = []
    for w in sequence.split(" "):
        if w in words:
            words[w] += 1
        else:
            words[w] = 1
    for pair in words.items():
        if pair[1] == 1:
            result.append(pair[0])
    return result


assert singleWords("ana are ana are mere rosii ana") == ['mere','rosii']
assert singleWords("a b c d") == ["a","b","c","d"]