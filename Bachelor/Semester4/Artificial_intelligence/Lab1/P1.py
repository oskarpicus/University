def lastWord(sequence):
    """
    Method for finding the last word in a string (alphabetical order)
    :param sequence: string
    :return: the last alphabetical word in sequence
    """
    words = sequence.split(" ")
    last = ""
    for word in words:
        lower = word.lower()
        if last < lower:
            last = lower
    return last


assert lastWord("Ana are mere rosii si galbene") == "si"
assert lastWord("astazi este luni") == "luni"
assert lastWord("a b za b zb") == "zb"
