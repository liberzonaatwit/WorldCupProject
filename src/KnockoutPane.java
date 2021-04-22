import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import java.util.ArrayList;
/** note for Zach (delete later):
 *   this class will create the pane for the knockout tab,
 *   Main is composed of this (I'm pretty sure)
 *      - Justin V
 */
public class KnockoutPane extends Pane {

    private ArrayList<Button> buttonList = new ArrayList<>();
    

    public KnockoutPane(){
        this.createBracket();
    }

    private void createBracket(){
        int x = 5;                              //The initial X cord of the top left bracket;
        int y = 5;                              //The initial y cord of the top left bracket;
        int scalingFactor = 50;                 //Scaling factor of the bracket, increase = bigger
        int yLength = scalingFactor/2;          //The length of the line going up and down on the L shape. **MIGHT GET REMOVED / TWEAKED since the scaling doesn't 100% fit with it.**
        int horizontalLength = scalingFactor*4;            //The length of the line going left and right on the L shape.  **MIGHT GET REMOVED // TWEAKED since the scaling isn't 100% with it.**
        int yIncrement = scalingFactor*2;       //How much will the next tier bracket be moved down. 
        int yCordsAtTierZero = y;               //Since we change the y cord, we're setting base y at tierZero          
        int yCordsAtTierOne = yCordsAtTierZero + scalingFactor; //Tier zero gets used by the scalingFactor to produce the proper y cords for the tier 
        int yCordsAtTierTwo = yCordsAtTierOne + scalingFactor*2;                         
        int yCordsAtTierThree = yCordsAtTierTwo + scalingFactor*4;
        int yIncrementIncrease = yIncrement;                    //Since we change the yIncrement, we use incrementIncrease to keep the base value; technically can be removed but eh
        int buttonSizeX = 1;                                    //Button sizes, more convenient
        int buttonSizeY = 1;

        for(int i = 1; i < 32; i++){
            Button button = new Button();
            button.setMinSize(buttonSizeX, buttonSizeY);
            if(i < 15){
                if(i % 2 == 1){
                    button.setLayoutX(x);
                    button.setLayoutY(y);
                    drawLines(x, y, false, false, yLength, horizontalLength);
                    y += yIncrement;
                }
                else{
                    button.setLayoutX(x);
                    button.setLayoutY(y);
                    drawLines(x, y, false, true, yLength, horizontalLength);
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
                button.setLayoutX(x);
                button.setLayoutY(y);
                drawLines(x, y, false, true, yLength, horizontalLength);
                x += horizontalLength;
                y = yCordsAtTierThree - yLength;                               //THIS IS THE SPECIAL CASE. since the button needs to be **UP**
            }
            else if(i == 16){
                button.setLayoutX(x);
                button.setLayoutY(y);
                x += horizontalLength;
                y = yCordsAtTierThree;                                         
            }
            else if(i == 17){
                button.setLayoutX(x);
                button.setLayoutY(y);
                drawLines(x, y, true, true, yLength, horizontalLength);
                x += horizontalLength;
                y = yCordsAtTierTwo;                                    
                yLength -= yIncrementIncrease/2;
            }
            else if(i > 17){
                if(i % 2 == 1){
                    button.setLayoutX(x);
                    button.setLayoutY(y);
                    drawLines(x, y, true, true, yLength, horizontalLength);
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
                    button.setLayoutX(x);
                    button.setLayoutY(y);
                    drawLines(x, y, true, false, yLength, horizontalLength);
                    y += yIncrement;
                }
            }
            buttonList.add(button);
            this.getChildren().add(button);
        }
    }

    private void drawLines(int x, int y, boolean isLeft, boolean isUp, int yLength, int horizontalLength){
        Line line1;
        Line line2;
        int x_length;

        if(isLeft){
            x_length = x - horizontalLength;
            line1 = new Line(x, y, x_length, y);
        }
        else{
            x_length = x + horizontalLength;
            line1 = new Line(x, y, x_length, y);
        }

        if(isUp){
            line2 = new Line(x_length, y, x_length, y - yLength);
        }
        else{
            line2 = new Line(x_length, y, x_length, y + yLength);
        }

        this.getChildren().addAll(line1, line2);
    }
}