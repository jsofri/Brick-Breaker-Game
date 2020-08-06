# Brick Breaker

Brick Breaker game (AKA arkanoid) is a simple arcade game i developed in my intro to OOP course in Bar Ilan University, 2019. Each student had to choose a theme for the game, so i chose babies, because everybody loves babies :)

### requirements
 - Java Development Kit
 - Linux OS
 
**note** - The game was originally built for Linux OS,
yet with some adjustments can be run on every OS. Contact me for more information.

### installation
 - download contents of master branch (except readme folder and file).
for compiling to a jar file, write
```
make compile
make jar
```

### run
if the gama is not compiled (no `.jar` file in the game directory, 
open the game directory in CMD and type:
```
make compile
make jar
java -jar ass7game.jar
```

otherwise, simply write `java -jar ass7game.jar`

note that the game takes a bit more space after playing, in order to save and display players highscores.

### game flow
Control is by input from keyboard, mainly the arrow keys. following flow chart explains more:
![](https://github.com/yehonatansofri/Brick-Breaker-Game/blob/master/readme/gameflow.png)
#### level 1
![](https://github.com/yehonatansofri/Brick-Breaker-Game/blob/master/readme/level1.png)
#### level 2
![](https://github.com/yehonatansofri/Brick-Breaker-Game/blob/master/readme/level2.png)
#### level 3
![](https://github.com/yehonatansofri/Brick-Breaker-Game/blob/master/readme/level3.png)

### Development
adding more levels is possible and pretty easy. contact me for more information.

### License
free
