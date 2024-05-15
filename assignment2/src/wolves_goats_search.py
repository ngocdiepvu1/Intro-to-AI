from collections import deque
import time

# creat a state that has number of wolves and goats on the left side of the ride and the locaiton of the boat
class State(object):
    def __init__(self, wolves, goats, boat):
        self.wolves = wolves
        self.goats = goats
        self.boat = boat

    # check the validity of number of wolves and goat
    def isValid(self):
        # num wolves and goats >= 0 and <= 3
        if self.wolves < 0 or self.goats < 0 or self.wolves > 3 or self.goats > 3:
            return False
        # more wolves than goats on the left side
        if self.wolves > self.goats and self.goats != 0:
            return False
        # more wolves than goats on the right side
        if self.wolves < self.goats and self.goats != 3:
            return False
        return True
    
    # reach goal state when no wolves and goast left on left side
    def isGoal(self):
        return self.wolves == 0 and self.goats == 0

    def successors(self):
        # move form left to right side
        if self.boat == 1:
            sign = -1
            direction = "left to right"
        else:
            sign = 1
            direction = "right to left"
        for g in range(3):
            for w in range(3):
                newState = State(self.wolves + sign * w, self.goats + sign * g, self.boat + sign * 1)
                # each time the boat has to move 1 or 2 animals
                if w + g >= 1 and w + g <= 2 and newState.isValid():
                    action = "%d wolves and %d goats %s. %r" % (w, g, direction, newState)
                    yield action, newState

    def __repr__(self):
        return "(%d, %d, %d)" %(self.wolves, self.goats, self.boat)

class Node(object):
    def __init__(self, parentNode, state, action, depth):
        self.parentNode = parentNode
        self.state = state
        self.action = action
        self.depth = depth

    # create all successor nodes
    def expand(self):
        for (action, successorState) in self.state.successors():
            successorNode = Node(parentNode = self, state = successorState, action = action, depth = self.depth + 1)
            yield successorNode

    # extraction all the steps in the solution
    def extractSolutions(self):
        solution = []
        node = self
        while node.parentNode is not None:
            solution.append(node.action)
            node = node.parentNode
        solution.reverse()
        return solution

# breadth first search algorithm
def breadthFirstTreeSearch(initialState):
    initialNode = Node(parentNode = None, state = initialState, action = None, depth = 0)
    # first in first out
    fifo = deque([initialNode])
    numExpansions = 0
    maxDepth = -1
    while True:
        #startTime = time.time()
        if not fifo:
            print("%d expansions" % numExpansions)
            return None
        node = fifo.popleft()
        if node.depth > maxDepth:
            maxDepth = node.depth
            #finishedTime = time.time()
            #print ("[depth = %d] %.2fs" % (maxDepth, finishedTime - startTime))
        #if goal is reached, return solution
        if node.state.isGoal():
            #print ("%d expansions" % numExpansions)
            solution = node.extractSolutions()
            return solution
        numExpansions += 1
        fifo.extend(node.expand())

def main():
    print("The state of the original side will be presented in (number of wolves, nummber of goats, boat) format")
    startTime = time.time()
    initialState = State(3, 3, 1)
    solution = breadthFirstTreeSearch(initialState)
    if solution is None:
        print("No solution")
    else:
        print("Using breadth-first search, the solution (%d steps) is: " % len(solution))
        for step in solution:
            print("%s" % step)
    finishedTime = time.time()
    print("Elapsed time: %.2fs" % (finishedTime - startTime)) 
        
if __name__ == "__main__":
    main()