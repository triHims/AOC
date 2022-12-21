import sys
import types
import io
import functools as ft


def setupInput() -> io.IOBase:
    readerObj: str
    if len(sys.argv) > 1:
        name = sys.argv[1]
    else:
        name = input("Enter file name: ")
    print("Reading the file ", name)
    readerObj = open(name, "r")
    return readerObj


def recurWithStr(lines: str, pos: int, arr: list):
    if lines[pos] == "[":
        # in a valid block
        loc = pos + 1
        while loc < len(lines):
            if lines[loc].isdigit():
                tupIntPos = parseNextInt(lines, loc)
                loc = tupIntPos[1]
                parsedInt = tupIntPos[0]
                arr.append(parsedInt)
                continue
            elif lines[loc] == "[":
                subArr = []
                loc = recurWithStr(lines, loc, subArr)
                arr.append(subArr)
            elif lines[loc] == "]":
                return loc
            loc += 1


def parseNextInt(line, pos) -> int:
    endPos = pos
    while endPos < len(line) and line[endPos].isdigit():
        endPos += 1
    return (int(line[pos:endPos]), endPos)


# Solution

# True , None , False


def compareObj(lst1, lst2):
    minLne = min(len(lst1), len(lst2))

    for i in range(minLne):
        if type(lst1[i]) == int and type(lst2[i]) == int:
            if lst1[i] < lst2[i]:
                return -1
            elif lst1[i] > lst2[i]:
                return 1
        elif type(lst1[i]) == list and type(lst2[i]) == list:
            res = compareObj(lst1[i], lst2[i])
            if res == 0:
                continue
            return res
        else:
            comp1 = lst1[i] if isinstance(lst1[i], list) else [lst1[i]]
            comp2 = lst2[i] if isinstance(lst2[i], list) else [lst2[i]]
            res = compareObj(comp1, comp2)
            if res == 0:
                continue
            return res

    if len(lst1) > len(lst2):
        return 1
    elif len(lst1) < len(lst2):
        return -1

    return 0


reader = setupInput()

lines = reader.readlines()

inputArr = []

for line in lines:
    
    if(line.endswith('\n')):
        line=line[:-1]
    if len(line.strip()) > 0:
        tempArr = []
        recurWithStr(line.strip(), 0, tempArr)
        inputArr.append(tempArr)


inputArr.append([[2]])
inputArr.append([[6]])

inputArr.sort(key=ft.cmp_to_key(compareObj))

in1=-1
in2=-1

for i in range(len(inputArr)):
    if(inputArr[i]==[[2]]):
        in1=i+1
        break
    
for i in range(len(inputArr)):
    if(inputArr[i]==[[6]]):
        in2=i+1
        break


print("ans is - %d"%(in1*in2))

reader.close()
