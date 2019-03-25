CSCI 234 - Software Engineering
Spring 2019
Project04 - Sprint 3

Due: Friday, April 5, at 11:59pm

Sprint 3 Goal: Add an order to the randomly generated data. Compare the costs of two different routing techniques for the truck.

Design (UML) and code(Java) programs that will: 

1. Resize the neighborhood so that there are 10 streets in each direction instead of 20. Put the distribution center on 510 East 5th Street.

2. Use the strategy design pattern to implement at least 2 routing strategies. Use the strategy that you may have already developed and add a new strategy in which the truck can only make right turns. If you have already implemented this strategy, then create a route in which the truck can only take left hand turns. Your code should handle the creation of more than one truck for future expansion.

3. Add a food order to each randomly generated customer event. Let's keep this simple for now. Customers can order Sandwich 1, 2, or 3, Chips 1 or 2, and drink 1, 2, 3

4. Compare the cost effectiveness of each routing method. Assumptions:
- move from one address to another in 1 unit of time
- a stop at a delivery address takes 5 units of time
- a right hand turn takes 2 units of time
- a left hand turn takes 4 units of time
- time to prepare a food order is 5 units of time
- compute the total length of each route in distance and time