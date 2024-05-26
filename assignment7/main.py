import random;

if __name__ == '__main__':
    symbols = ["BAR", "BELL", "LEMON", "CHERRY"]

    result = [0] * 3
    playsArr = []

    numPlays = 100

    # start with 10 coins for 10 times 
    for i in range(numPlays):
        money = 10
        plays = 0

        # until going broke
        while (money > 0):
            money -= 1
            plays += 1

            for i in range(3):
                result[i] = symbols[random.randint(0, 3)]

            if result[0] == result[1] and result[1] == result[2] and result[2] == "BAR":
                money += 20
            elif result[0] == result[1] and result[1] == result[2] and result[2] == "BELL":
                money += 15
            elif result[0] == result[1] and result[1] == result[2] and result[2] == "LEMON":
                money += 5
            elif result[0] == result[1] and result[1] == result[2] and result[2] == "CHERRY":
                money += 3
            elif result[0] == result[1] and result[1] == "CHERRY":
                money += 2
            elif result[0] == "CHERRY":
                money += 1

        print("Number of plays: " + str(plays))
        playsArr.append(plays)

    meanPlay = sum(playsArr) // numPlays
    playsArr.sort()
    print("Mean number of plays until going broke is " + str(meanPlay))
    print("Median number of plays until going broke is " + str(playsArr[numPlays//2]))