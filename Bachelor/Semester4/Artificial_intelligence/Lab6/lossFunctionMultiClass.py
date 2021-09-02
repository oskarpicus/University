from math import exp


def exponential(real, computed):
    total = 0
    for itR, itC in zip(real, computed):
        total += sum([exp(-realValue*computedValue) for realValue, computedValue in zip(itR, itC)])
    return total


print(exponential([[1, -1, -1]], [[3.2, 1.3, 0.2]]))
print(exponential([[1, -1, -1]], [[900, -2, -3]]))
print(exponential([[1, -1, -1], [-1, -1, 1], [-1, 1, -1]], [[1000, -100, -1], [2, 2, 2], [3, 9, 10]]))
print(exponential([[1, -1, -1]], [[1000, -100, -1]]))
print(exponential([[-1, 1, -1]], [[3, 9, 10]]))