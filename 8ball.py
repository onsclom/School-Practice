import random

print("Ask a question?")

i=input()

answers = ["Yes","No","Ask again","Maybe"]

print(random.choice(answers))

print(i)
