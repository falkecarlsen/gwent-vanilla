# Milestone 3

Despite being a card game playable with physical cards, Vanilla Gwent is quite complex when modeled.
It gets especially more complicated when we add interfaces, servers, serialization, and more into the mix.
Therefore, development is broken down into a series of milestones.

This document describes Milestone 3: Units and Spells.
In this milestone we shall develop the abilities that some cards have.
We need to create the concept of spells and weather, do non-trivial power calculation,
as well as adding more parameters to playing a card (targeting, etc). 
When M3 is complete, every card will be interesting.

## Features
The following is a list of features planned for this milestone:

- Effects for units
- Weather effects
- Destruction of cards
- Calculation of power based on unit and weather effects
- Actions which can or must target something
- Hand peeking
- Spells

## Not in this milestone
The following is a list of features, which is NOT planned for this milestone.
This list is included to avoid confusion and to outline future milestones.

- Any other new gameplay logic, such as:
    - Mulligan phase (where cards are discarded initially)
    - Alignments
- Matchmaking system
    - Multiple matches running in parallel
    - Waiting for new games to start
- Persistence (No database, games are simply stored in RAM for now)
- Hidden information (the game state will include the entire game state for now)
- UI
