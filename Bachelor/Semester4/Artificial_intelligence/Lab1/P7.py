def greatestK(nums, k):
    """
    Method for finding the k-greatest element in a sequence of numbers
    :param nums: list of ints
    :param k: int, k < len(nums)
    :return: the k-greatest number in nums
    """
    nums = list(dict.fromkeys(nums))  # transform into set
    nums.sort()
    return nums[-k]


assert greatestK([7,4,6,3,9,1],2) == 7
assert greatestK([1,2,3,4],1) == 4
assert greatestK([1,0,4,5,2],3) == 2
assert greatestK([1,0,4,5,2],1) == 5
assert greatestK([1,2,-4],3) == -4
assert greatestK([1,0,4,5,5,2],3) == 2
assert greatestK([1,0,4,5,5,2],1) == 5
assert greatestK([1,0,4,5,5,2],1) == 5
