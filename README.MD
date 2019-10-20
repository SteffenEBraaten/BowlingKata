# Bowling
________

The bowling kata implemented in Kotlin.
Link to the original kata description can be found here: http://codingdojo.org/kata/Bowling/

Albert Llousas Ortizs(albertllousas) implementation of the kata has been used
as inspiration for the implementation for this kata as it's structure and design
was appealing. However I figured I could make the implementation of the kata more
readable by adding companion objects and redesign how to final score was calculated.
This has resulted that the focus of this kata has been on the readability of the code
and not optimization.

A link to Alberts implementation can be found here: https://github.com/albertllousas/kata-bowling

#### Description of bowling
In American Ten-Pin Bowling a whole game or line consits of ten frames. In each frame the player gets up to two tries to knock down all the pins. The score varies from 0-300 in each game. There are some special cases in bowling that we'll go over now.

*Strike*
- If the player manages to knock down all the pins in the first roll then it's called a strike and is marked by a '**X**'.
- If a player gets a strike the frame is over and he/she won't get the second roll.
- A strike is calculated by 10 plus the sum of the next two shots.

*Spare*
- If a player manages to knock down all the pins in two rolls then it's called a spare and is marked by a '**/**'.
- A spare is calculated by 10 plus the sum of the next shot.

*Open frame*
- A open frame happens when the player does not manage to knock down all the pins in two rolls.
- An open frame is the sum of the two rolls in the current frame.

*Miss*
- If a player misses all the pins it's marked by a '**-**' and will be given 0 points for that roll.

##### Final frame
The final frame or 10. frame in bowling is a special round where the player can be given extra rolls.
- If you roll a strike in the first shot of the 10th frame, you get 2 more shots.
- If you roll a spare in the first two shots of the 10th frame, you get 1 more shot.
- If you leave the 10th frame open after two shots, the game is over and you do not get an additional shot.

### Running the tests
```bash
gradle test 
```
