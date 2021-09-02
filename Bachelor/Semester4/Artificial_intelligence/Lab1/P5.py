def duplicate(nums):
    """
    Method for finding the duplicate number in a sequence
    :param nums: list of numbers in {1,2,...,n-1}, len(nums)=n
    :return: x in nums, x duplicate
    """
    frequencies = {}
    result = None
    for number in nums:
        if number in frequencies:
            return number
        frequencies.update({number:1})
    return result


assert duplicate([1,2,3,4,2]) == 2
assert duplicate([1,3,2,1]) == 1
assert duplicate([1,2,3,1]) == 1
assert duplicate([3,2,1,4,3]) == 3
assert duplicate([4,3,2,1,1]) == 1
assert duplicate([1,2,3]) is None
