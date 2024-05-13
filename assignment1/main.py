from Environment import VacuumEnvironment
from Agents import ReflexAgent

ENV_SIZE = (2,1)


def main():
    # Clean clean
    envCC = VacuumEnvironment(ENV_SIZE, 0 ,0)
    # Clean Dirty
    envCD = VacuumEnvironment(ENV_SIZE, 0 ,1)
    # Dirty Clean
    envDC = VacuumEnvironment(ENV_SIZE, 1 ,0)
    # Dirty Dirty
    envDD = VacuumEnvironment(ENV_SIZE, 1 ,1)

    envList = [envCC, envCD, envDC, envDD]
    score = 0

    for env in envList:
        agent = ReflexAgent()
        observation = env.state()
        reward = 0
        final = 0
        done = False
        # Do nothing first
        action = agent.act(observation, reward)
        turn = 1

        #print("Agent location: " +(str)(agent.agent_location))
        #print("Current state: " +(str)(observation))
        #print (envDD.room[0] + envDD.room[1])

        while not done:

            observation, reward, done = env.step(action[0])
            print ("Step {0}: Action - {1}".format(turn, action[1]))
            action = agent.act(observation, reward)
            print ("Reward {0}   Total Reward {1}".format(reward, agent.reward1))
            turn += 1
            final = agent.reward1

        score += final
        print("Performance Measure: {0} points\n".format(final))

    score /= 4
    print("Average score: " + (str)(score))

if __name__ == "__main__":
    main()