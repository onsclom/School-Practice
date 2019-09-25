import random
import time

print("Ask a question?")

i=input()

time.sleep(2)
answers = ["Yes","No","Ask again","Maybe"]

print(random.choice(answers))

