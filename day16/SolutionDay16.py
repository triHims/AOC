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
    
    

def processNodes(node,time,visit:dict,nodeList,ans,nodeArr,goodValList):

    # print(f"we have nodeBoy={nodeBoy}  at time {timeBoy}\n nodeEle={nodeEle}  at time {timeEle} with \n dict {bin(visit.getHash())}\n\n" )


    visit[node]=True
    nodeArr.append(node)
    



    notRun=True
    if(time>0):
        localReach = apsp[node]
        localCost = {locNode: [time-1-localReach[locNode],((time-1-localReach[locNode])*nodeFlowMap[locNode])] for locNode in nodeList if visit.get(locNode)!=True and time-1-localReach[locNode] >= 0 }
        for nextNode,nodeFlow in localCost.items():
            notRun=False
            processNodes(nextNode,nodeFlow[0],visit.copy(),nodeList,ans+nodeFlow[1],nodeArr[:],goodValList)
    
    goodValList.append([ans,nodeArr])



    
    
    
        
    
    #     return process(False,time)
    # else:
    #     return max(process(False,time),process(True,time))



        








reader = setupInput()


nodeFlowMap,nodeAdjMap=processInput(reader)

apsp = allPairsShortestPath(nodeAdjMap)


positiveValList=[node for node ,flow in nodeFlowMap.items() if flow > 0  ]

goodValList=[]
processNodes('AA',26,{},positiveValList,0,[],goodValList)

finalAns = 0

for ans,combination in goodValList:
    secondGoodVal=[]
    newList = [ element for element in positiveValList if element not in combination ] 

    processNodes('AA',26,{},newList,0,[],secondGoodVal)

    maxOfSecondGoodVals=max(val for val,lst in secondGoodVal)
    finalAns = max(finalAns,maxOfSecondGoodVals+ans)



    



print(f"{finalAns} -- is answer")

reader.close()
