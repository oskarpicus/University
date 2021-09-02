def increment(number):
    """
    Method for incrementing (adding 1) to a number in binary
    :param number: list, represents a number in binary
    :return: list, representing number+1 in binary
    """
    transport = 1
    for counter, element in reversed(list(enumerate(number))):
        element = int(element)
        number[counter] = str((element+transport) % 2)
        transport = (element+transport)//2
        if transport == 0:
            break
    if transport != 0:
        number.insert(0, str(transport))


def generate(n):
    """
    Method for generating the first n numbers in binary
    :param n: int, n > 0
    :return: list of numbers from 1 to n in binary
    """
    result = []
    current = ["1"]
    for i in range(1,n+1):
        result.append(int("".join(current)))
        increment(current)
    return result


print(generate(10))
