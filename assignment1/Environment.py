class VacuumEnvironment(object):
    def __init__(self, size, locationA, locationB):
        #clean=0, dirty=1
        self.size = size
        # Agent starts at location A
        self.agent_location1 = 0

        # 4 possible initial dirt configurations - clean=0, dirty=1
        self.room = [locationA, locationB]


    def state(self):
        return self.room[self.agent_location1]

    def step(self, action): # 0-left 1-right 2-suck 3-stop
        reward = -1
        done = False
        #print("Action " + (str)(action))
        #print("Agent location 1 : " +(str)(self.agent_location1))

        # Move left
        if action == 0:
            self.agent_location1 -= 1

        # Turn right
        elif action == 1 and self.agent_location1 == 0:
            self.agent_location1 += 1

        elif action == 2:
            # Reward of +100 for sucking up dirt
            if (self.room[self.agent_location1] == 1):
                reward += 100
                self.room[self.agent_location1] = 0

        #Both clea, turn off 
        elif action == 1 and self.agent_location1 == 1:
            done = True
        
        #print("Agent location: " +(str)(self.agent_location1))

        return self.room[self.agent_location1], reward, done
