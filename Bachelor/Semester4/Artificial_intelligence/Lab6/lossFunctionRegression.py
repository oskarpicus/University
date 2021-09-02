def squareLoss(real: list, computed: list):
    return sum([(e1-e2)**2 for e1, e2 in zip(real, computed)])


print(squareLoss([1, 3], [2, 5]))
print(squareLoss([1], [1]))
print(squareLoss([1], [4]))