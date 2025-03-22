# Tic Tac Toe Java
Tic Tac Toe with bot and online play made in java\
All modes support n x n grid (change size in constructor of MainMenu in Main class)\
UI made with Swing and AWT\
Bot uses minimax algorithm with alpha beta pruning

## Compile and Run

### Using Visual Studio Code
Install [Language Support for Java(TM) by RedHat](https://marketplace.visualstudio.com/items?itemName=redhat.java) extension\
Go to Run and Debug tab in sidebar (Ctrl + Shift + B)\
Select Single or Dual Instance on top and run (F5)

### Using Command Line
Compile files to bin folder
```bash
javac -d bin src/TicTacToe/*.java src/UI/*.java src/Main.java
```

Run after compilation (Run again in another terminal for dual instance)
```bash
java -cp bin Main
```
