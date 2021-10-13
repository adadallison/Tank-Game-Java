# Tank-Game-Java


## Project Overview:
My project is a typical Tank Wars game where two players exist that, each with the objec+ve to win the game by firing bullets at the other player until 
the other player has no more lives. The game is Super Mario Bros. themed, and uses power ups to replenish players’ health, lives, and give them bulletproof 
abili+es. To realize this game Java Swing and other libraries to develop GUIs. The game allows two players, and the objec+ve is to basically not die. The tanks 
are able to shoot one another and reduce the health count and life count of the other tank, and if they life count of one tank reaches zero, then that player 
loses. They play the game within the game panel, and also shoot breakable walls to create navigate more easily. Throughout the game a player can replenish itself 
with two kinds of power ups; one power up gives the player an extra life, another replenishes its health, and the other allows the player to be bullet proof for
a certain amount of +me. Each player starts out with 3 lives and is able to obtain a maximum of 5 lives; 1 life = 100 health counts, and each bullet fired at a 
tank reduces its health count by 10. Once the game is over, we are told which player won, and the player can exit.

## How to Build/Import Project:
Once you copy the HTTP link from GitHub, from my repository, you can use your terminal to perform git clone and it will clone a local repository,
typically on ones desktop, where the folder will then be . An easier way might be to go to GitHub and search for the GitHub repository link 
(https://github.com/adadallison/Tank-Game-Java). If you press code, now you can see ‘Download ZIP files,’ which will allow the folder to directly
download to your computer. To import the project correctly onto an IDE, open your IDE and selected “import project,” select the folder and then press Open. 
Once the file is open, the Launcher class can run the project, or running the jar file in the jar folder can also do that.


## How to Run Project:

### Build JAR:
Once the file is open on IntelliJ or another IDE, the Launcher class is where the main method is located, therefore this is where it can be ran. The jar file can also be selected to run the project. Once the project has successfully ran, the player begins the game by pressing start, or the space key. The game panel appears, and once a player loses, the end game panel appears telling the player that the game is over. Here the player must exit the game by pressing the exit bucon. If the player would like to play again, they can run the Launcher class by right clicking and pressing run, or running the jar the same way.

### Game Controls and Rules:
The two players use different keys to control their movements. Each player can move forwards, backwards, rotate, lee or right, and shoot bullets. Player 1 uses the up, down, lee, right, and enter arrows to navigate. Each key does that is implied, the enter key is for shoo+ng bullets. Player 2 uses the W (up), S (down), A (rotate left), D (rotate right) and F (shoot bullets) keys. To
 
begin the game once the start panel appears, the player can press the space key to begin playing instead of clicking Start.
The only rules are that aeer having 5 lives while having 100 health count, the mushrooms and coins (power-ups) are no longer effective. Players die when they have zero, lives and this will make them lose the game. They also have a bulletproof power-up, the other players bullets are not effective.


*read more in depth overview of project the Documentation PDF
