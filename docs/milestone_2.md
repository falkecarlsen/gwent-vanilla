# Milestone 2

Despite being a card game playable with physical cards, Vanilla Gwent is quite complex when modeled.
It gets especially more complicated when we add interfaces, servers, serialization, and more into the mix.
Therefore, development is broken down into a series of milestones.

This document describes Milestone 2: The API.
In this milestone we shall develop the fundamentals of the server API and a minimal CLI client.
This includes serialization of relevant data, such as game state and actions.
When M2 is complete, we have a vertical slice.

## Features
The following is a list of features planned for this milestone:

- A server managing a single game of Vanilla Gwent
- minimal CLI client
- (de)serialization of game state
- (de)serialization of actions
- The server API, including
  - POST request
    - Start new game
    - Join game
    - Player actions
  - GET request
    - Current game state


## Not in this milestone
The following is a list of features, which is NOT planned for this milestone.
This list is included to avoid confusion and to outline future milestones.

- Any new gameplay logic, such as:
  - Mulligan phase (where cards are discarded initially)
  - Spell cards like aces and twos (weather effects)
  - Unit cards with abilities
  - Alignments
- Matchmaking system
  - Multiple matches running in parallel
  - Waiting for new games to start
- Persistence (No database, games are simply stored in RAM for now)
- Hidden information (the game state will include the entire game state for now)
- UI
