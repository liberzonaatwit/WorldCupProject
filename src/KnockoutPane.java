import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.Random;
import java.util.ArrayList;
/** note for Zach (delete later):
 *   this class will create the pane for the knockout tab,
 *   Main is composed of this (I'm pretty sure)
 *      - Justin V
 */

// TODO: Add comments and description
public class KnockoutPane extends BorderPane {
    private ArrayList<TeamButton> buttonList = new ArrayList<>();
    private GridPane masterKnockoutPane = new GridPane();
    private Pane knockoutPane = new Pane();
    private ArrayList<Team> sixteenTeams;
    private ArrayList<Game> eightGames;
    private ArrayList<Game> quarterGames;
    private ArrayList<Game> semiGames;
    private ArrayList<Game> finalAndThirdPlaceGame;
    private boolean sixteenTeamsDisplayed = false;
    private boolean eightGamesDisplayed = false;
    private boolean quarterGamesDisplayed = false;
    private boolean semisGamesAndThirdPlacementsDisplayed = false;
    private int lineWidth = 2;
    private Color lineColor = Color.WHITE;

    private Button displaySixteenTeams = new Button("Display 16");
    private Button displayEightGames = new Button("Display 8");
    private Button displayQuarterGames = new Button("Display Quarters");
    private Button displaySemisGames = new Button("Display Semis");
    private Button displayFinalAndThird = new Button("Display Final and Third");
    private Button simulateAll = new Button("Display All Rounds");


