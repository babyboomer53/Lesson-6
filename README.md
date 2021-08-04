# Data Structures and Algorithms in Java (CSE-41321)
## University of California San Diego Extension
## Summer 2021 (157162)
## Raymond Mitchell III (Instructor)
## Assignment #6
### Binary Tree Algorithms
### Problem Statement
### Proposed Solution
### Implementation
#### Source Code
#### Homework6 class
#### Homework6Test class
### Results
### Postmortem
The first unit test of the in-order method failed on the second tree. When I discovered that I had inadvertently omitted
a couple of nodes/leaves, I thought I had found the remedy. However, the test continue to fail! That's when I discovered
that, because I was testing both trees in a single test method, I needed a fresh (new) "Visitor" each time. So, I 
ensured that before each call to the in-order method, I instantiated a new Visitor object.

The success of the unit test of the in-order method was probably sufficient to confirm that trees had been constructed
correctly. Besides, I was reusing code that had already been rigorously tested. Regardless, I implemented a couple of
additional tests just to be thorough.

Considering that I was reusing code that was likely already rigorously tested, I wasted a lot of time on those unit 
tests.

Wow! I ended up with two methods with the same name! The only thing distinguishing them is their parameter lists.
I did not know that would work!

This is an example of why programming is so tedious. There was a statement in the code being flagged by the editor
because a command was misspelled! The thing that made it so insidious was that the misspelling actually represented a
legitimate command. It was only the argument list that differentiated the command I wanted from the command I had typed.
So, it wasn't immediately apparent to me why the editor was flagging statement. This time, it only took a few moments
for me to figure it out.

As a measure against disaster, I push to GitHub frequently.