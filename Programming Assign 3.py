from math import sqrt

#
def my_sqrt(a):
     x = a
     while True:
          y = (x + a/x) / 2.0
          if y == x:
               return y
          x = y 

#
def test_sqrt():
     a = 1
     while a <= 25:
          x = my_sqrt(a)
          y = sqrt(a)
          diff = abs(x-y)
          print("a = " + str(a) + "| my_sqrt(a) =" + str(x) + "| math.sqrt(a) =" + str(y) + "| diff =" + str(diff))
          a +=1
          

test_sqrt()
   
