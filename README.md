
# CollatzConjectureSimulation
Let's imagine we have a number *n* > 0.
**Rules:**

 1. If the number is even, divide it by two
 2. If the number is odd, triple it and add one

Let's try it with **n = 3:**
`n mod 2 ≠ 0` n is odd

    3 * 3 + 1 = 10
10 is even

    10 / 2 = 5
5 is odd

    5 * 3 + 1 = 16
    16 / 2 = 8
    8 / 2 = 4
    4 / 2 = 2
    2 / 2 = 1
1 is obviously odd

     3 * 1 + 1 = 4
Now we are back at **4**, accordingly we are now in a **loop**.
What does the Collatz conjencture say now? 
Find a number n > 0 for which you do not end in a loop when applying the rules mentioned.