    public KnockoutPane(Double height, Double width, Simulator sim){
        //this.sim = sim;
        sixteenTeams = sim.getTeamsInOrderInRoundOfSixteen();
        eightGames = sim.simulateRoundOfSixteen();
        quarterGames = sim.simulateQuarters();
        semiGames = sim.simulateSemis();
        finalAndThirdPlaceGame = sim.simulateFinalAndThirdPlace();
        //this.setBackgrou6nd(new Background(new BackgroundFill(Color.rgb(88,146,87), CornerRadii.EMPTY, Insets.EMPTY)));
        //this.setBackground(new Background(new BackgroundImage(new Image("Images/One.jpg"),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        Image img = new Image("Images/grass.png");
        this.setBackground(new Background(new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT))); //new BackgroundSize(width, height,true,true,true,true)
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 10, 10, 10));
        buttonBox.setSpacing(10);
        buttonBox.getChildren().addAll(displaySixteenTeams,displayEightGames, displayQuarterGames,displaySemisGames,displayFinalAndThird);
        VBox verticalButtonBox = new VBox();
        verticalButtonBox.setAlignment(Pos.CENTER);
        verticalButtonBox.getChildren().addAll(buttonBox,simulateAll);
        this.setTop(verticalButtonBox);
        this.setCenter(this.createBracket());

        int counter = 0;
        char c = 65;
        for(int i = 0; i < 16; i +=4){   
            buttonList.get(counter).setText("Group " +  c + " Winner ");
            c++;
            buttonList.get(counter+1).setText("Group " + c + " Runner Up");
            buttonList.get(counter+23).setText("Group " + c + " Winner");
            c--;
            buttonList.get(counter+23+1).setText("Group " + c + " Runner Up");
            c +=2;
            counter +=2;
        }

        displaySixteenTeams.setOnAction(e -> addNamesOfSixteenTeams());
        displayEightGames.setOnAction(e -> addNamesOfTheEightGames());
        displayQuarterGames.setOnAction(e -> addNamesToQuaterGames());
        displaySemisGames.setOnAction(e -> addNamesToSemisGamesAndThirdPlacePlacements());
        displayFinalAndThird.setOnAction(e -> addNamesToFinalsAndThirdPlace());
        simulateAll.setOnAction(e -> simulateAll());

        displayEightGames.setDisable(true);
        displayQuarterGames.setDisable(true);
        displaySemisGames.setDisable(true);
        displayFinalAndThird.setDisable(true);
    }

    private GridPane createBracket(){
        int scalingFactor = 40;      // Z.L. (modified value in "scalingFactor" from 50 to 40.)   //Scaling factor of the bracket, increase = bigger
        double buttonSizeX = scalingFactor * 3;                                    //Button sizes, more convenient
        double buttonSizeY = scalingFactor / 1.25;
        int thirdPlaceX = 0;
        int x = (int) buttonSizeX/2;          // Z.L. (modified value in "x" variable from 100 to 120.)      //The initial X cord of the top left bracket;
        int y = 0;                    
       
        int yLength = scalingFactor/2;          //The length of the line going up and down on the L shape. **MIGHT GET REMOVED / TWEAKED since the scaling doesn't 100% fit with it.**
        int horizontalLength = scalingFactor*4;            //The length of the line going left and right on the L shape.  **MIGHT GET REMOVED // TWEAKED since the scaling isn't 100% with it.**
        int yIncrement = scalingFactor*2;       //How much will the next tier bracket be moved down. 
        int yCordsAtTierZero = y;               //Since we change the y cord, we're setting base y at tierZero          
        int yCordsAtTierOne = yCordsAtTierZero + scalingFactor; //Tier zero gets used by the scalingFactor to produce the proper y cords for the tier 
        int yCordsAtTierTwo = yCordsAtTierOne + scalingFactor*2;                         
        int yCordsAtTierThree = yCordsAtTierTwo + scalingFactor*4;
        int yIncrementIncrease = yIncrement;                    //Since we change the yIncrement, we use incrementIncrease to keep the base value; technically can be removed but eh
                  //The initial y cord of the top left bracket;

        Label title = new Label("FIFA WORLD CUP BRACKET");
        title.setTextFill(Color.rgb(255,255,255));
        title.setFont(Font.font("Arial Black", 25));
        
        //title.setBackground(new Background(new BackgroundFill(Color.rgb(105,105,105), CornerRadii.EMPTY, Insets.EMPTY)));
        

        Label winner = new Label("    WORLD\nCUP WINNER");
        winner.setFont(Font.font("Arial Black", 20));
        //winner.setTextFill(Color.rgb(255,244,32));
        //winner.setBackground(new Background(new BackgroundFill(Color.rgb(105,105,105), CornerRadii.EMPTY, Insets.EMPTY)));
        winner.setTextFill(Color.rgb(255,244,32));
        

        Rectangle rect = new Rectangle();

        rect.setHeight(buttonSizeY*5);
        rect.setWidth((buttonSizeX*2.09));
        rect.setStroke(lineColor);
        rect.setStrokeWidth(lineWidth);
        rect.setFill(Color.TRANSPARENT);

        Label thirdPlace = new Label("3rd Place");
        thirdPlace.setFont(Font.font("Arial Black", 18));
        thirdPlace.setTextFill(Color.rgb(255,244,32));
        //thirdPlace.setBackground(new Background(new BackgroundFill(Color.rgb(105,105,105), CornerRadii.EMPTY, Insets.EMPTY)));
        

        knockoutPane.getChildren().addAll(title, winner, rect, thirdPlace);
        

        for(int i = 1; i < 35; i++){
            int buttonX = (int)(x - buttonSizeX/2);
            int buttonY = (int)(y - buttonSizeY/2);
            TeamButton button = new TeamButton();
            button.setMinSize(buttonSizeX, buttonSizeY);
            button.setMaxSize(buttonSizeX, buttonSizeY);
            //
            button.setFont(Font.font("Arial Condensed",scalingFactor * 3 / 10));
            button.setTextFill(Color.WHITE);
            button.setBackground(new Background(new BackgroundFill(Color.rgb(69,113,80), CornerRadii.EMPTY, Insets.EMPTY)));

            if(i < 15){
                if(i % 2 == 1){//odd
                    button.setLayoutX(buttonX);
                    button.setLayoutY(buttonY);
                    drawLines(x, y, false, false, (int)(((yIncrement)/2)-(buttonSizeY)/2), horizontalLength);
                    y += yIncrement;
                }
                else{
                    button.setLayoutX(buttonX);//even
                    button.setLayoutY(buttonY);
                    drawLines(x, y, false, true, (int)(((yIncrement)/2)-(buttonSizeY)/2), horizontalLength);
                    y += yIncrement;
                    if(i == 8){
                        x += horizontalLength;
                        y = yCordsAtTierOne;                                //sets up the yCords for the **NEXT** tier
                        yLength += yIncrementIncrease/2;
                        yIncrement += yIncrementIncrease;
                    }
                    else if(i == 12){
                        x += horizontalLength;
                        y = yCordsAtTierTwo;                                
                        yLength += yIncrementIncrease/2;
                        yIncrement += yIncrementIncrease*2;
                    }
                    else if(i == 14){
                        x += horizontalLength; 
                        y = yCordsAtTierThree;               
                        yLength += yIncrementIncrease/2;
                    }
                }
            }
            else if(i == 15){
                button.setLayoutX(buttonX);
                button.setLayoutY(buttonY);
                drawLines(x, y, false, true, (int)(((yIncrement)/2)-(buttonSizeY)/2), horizontalLength);
                x += horizontalLength;
                y = yCordsAtTierThree - yLength;  //THIS IS THE SPECIAL CASE. since the button needs to be **UP**
            }
            else if(i == 16){
                button.setLayoutX(buttonX);
                button.setLayoutY(buttonY);
                title.setLayoutX(x-(354/2)); //312 is the rough pixel measurement #GimpForLife
                title.setLayoutY(y-yCordsAtTierTwo);//
                winner.setLayoutX(x-(144/2));//108 is rough pixel measurement (font isn't scalable for this reason)
                winner.setLayoutY(y-35-10-buttonSizeY); //5 is the height of the label, and 10 is the "distance" between the label and the button, and buttonSizeY is the height of the button
                rect.setX(x-(rect.getWidth()/2));//
                rect.setY(y+(rect.getHeight()*1.23));//
                thirdPlace.setLayoutX(rect.getX()+((92*((rect.getWidth()/92)-1))-buttonSizeX/16));//
                thirdPlace.setLayoutY(rect.getY());//12 is the height of the label
                thirdPlaceX = x;
                x += horizontalLength;
                y = yCordsAtTierThree;
            }
            else if(i == 17){
                button.setLayoutX(buttonX);
                button.setLayoutY(buttonY);
                drawLines(x, y, true, true,(int)((((yIncrement)/2)-(buttonSizeY))-(buttonSizeY/8)), horizontalLength);
                x += horizontalLength;
                y = yCordsAtTierTwo;                                    
                yLength -= yIncrementIncrease/2;
            }
            else if(i > 17 && i < 32){
                if(i % 2 == 1){
                    button.setLayoutX(buttonX);
                    button.setLayoutY(buttonY);
                    drawLines(x, y, true, true, (int)(((yIncrement)/2)-(buttonSizeY)/2), horizontalLength);
                    y += yIncrement;
                    if(i == 19){
                        x += horizontalLength;
                        y = yCordsAtTierOne;                               
                        yLength -= yIncrementIncrease/2;
                        yIncrement -= yIncrementIncrease*2;
                    }
                    else if(i == 23){
                        x += horizontalLength;
                        y = yCordsAtTierZero;                                
                        yLength -= yIncrementIncrease/2;
                        yIncrement -= yIncrementIncrease;
                    }
                }
                else{
                    button.setLayoutX(buttonX);
                    button.setLayoutY(buttonY);
                    drawLines(x, y, true, false, (int)(((yIncrement)/2)-(buttonSizeY)/2), horizontalLength);
                    y += yIncrement;
                }
            }
            if(i == 31){
                y = yCordsAtTierThree;
                x = thirdPlaceX;
            }
            // Third Place Bracket:
            else{
                
                if(i == 32){
                    x -= (buttonSizeX*.4);
                    y -= ((buttonSizeY*3)-(buttonSizeY*6));
                    button.setLayoutX(x - buttonSizeX/2);
                    button.setLayoutY(y - buttonSizeY/2);
                    drawLines(x,y,false,false, (int)(((yIncrement)/2)-(buttonSizeY)/2), horizontalLength /2);
                    y += yIncrement;
                }
                else if(i == 33){
                    button.setLayoutX(buttonX);

                    button.setLayoutY(buttonY);
                    drawLines(x,y,false,true, (int)(((yIncrement)/2)-(buttonSizeY)/2), horizontalLength /2);
                    x += horizontalLength / 1.5;
                    y -= yIncrement / 2;
                }
                else if(i == 34){
                    button.setLayoutX(buttonX);
                    button.setLayoutY(buttonY);
                }
            }
            buttonList.add(button);
            knockoutPane.getChildren().addAll(button);
        }
        masterKnockoutPane.add(knockoutPane, 0, 0);
        masterKnockoutPane.setAlignment(Pos.CENTER);
        return masterKnockoutPane;
    }

    private void drawLines(int x, int y, boolean isLeft, boolean isUp, int yLength, int horizontalLength){
        Line line1;
        Line line2;
        

        int x_length;

        if(isLeft){
            x_length = x - horizontalLength;
            line1 = new Line(x, y, x_length, y);
            line1.setStrokeWidth(lineWidth);
            line1.setStroke(lineColor);
        }
        else{
            x_length = x + horizontalLength;
            line1 = new Line(x, y, x_length, y);
            line1.setStrokeWidth(lineWidth);
            line1.setStroke(lineColor);
        }
        if(isUp){
            line2 = new Line(x_length, y, x_length, y - yLength);
            line2.setStrokeWidth(lineWidth);
            line2.setStroke(lineColor);
        }
        else{
            line2 = new Line(x_length, y, x_length, y + yLength);
            line2.setStrokeWidth(lineWidth);
            line2.setStroke(lineColor);

        }

        knockoutPane.getChildren().addAll(line1, line2);
    }

    private void buttonNamesToNumbers(){
        //JUST A DEBUG BECAUSE COUNTING IS HARD
        for(int i = 0; i < buttonList.size(); i++){
            buttonList.get(i).setText(Integer.toString(i));
        }
    }

    private void addNamesOfSixteenTeams(){
        int counter = 0;
        for(int i = 0; i < 16; i +=4){   
            buttonList.get(counter).setTeam(sixteenTeams.get(i));
                
            buttonList.get(counter+1).setTeam(sixteenTeams.get(i+1));  

            buttonList.get(counter+23).setTeam(sixteenTeams.get(i+2));

            buttonList.get(counter+23+1).setTeam(sixteenTeams.get(i+3)); 

            counter +=2;
        }
        sixteenTeamsDisplayed = true;
        displaySixteenTeams.setDisable(true);
        displayEightGames.setDisable(false);
    }
    private void addNamesOfTheEightGames(){
        if(sixteenTeamsDisplayed == true){
            int counter = 0;
            for(int i = 8; i < 11; i += 2){
                buttonList.get(i).setGame(eightGames.get(counter));  
                buttonList.get(i).setGameOrder(1);   

                buttonList.get(i+11).setGame(eightGames.get(counter+1));
                buttonList.get(i+11).setGameOrder(1);        

                buttonList.get(i+1).setGame(eightGames.get(counter+2));
                buttonList.get(i+1).setGameOrder(1);        
                
                buttonList.get(i+12).setGame(eightGames.get(counter+3));   
                buttonList.get(i+12).setGameOrder(1);     

                counter =+ 4;
            }
        }
        displayEightGames.setDisable(true);
        eightGamesDisplayed = true;
        displayQuarterGames.setDisable(false);
    }
    private void addNamesToQuaterGames(){
        if(eightGamesDisplayed == true){
            int counter = 0;
            for(int i = 12; i < 14; i++){
                buttonList.get(i).setGame(quarterGames.get(counter));
                buttonList.get(i).setGameOrder(2);   

                buttonList.get(i+5).setGame(quarterGames.get(counter+1));
                buttonList.get(i+5).setGameOrder(2);   
                counter =+2;
            }
        }
        displayQuarterGames.setDisable(true);
        quarterGamesDisplayed = true;
        displaySemisGames.setDisable(false);
    }
    private void addNamesToSemisGamesAndThirdPlacePlacements(){
        if(quarterGamesDisplayed == true){
            buttonList.get(14).setGame(semiGames.get(0));
            buttonList.get(14).setGameOrder(3);   

            buttonList.get(16).setGame(semiGames.get(1));
            buttonList.get(16).setGameOrder(3);   
            Random random = new Random();
            if(semisGamesAndThirdPlacementsDisplayed == false){
            int randomNumber = random.nextInt(2);
                if(randomNumber == 0){
                    buttonList.get(31).setGame(finalAndThirdPlaceGame.get(1), true);
                    buttonList.get(31).setGameOrder(3);   
                }
                else{
                    buttonList.get(31).setGame(finalAndThirdPlaceGame.get(1), false);
                    buttonList.get(31).setGameOrder(3);
                } 
                if(randomNumber == 0){
                    buttonList.get(32).setGame(finalAndThirdPlaceGame.get(1), false);
                    buttonList.get(32).setGameOrder(3);
                }
                else{
                    buttonList.get(32).setGame(finalAndThirdPlaceGame.get(1), true);
                    buttonList.get(32).setGameOrder(3);
                    
                }
            }
            displaySemisGames.setDisable(true);
            displayFinalAndThird.setDisable(false);
            }
        semisGamesAndThirdPlacementsDisplayed = true;
    }
    private void addNamesToFinalsAndThirdPlace(){
        if(semisGamesAndThirdPlacementsDisplayed == true){
            //buttonList.get(15).setText(finalAndThirdPlaceGame.get(0).getWinner().getCountry());
            buttonList.get(15).setGame(finalAndThirdPlaceGame.get(0));
            buttonList.get(15).setGameOrder(4);
            

            //buttonList.get(33).setText(finalAndThirdPlaceGame.get(1).getWinner().getCountry());
            buttonList.get(33).setGame(finalAndThirdPlaceGame.get(1));
            buttonList.get(33).setGameOrder(4);
        }
        displayFinalAndThird.setDisable(true);
        simulateAll.setDisable(true);
    }
    
    private void simulateAll(){
        this.addNamesOfSixteenTeams();
        this.addNamesOfTheEightGames();
        this.addNamesToQuaterGames();
        this.addNamesToSemisGamesAndThirdPlacePlacements();
        this.addNamesToFinalsAndThirdPlace();
    }

    

        
        


    
}
