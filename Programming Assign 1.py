#Prints lines using chaned function calls

#prints 25 lines
def clear_screen():
    nine_lines()
    nine_lines()
    three_lines()
    three_lines()
    new_line()
#prints 9 lines
def nine_lines ():
    three_lines()
    three_lines()
    three_lines()
#prints 3 lines
def three_lines() :
    new_line()
    new_line()
    new_line()
#prints 1 line
def new_line ():
    print('.')

# main section 
    nine_lines()
    print()
    clear_screen()