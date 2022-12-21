import types, io, sys


def setupInput() -> io.IOBase:
    readerObj: io.IOBase
    if len(sys.argv) > 1:
        name = sys.argv[1]
    else:
        name = input("Enter file name: ")
    print("Reading the file ", name)
    readerObj = open(name, "r")
    return readerObj


def mutateInputsToGrid(r1, c1, r2, c2, grid):
    """
    pass prev(row,col),current(row,col) it will fill grid
    """

    if r1 != r2:
        if r1 > r2:
            r1, r2 = r2, r1
        for i in range(r1, r2 + 1):
            sg = grid.setdefault(i, {})
            sg[c1] = "#"
    elif c1 != c2:
        if c1 > c2:
            c1, c2 = c2, c1
        for i in range(c1, c2 + 1):
            sg = grid.setdefault(r1, {})
            sg[i] = "#"


def produceUnits(grid):
    if checkGridPosEmpty(0,500)==False:
        return False

    pos = [0, 500]  # [row,col]
    mov = True

    while mov:

        if checkGridPosEmpty(pos[0] + 1, pos[1]):
            pos[0] = pos[0] + 1
        elif checkGridPosEmpty(pos[0] + 1, pos[1] - 1):
            pos[0], pos[1] = pos[0] + 1, pos[1] - 1
        elif checkGridPosEmpty(pos[0] + 1, pos[1] + 1):
            pos[0], pos[1] = pos[0] + 1, pos[1] + 1
        else:
            mov = False

    grid.setdefault(pos[0], {})[pos[1]] = "o"
    return True


reader = setupInput()

# logic (498,6)-> 6-> row , 498 is column to make it easier for mind to warp around
#   489|499|590|591|592|593|489|489|489|
# 0    |   |   |   |   |   |   |   |   |
# 1    |   |   |   |   |   |   |   |   |
# 2    |   |   |   |   |   |   |   |   |
# 3    |   |   |   |   |   |   |   |   |
# 4    |   |   |   |   |   |   |   |   |

grid = {}

limiterR = 0


def checkGridPosEmpty(row, col):
    if row >= limiterR:
        return False
    if grid.get(row) == None or grid[row].get(col) == None:
        return True
    return False


while 1:
    line = reader.readline()
    if len(line) == 0:
        break
    inputs = list(
        map(
            lambda stng: (
                int(stng.strip().split(",")[0]),
                int(stng.strip().split(",")[1]),
            ),
            line[:-1].strip().split("->"),
        )
    )

    limiterR = max(limiterR, max(i[1] for i in inputs))

    prev = inputs[0]
    for i in range(1, len(inputs)):
        tupIp = inputs[i]
        # we know that inputs are (c1,r1) -> so assign properly
        mutateInputsToGrid(prev[1], prev[0], tupIp[1], tupIp[0], grid)
        prev = tupIp


limiterR+=2 #according to question

units = 1

ans = 0

while 1:
    if produceUnits(grid) == True:
        ans += 1
    else:
        break
    units += 1

print(ans)

def printGrid(grid:dict):
    minRow = 999999
    maxRow=0
    minCol=9999
    maxCol=0

    for i in grid.keys():
        minRow = min(minRow,i)
        maxRow = max(maxRow,i)
        for j in grid[i].keys():
            minCol = min(minCol,j)
            maxCol = max(maxCol,j)
    
    for i in range(minRow,maxRow+1):
        for j in range(minCol,maxCol+1):
            try:
                print(grid[i][j],end='')
            except KeyError:
                print('.',end='')
        print()

    for j in range(minCol,maxCol+1):
        print("#",end='')
    print()
    






printGrid(grid)

reader.close()
