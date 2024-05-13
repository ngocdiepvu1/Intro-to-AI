ACTIONS = ((0, "Turn Left"),
           (1, "Turn Right"),
           (2, "Suck Dirt"),
           (3, "Turn Off"),)

class ReflexAgent(object):
    def __init__(self):
        self.reward1 = 0

    def act(self, observation, reward):
        self.reward1 += reward

        # If dirt then suck
        if observation == 1:
            return ACTIONS[2]

        # Clean, turn or stop
        if observation == 0:
            return ACTIONS[1]

    
        # Else return -1
        return [-1, -1]

