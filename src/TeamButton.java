import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * TeamButton creates a special button that holds the Team object. It holds a game object as well, but it's mostly used for team.
 * Sets the name and flag when setTeam() is called
 * 
 * @author Shane Callahan, Justin Valas, Ariel Liberzon
 */
public class TeamButton extends Button{
    private Team team;
    private Game game; //the point of this class, to store the information that the button is going to display
    private int gameOrder; //If it's 1, it's a game in the eight, 2 if quaterly, 3 if semi, or third place, 4 if winner or third place winner
    private ImageView flag;

    public TeamButton(){
        super();
        this.setOnMouseClicked(clicked);
    }
    public TeamButton(String buttonName){
        super(buttonName);
        this.setOnMouseClicked(clicked);
    }

    public TeamButton(String buttonName, Node node){
        super(buttonName, node);
        this.setOnMouseClicked(clicked);
    }

    private Tooltip createToolTip(TeamButton button) {
        Tooltip tooltip = new Tooltip();
        if(button.getTeam() != null){
            tooltip.setText(button.getTeam().toString());
            tooltip.setGraphic(button.getTeam().getFlag());           //THESE ARE REDUNDENT IF WE SHOW IT IN BUTTON NAME
        }
        else if(button.getGame() != null){
            tooltip.setText(button.getGame().getWinner().toString());
            tooltip.setGraphic(button.getGame().getWinner().getFlag());
        }
        return tooltip;
    }

    private Tooltip createToolTip(TeamButton button, Boolean thirdPlaceWinner) {
        Tooltip tooltip = new Tooltip();
        if(button.getTeam() != null){
            tooltip.setText(button.getTeam().toString());
            tooltip.setGraphic(button.getTeam().getFlag());
        }
        else if(button.getGame() != null){
            if(thirdPlaceWinner){
            tooltip.setText(button.getGame().getWinner().toString());
            tooltip.setGraphic(button.getGame().getWinner().getFlag());
        }
            else{
                tooltip.setText(button.getGame().getLoser().toString()); 
            }
        }
        return tooltip;
    }

    public void setTeam(Team team) {
        this.team = team;
        this.setFlag(team.getFlag().getImage());
        this.setGraphic(flag);
        this.setTooltip(createToolTip(this));
        this.setText(team.getCountry());
        this.setTextAlignment(TextAlignment.CENTER);
    }
    public void setGame(Game game) {
        this.game = game;
        this.team = game.getWinner();
        this.setFlag(game.getWinner().getFlag().getImage());
        this.setGraphic(flag);
        this.setTooltip(createToolTip(this));
        this.setText(game.getWinner().getCountry());
    }
    public void setGame(Game game, Boolean thirdPlaceWinner){//Special case for third place
        if(thirdPlaceWinner){
        this.game = game;
        this.team = game.getWinner();
        this.setFlag(game.getWinner().getFlag().getImage());
        this.setGraphic(flag);
        this.setTooltip(createToolTip(this,true));
        this.setText(game.getWinner().getCountry());
        }
        else if(!thirdPlaceWinner){
            this.game = game;
            this.team = game.getLoser();
            this.setFlag(game.getLoser().getFlag().getImage());
            this.setGraphic(flag);
            this.setTooltip(createToolTip(this,false));
            this.setText(game.getLoser().getCountry()); 
        }
    }
    public void setGameOrder(int gameOrder) {
        this.gameOrder = gameOrder;
    }
    public void setFlag(Image flag) {
       this.flag = new ImageView(flag);
       this.flag.setFitHeight(20);//change these values to change the scaling
       this.flag.setFitWidth(30);
    }
    public Team getTeam() {
        return team;
    }
    public Game getGame() {
        return game;
    }
    public int getGameOrder() {
        return gameOrder;
    }

    public void removeName(){
        this.setText("");
    }
    

    private EventHandler<MouseEvent> clicked = mouseEvent -> {
        TeamButton button = (TeamButton) mouseEvent.getSource();
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {//LEFT CLICK
            if(button.getGame() != null){
                VBox gameBox = new VBox();
                Label knockoutLabel = new Label("Knockout Games: ");
                knockoutLabel.setFont(new Font(knockoutLabel.getFont().toString(),16));
                gameBox.getChildren().addAll(knockoutLabel);
                gameBox.setAlignment(Pos.CENTER);//makes everything inside the VBox centered
                //gameBox.setAlignment(Pos.CENTER);
                for (int i = button.getGameOrder(); i > 0; i--) {
                   //Gives an arrayList, call the array List from +3, 
                    gameBox.getChildren().addAll(button.getTeam().getGames().get(i+2).getScoreDisplay());
                }
               
                Label groupLabel = new Label("\n Group Stage Games: \n");
                groupLabel.setFont(new Font(groupLabel.getFont().toString(),16));
                //label.setAlignment(Pos.CENTER);
                gameBox.getChildren().addAll(groupLabel);
                for (int i = 2; i >= 0; i--) {
                    //Gives an arrayList, call the array List from 0 - 3,  
                    gameBox.getChildren().addAll(button.getTeam().getGames().get(i).getScoreDisplay());
                 }
                 this.showMessageDialogue(gameBox);
            }
            else if(button.getTeam() != null){
                VBox gameBox = new VBox();
                gameBox.setAlignment(Pos.CENTER);
                Label label = new Label("Group Stage Games: \n");
                label.setAlignment(Pos.CENTER);
                gameBox.getChildren().addAll(label);
                for (int i = 2; i >= 0; i--) {
                    //Gives an arrayList, call the array List from 0 - 3, 
                    gameBox.getChildren().addAll(button.getTeam().getGames().get(i).getScoreDisplay());
                 }
                 this.showMessageDialogue(gameBox);
            }
                
            }
        else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {//RIGHT CLICK
            //useless but kept here incase we need it
            }
        };

    private void showMessageDialogue(Pane test) {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Games"); // come back to

        Pane pane = new Pane();


        pane.getChildren().add(test);
        stage.getIcons().add(new Image("Images/logo2.png"));
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();

    }
}
