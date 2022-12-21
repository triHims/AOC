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


class Sensor:
    pos: tuple
    beacon: tuple

    def __init__(self, pos, beacon) -> None:
        self.pos = pos
        self.beacon = beacon

    def __repr__(self) -> str:
        return f"Sensor(pos={self.pos}, beacon={self.beacon})"

    def __str__(self) -> str:
        return f"Sensor at {self.pos} with beacon at {self.beacon}"


def parseSensorLines(reader: io.IOBase):
    it1 = filter(
        lambda line: len(line) > 0,
        map(lambda line: line[:-1].strip(), reader.readlines()),
    )

    def takeLineReturnSensor(line):
        token = line.split(" ")
        # see x and y corrosponds to horizontal and vericlie - > so y is row and x is column
        return Sensor(
            pos=(int(token[3][:-1].split("=")[1]), int(token[2][:-1].split("=")[1])),
            beacon=(int(token[9].split("=")[1]), int(token[8][:-1].split("=")[1])),
        )

    return list(map(takeLineReturnSensor, it1))


def checkGridPosEmpty(row, col):
    if grid.get(row) == None or grid[row].get(col) == None:
        return True
    return False


def mutateInputsToGrid(grid, r1, c1, symbol):
    grid.setdefault(r1, {})[c1] = symbol


def manhattenDistance(x1, y1, x2, y2):
    return abs(x1 - x2) + abs(y1 - y2)


def combineRanges(rangeList: list):
    workList = rangeList[:]
    workList.sort()

    prev = workList[0][:]

    finalList = []

    for i in range(1, len(workList)):
        if prev[0] <= workList[i][0] <= prev[1]:
            prev[1] = max(prev[1], workList[i][1])
        else:
            finalList.append(prev)
            prev = workList[i][:]

    finalList.append(prev)
    return finalList

convergeDp={}
def convergeAllSensorsToRange(row):

    if(convergeDp.get(row)!=None):
        return convergeDp.get(row)

    targetSensors = [
        sensors_range[0]
        for sensors_range in sensors_range_map.items()
        if sensors_range[1] - abs(sensors_range[0].pos[0] - row) >= 0
    ]
    # print(targetSensors)

    rangeList = []

    for sensors in targetSensors:
        pos = sensors.pos
        leftManhattenDistance = sensors_range_map[sensors] - abs(pos[0] - row)
        leftrange = pos[1] - leftManhattenDistance
        rightrange = pos[1] + leftManhattenDistance
        rangeList.append([leftrange, rightrange])

    convergeDp[row]= combineRanges(rangeList)
    return convergeDp[row]



reader = setupInput()

grid = {}

sensorList = parseSensorLines(reader)

sensors_range_map = {
    sensor: manhattenDistance(
        sensor.pos[0], sensor.pos[1], sensor.beacon[0], sensor.beacon[1]
    )
    for sensor in sensorList
}
beaconList = [sensor.beacon for sensor in sensorList]


def part1(row):
    # get all the senosrs which are in the mhd
    filteredBeaconForRow = {beacon for beacon in beaconList if beacon[0] == row}
    filteredRangeList = convergeAllSensorsToRange(row)

    beaconsToSubtract = 0

    for b in filteredBeaconForRow:
        for range in filteredRangeList:
            if range[0]<=b[1] <=range[1]:
                beaconsToSubtract+=1
     
    emptyPos = sum(ranges[1]-ranges[0]+1 for ranges in filteredRangeList)
    print(f"For the {row} empty pos are {emptyPos-beaconsToSubtract}")

def part2(limitsLow,limitsHigh):
    '''
    Now get all the sesors and travese their maximum parameters, and find if any cordinate is not in the range, 
    point to note is the most probable points will be point just outside the parameter

    '''

    for sensor in sensorList:
        pos = sensor.pos;
        max_range = sensors_range_map[sensor]

        for delta in range(0,max_range+1):
            for signx,signy in [[-1,-1],[-1,1],[1,-1],[1,1]]:
                valx = pos[0]+signx*delta
                valy = pos[1]+signy*(max_range+1-delta)
                if(valx < limitsLow[0] or valx > limitsHigh[0] or valy < limitsLow[1] or valy > limitsHigh[1]):
                    continue;

                filteredRangeList = convergeAllSensorsToRange(valx)

                found = False

                for ranges in filteredRangeList:
                    if ranges[0]<= valy <= ranges[1]:
                        found = True
                        break

                if(not found):
                    return (valx,valy)
            
            




# targetRow = 10 # for input.txt
targetRow = 2000000
# part1(targetRow)
# pos = part2((0,0),(20,20)) #test
pos = part2((0,0),(4000000,4000000)) #test

print("Ans is ")
print(pos)
print(pos[1]*4000000+pos[0])

reader.close()
