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
a couple of nodes/leaves, I thought I had found the remedy. However, the test continued to fail! Eventually I 
discovered that, because I was testing both trees in a single test method, I needed a fresh (new) "Visitor" each time.
So, I ensured that before each call to the in-order method, I instantiated a new Visitor object.

The success of the unit test of the in-order method was probably sufficient to confirm that trees had been constructed
correctly. Besides, I was reusing code that had already been rigorously tested. Regardless, I implemented a couple of
additional tests, just to be thorough.

Considering that I was reusing code that was likely already rigorously tested, I wasted a lot of time on those unit 
tests.

This is an example of why programming is so tedious. There was a statement in the code being flagged by the editor
because a command was misspelled! The thing that made it so insidious was that the misspelling actually represented a
legitimate command. It was only the argument list that differentiated the command I wanted from the command I had typed.
So, it wasn't immediately apparent to me why the editor was flagging the statement. Fortunately, it only took a few 
moments for me to figure it out.

To divert a disaster, I push the repository to GitHub frequently.

The test suite is not very robust, but I soon realized that I could spend forever refining the test suite, and never get
around to anything else.

I spent the majority of my time trying to get the _removeLeaves()_ method to work, only to find out that the unit test's
method call was flawed. Specifically, I had placed arguments in the parameter list which, to the best of my knowledge,
isn't expecting any. I have no idea how long the _removeLeaves()_ method might have been working correctly before I
discovered the root cause. All I can say is that I toiled for at least a couple of hours.

As of 4:51 on the morning of August 4, all of the coding is done.