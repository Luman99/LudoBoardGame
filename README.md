# LudoBoardGame
Ludo is a strategy board game for two to four players. Written in Java some time ago.
The project includes comments in Polish.

# Instruction
After selecting the number of players and the colors of the players who will play, the game begins
A player is randomly selected from among the players who will start.
To roll a die, click on the white oval shape, then the side of the dice will appear next to it with the number of spots shown,
and the oval itself will change its color to the color of the player whose turn it will now be.
For example, if the oval shape is red, it means that the red player can move his pawn. It works like in traditional Ludo,
that is, first he has to throw a 6 in order to get a pawn from the "waiting" field, where his 4 pieces are on the starting field,
which is already on the fields to move, they are appropriately marked with a color. When a player has no move or his move is over, the next player must roll the die.
When a player rolls a 6, he may roll again.

It may seem complicated, but it actually works as if we were playing board Ludo and moving the pieces, with the difference in dice roll which is done differently.
When it is the red player's turn, it is impossible for another player to move, and when the player moves, he cannot move again without the previous die roll.
