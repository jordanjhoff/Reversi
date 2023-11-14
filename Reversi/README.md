# CS3500 Hexagonal Reversi


## Overview

This codebase is designed to implement the full game of 
 reversi on a hexagonal board. The game consists of two players
competing for control of a board. The game is turn based,
and whenever a player manages to 'sandwich' another players pieces
it captures all the pieces in between and turns them into the player's
piece color. 

A turn can optionally be passed, or forced to pass if there are no 
available moves. The game is over either when a both players skip in a row
or there are no more available moves. The player with more pieces
on the board when the game ends is the winner.

The game assumes that the player understands the player interface
and knows how to make moves, and does the rest from there. 

### Extensibility 
- The game is implemented due to the MVC design pattern, consisting of a model,
view, and controller. 
- Currently, the only implementation of the view is a textual version, 
where the entire game is represented in the terminal. In the future,
we plan to implement our interface with a graphical user interface, to be more
friendly. 
- It's within our bounds to extend any of the three sections of 
our design, to create a different type of reversi, a different
view for reversi, and various types of controllers, being human or ai.
-  At this time, no controllers are implemented

***
### Why Reversi?
- It was due
- The game looked fun to play
***

## Quick Start

The following section will take the user through
an example of making the game and playing a couple moves.


The game can be initialized by the following code. We will define 
both our view and model:

    Appendable out = new StringBuilder();   // output for textual view
    ReversiModel model = new HexReversi(5); // create hexagonal reversi with radius 5
    ReversiView view = new ReversiTextualView(model, out); // creates view due to output and model

The game can be rendered and visualized by the following command:
    
    view.render();
    System.out.println(out.toString());

We will observe the following output:

         _ _ _ _ _ _
        _ _ _ _ _ _ _
       _ _ _ _ _ _ _ _
      _ _ _ _ * _ _ _ _
     _ _ _ * X O * _ _ _
    _ _ _ _ O _ X _ _ _ _
     _ _ _ * X O * _ _ _
      _ _ _ _ * _ _ _ _
       _ _ _ _ _ _ _ _
        _ _ _ _ _ _ _
         _ _ _ _ _ _

#### Where X is BLACK, O is WHITE, and * are currently possible moves. 
To make a move in the current state, add a piece with the position.

Before I show that, I'll do a brief dive into our coordinate system.

### HexPosition

There are three coordinates, with the central coordinate is (q,r,s) = (0,0,0). In any given direction, two coordinates change while one stays the same.
The corresponding coordinate increases in each direction are:
    
         q,-r,+s    +q,-r,s
               \   /
                \ /
      -q,r,+s ---*--- +q,r,-s
                / \
               /   \
         -q,+r,s     q,+r,-s
    
     q
      /    \
     |  *  | r
      \    /
      s

The coordinates cannot just be any number, and it is ALWAYS true that:
    
    q = -r - s; // ( q + r + s will equal 0 )

### Making a move
Lastly, to make a move, just add the piece to the game board by the following:

    model.addPiece(new HexPosition(1, -2, 1));
    view.render(); //render the update
    System.out.println(out.toString());

This will result in the following.

         _ _ _ _ _ _
        _ _ _ _ _ _ _
       _ _ _ _ * _ _ _
      _ _ _ _ X _ _ _ _
     _ _ _ _ X X * _ _ _
    _ _ _ _ O _ X _ _ _ _
     _ _ _ * X O _ _ _ _
      _ _ _ _ * _ _ _ _
       _ _ _ _ _ _ _ _
        _ _ _ _ _ _ _
         _ _ _ _ _ _

Now, whites possible moves are highlighted by * .

---

For a more in depth understanding of how to work our game,
please observe the model's interface ReversiModel for additional methods with javadoc. 
This was just a little exercise to get the ball rolling for anyone who is interested.

---

## Heirarchy

### Key Components

#### Model

As stated, we strictly followed the model-view-controller design
pattern. Quickly, the model is the one that models the entire game board, handling
moves, which piece is where, what is legal, as well as making changes when commands are inputted. 

#### View
The view listens to the model to represent it, in whichever way it is implemented.
Currently, our only representation is the textual version,
but it could represent the exact same game as a GUI. 

#### Controller
The controller, albeit not implemented, will be for communicating between the user,
the model, and the view. It will take inputs and make sure that the changes happen, 
and are represented by the view so the player can have a seamless experience while playing
hexagonal reversi. 

### Key Subcomponents
Our main subcomponents, besides being our implementations of our interfaces for the explained
hexagonal version of reversi as well as textual view, would be our classes for the hexagonal positions
as well as teamcolor. 

#### HexPosition
This was explained briefly earlier, but essentially this class just has its three coordinates,
and mainly checks to make sure that the position is legal. It additionally has the basic toString,
getters, as well as equals and hashcode. 

#### TeamColor
TeamColor is our enumerated type for representing every team playing the game, as well as 
their desired textual representation. It also contains the information about cycling through the teams:
if it is one colors turn, who goes next?

### Source Organization
Our codebase is first split into source and tests. Tests is just for our 
testing to make sure every single thing works as we intend it to. 
If you choose source, follow this map to be guided.

                        Source
                          |
                          |
                    cs3500.reversi
                    /     |      \
                   /      |       \
              model   controller   view
                /         |          \ 
               /          |           \
              |   CONTROLLER IMPL TBD  \                        JPanel
              |                         \         JFrame          |
              |                     IReversiView   /              |
          ReversiModel (interface)        \       /               |
              |                       ReversiGUIView (has a JReversiPanel)
              |                                
              |                              
              |
              |
    HexPosition (hexagonal implementation of ReversiModel
             /                             \
            /                               \
           /                                 \
    HexPosition (hex coords)          TeamColor (enum of possible types)


# Changes for PART II

- added additional observer methods including ...
- 







# References

Most of our knowledge about the hexagonal coordinate system
was based off this incredibly useful article, please check it out
if you have time because it gets into stuff way more
interesting than reversi.

    https://www.redblobgames.com/grids/hexagons/

We additionally used this site to learn how to play this game, which implements
hexagonal reversi with an AI incredibly hard to beat. 

    http://meta-site.net/hexlo0.1/#