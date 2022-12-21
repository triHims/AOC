import types, io, sys

MAX_INT = 9999999
sys.setrecursionlimit(10**5)

def setupInput() -> io.IOBase:
    readerObj: io.IOBase
    if len(sys.argv) > 1:
        name = sys.argv[1]
    else:
        name = input("Enter file name: ")
    print("Reading the file ", name)
    readerObj = open(name, "r")
    return readerObj


def processInput(reader):
    nodeFlowMap={}
    nodeAdjMap={}

    lines=reader.readlines()

    for line in lines:
        splitByColon = line[:-1].split(";")


        splitFirstPartBySpace=splitByColon[0].split(" ")

        nodeName = splitFirstPartBySpace[1]

        rate=int(splitFirstPartBySpace[4].split('=')[1])


        targetNodes=splitByColon[1].strip().replace(',','').split(" ")[4:]

        nodeFlowMap[nodeName]=rate
        nodeAdjMap[nodeName]=targetNodes

    return (nodeFlowMap,nodeAdjMap)



def traverseComplete(currentPoint,timeLeft,visitedList):
    pass   

def allPairsShortestPath(g):
    """Return distance structure as computed"""

    dist = {}
    for u in g:
        dist[u] = {}
        for v in g:
            dist[u][v] = 10000000 

        dist[u][u] = 0

        for v in g[u]:
            dist[u][v] = 1

    for mid in g:
        for u in g:
            for v in g:

                newlen = dist[u][mid] + dist[mid][v]
                if newlen < dist[u][v]:
                    dist[u][v] = newlen

    return dist
    
    
Deeepe = {}    

class CustomVisiter:
    def __init__(self,*args) -> None:

        if(len(args)==0):
            self.keys=[]
            self.mapper={}
            self.vals=0
            return


        copyLst = list(args[0])
        copyLst.sort()
        self.keys=copyLst
        self.mapper={}

        for i in range(len(copyLst)):
            self.mapper[copyLst[i]]=i

        self.vals = 1<<(len(copyLst)+2)
    
    
    
    def markVisit(self,key:str):
        loc = self.mapper[key]
        self.vals=self.vals|1<<loc

    def getVisit(self,key:str):
        loc = self.mapper[key]
        out=self.vals&(1<<loc)
        return True if out>0 else False
        
    
    def getHash(self):
        return self.vals
    
    def copy(self):
        neObj = CustomVisiter()
        neObj.keys=self.keys
        neObj.mapper=self.mapper
        neObj.vals=self.vals
        return neObj


        


def processNodes(nodeBoy,nodeEle,timeBoy,timeEle,visit:CustomVisiter):

    # print(f"we have nodeBoy={nodeBoy}  at time {timeBoy}\n nodeEle={nodeEle}  at time {timeEle} with \n dict {bin(visit.getHash())}\n\n" )

    if(Deeepe.get((nodeBoy,nodeEle,timeBoy,timeEle,visit.getHash()))!=None):
        return Deeepe.get((nodeBoy,nodeEle,timeBoy,timeEle,visit.getHash()))

    def process(timeBoy,timeEle):
        result=0
        visit.markVisit(nodeBoy)
        visit.markVisit(nodeEle)
        if(timeBoy>0 and  nodeFlowMap[nodeBoy]>0):
            result+=(timeBoy-1)*nodeFlowMap[nodeBoy]
            timeBoy-=1
        
        if(timeEle>0 and nodeFlowMap[nodeEle]>0):
            result+=(timeEle-1)*nodeFlowMap[nodeEle]
            timeEle-=1

        ans = 0
        if(timeBoy>0 or timeEle>0):
            localReachBoy = apsp[nodeBoy]
            localReachEle = apsp[nodeEle]
            localCostBoy = {locNode: ((timeBoy-1-reach)*nodeFlowMap[locNode]) for locNode,reach in localReachBoy.items() if not visit.getVisit(locNode) }
            localCostEle = {locNode: ((timeEle-1-reach)*nodeFlowMap[locNode]) for locNode,reach in localReachEle.items() if not visit.getVisit(locNode) }
            for locEleNode,flowEle in localCostEle.items():
                for locBoyNode,flowBoy in localCostBoy.items():
                    if(locBoyNode==locEleNode):
                        continue
                    nextBoyNode=nodeBoy
                    nextEleNode=nodeEle
                    nextTimeBoy=timeBoy
                    nextTimeEle=timeEle
                    if(timeBoy>0 and flowBoy>0 and locBoyNode!=nodeBoy):
                        nextBoyNode=locBoyNode
                        nextTimeBoy=timeBoy-localReachBoy[locBoyNode]

                    if(timeEle>0 and flowEle>0 and locEleNode!=nodeEle):
                        nextEleNode=locEleNode
                        nextTimeEle=timeEle-localReachEle[locEleNode]
                    ans = max(ans,processNodes(nextBoyNode,nextEleNode,nextTimeBoy,nextTimeEle,visit.copy()))
        return result+ans


    
    
    computedAns = process(timeBoy,timeEle)
    
        
    Deeepe[(nodeBoy,nodeEle,timeBoy,timeEle,visit.getHash())]= computedAns
    
    return computedAns     # if(nodeFlowMap[node]==0):
    #     return process(False,time)
    # else:
    #     return max(process(False,time),process(True,time))



        








reader = setupInput()


nodeFlowMap,nodeAdjMap=processInput(reader)

apsp = allPairsShortestPath(nodeAdjMap)

ans=0
visit = CustomVisiter(nodeFlowMap.keys())

print(processNodes('AA','AA',26,26,visit))



reader.close()
