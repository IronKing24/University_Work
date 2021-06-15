# returns whether the remainder is zero or not
def is_divisible(x, y):
    return x % y == 0

#check if a is (a) power of (b)
def is_power(a, b):
    if a < 0:
        #checks for negative inputs
        return -1
    elif b == 1 and a != 1:
        #returns false only if a isn't so not to stand as counteraction to the next branch
       return False
    elif a == b:
        #a number to the power of one is the same number
        return True    
    elif a == 1:
        #a number to the power of zero is one
        return True
    elif is_divisible(a,b) and is_power(a/b,b):
        #recursive step   
        return True
    else:
        #default 
        return False

print("is_power(10, 2) returns: ", is_power(10, 2))
print("is_power(27, 3) returns: ", is_power(27, 3))
print("is_power(1, 1) returns: ", is_power(1, 1))
print("is_power(10, 1) returns: ", is_power(10, 1))
print("is_power(3, 3) returns: ", is_power(3, 3))
