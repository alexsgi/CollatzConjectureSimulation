
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

*Find a number n > 0 for which you do not end in a loop when applying the rules mentioned.*

## What does the software?

The code applies all mentioned rules above in a loop to all numbers up to n = 10^273 - 1. The progress is shown in the console.

    BigInteger n = new BigInteger("24061547");
  is the first value to check. This can of course be customized.
  
In addition, another special feature has been implemented: Notification by e-mail.

Open *mail.props* (under resources), set "*enabled*" to **true** and add your SMTP details.

You will now be notified when the program starts, ends and if a possible solution candidate was found.

---
**Difficulties:**

How can the program tell if it hasn't ended up in the 4-2-1 loop? Doesn't it then have to continue calculating indefinitely? 

The number of calculations per number was limited to Integer.MAX_VALUE, i.e. theoretically one could exceed the number of calculations and still not have landed in the 4-2-1 loop. That's why the email and the console only say "Potential candidate found".

---
More information to the Collatz conjuncture:

[Wikipedia](https://en.wikipedia.org/wiki/Collatz_conjecture)

[Veritasium on YouTube](https://www.youtube.com/watch?v=094y1Z2wpJg)
