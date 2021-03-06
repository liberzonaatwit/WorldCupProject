import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * This is the application's main class, run it to view the simulator. It has a
 * starterPane that displays an image and has a "Start" button. It also creates a
 * rootPane which that contains tabs for each aspect of the World Cup (all national
 * teams, the tournament group stage, and the tournaments knock out stage). It also
 * has a "Help" button to quickly break down what each feature of the simulator. A
 * "Reset" button was added to relaunch the simulator without having to close the
 * application.
 * @author Harjit Singh, Justin Valas, Samuel Hernandez, Ariel Liberzon
 */

public class WorldCupGUI extends Application {

    private BorderPane rootPane = new BorderPane();
    private GridPane starterPane = new GridPane();
    private Scene scene = new Scene(rootPane);
    private Simulator simulator;
    private VBox vBox;
    private Stage window;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        primaryStage.setTitle("World Cup");
        // Try and catch added by Harjit Singh
        try {
            primaryStage.getIcons().add(new Image("Images/logo2.png"));
        }
        catch (Exception e) {
            showError(new Exception("Can't find "+e.getMessage(),e),true);
        }
        showIntroScene(primaryStage);
        primaryStage.setMaximized(true);
    }

    /**
     * @author Harjit Singh
     * Welcome scene with the Background Start button.
     * Create the scene with the TabPane.
     * @param window
     */
    private void showIntroScene(Stage window) {
        // Try and catch added by Harjit Singh
        try {
            Image img = new Image("Images/two.jpg");
            starterPane.setBackground(new Background(new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        }
        catch (Exception e) {
            showError(new Exception("Can't find "+e.getMessage(),e),true);
        }
        Button startButton = new Button("Start");
        startButton.setBackground(new Background(new BackgroundFill(Color.LIGHTSTEELBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        startButton.setFont(Font.font("Arial Black", 18));
        startButton.setTextFill(Color.WHITE);
        starterPane.setAlignment(Pos.CENTER);
        starterPane.add(startButton, 0, 1);
        starterPane.setAlignment(Pos.CENTER);
        window.setScene(new Scene(starterPane));
        window.setMaximized(true);
        window.show();
        initialize();
        startButton.setOnAction(e -> window.setScene(scene));
    }

    /**
     * @author Harjit Singh
     * Method to create the tab for Teams, Group Stage and Knockout Stage
     * @param height
     * @param width
     * @return tabPane
     */
    private TabPane createTabPane(Double height, Double width){

        TabPane tabPane = new TabPane();
        Tab qualifierStageTab = new Tab("   Teams   ",new TeamsPane(height, width,simulator));
        Tab groupStageTab = new Tab("   Group Stage   ",new GroupsPane(height, width, simulator));
        Tab knockoutStageTab = new Tab("   Knockout Stage  ",new KnockoutPane(height, width, simulator));
        groupStageTab.setClosable(false);
        knockoutStageTab.setClosable(false);
        qualifierStageTab.setClosable(false);
        tabPane.getTabs().addAll(qualifierStageTab,groupStageTab,knockoutStageTab);
        return tabPane;
    }

    /**
     * @author Ariel Liberzon
     * A function which produces an HBox consisting of multple buttons
     * @return HBox ButtonBar
     */
    private HBox createButtonBar() {
        HBox buttonBar = new HBox();
        buttonBar.setPadding(new Insets(5, 10, 5, 10));
        buttonBar.setSpacing(10);
        // Try and catch added by Harjit Singh
        try {
            buttonBar.setBackground(new Background(new BackgroundImage(new Image("Images/grass.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT))); //new BackgroundSize(width, height,true,true,true,true)
        }
        catch (Exception e) {
            showError(new Exception("Can't find "+e.getMessage(),e),true);
        }
        Button helpButton = new Button("Help");;
        Button resetButton = new Button("Reset");

        //Added by Samuel Hernandez
        resetButton.setOnAction(e -> initialize());
        helpButton.setOnAction(e -> help());
        buttonBar.getChildren().addAll(helpButton, resetButton);
        return buttonBar;
    }

    /**
     * @author Samuel Hernandez
     * Method sets up and starts the buttons, tab and pane that holds and
     * shows the world cup tournament information It also creates the
     * simulator object and simulates the tournament. Method designed to
     * allow to reset the game without showing the welcome screen again.
     */
    private void initialize(){
        simulator = new Simulator();
        vBox = new VBox();
        HBox buttonBar = createButtonBar();
        TabPane tabs = createTabPane(window.getHeight() - 100, window.getWidth());
        vBox.getChildren().addAll(buttonBar, tabs);
        rootPane.setTop(vBox);
    }

    /**
     * @author Harjit Singh, Justin Valas
     * Displays Alert message to Help the user and them
     * user-friendly experience
     * @return alert
     */
    private Alert help(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText(null);
        try {  // Try and catch added by Harjit Singh
            ImageView graphic = new ImageView(new Image("Images/logo2.png"));
            graphic.setFitHeight(60);
            graphic.setFitWidth(60);
            alert.setGraphic(graphic);
        }
        catch (Exception e) {
            showError(new Exception("Can't find "+e.getMessage(),e),true);
        }


        alert.setContentText("WE ARE HERE TO HELP YOU" +
                "\n-Click on the \"Teams\" tab to see all confederations" +
                "\n-Search your team in the search-bar by country name or country code"+
                "\n-Click on the \"Group Stage\" tab to see all 8 groups" +
                "\n-Click on the \"Knockout Stage\" tab to simulate for the winner" +
                "\n-Click on the \"Reset\" button to reset the simulator" +
                "\n\nTEAMS TAB:" +
                "\n-Highlighted teams are the ones that have qualified" +
                "\n-Click on the buttons at the top to switch between confederations" +
                "\n-Search for any team in the search-bar using country name or country code" +
                "\n\nGROUP STAGE TAB:" +
                "\n-There are eight colored groups with the flags of each group above each table" +
                "\n-Click on a flag to see all the group stage games for that team" +
                "\n-The tables hold each team's results for the group stage:" +
                "\n    GA(Goals Against) - total goals scored against this team" +
                "\n    GF(Goals For) - total goals scored by this team" +
                "\n    GD(Goal Difference) - GF minus GA" +
                "\n    Points - teams get +3 for wins and +1 for ties" +
                "\n\nKNOCKOUT STAGE TAB:" +
                "\n-Click the buttons at the top to reveal teams on the bracket" +
                "\n-Click on a team to show all of its knockout games up to that point on the bracket" +
                "as well as all of its group stage games" +
                "\n-Hold your cursor over a team to see a tooltip of the team's info");

        alert.show();
        return alert;
    }

    /**
     * @author Harjit Singh
     * The Exception handler
     * Displays a error message to the user
     * and if the error is bad enough closes the program
     * @param fatal true if the program should exit. false otherwise
     */
    private void showError(Exception e,boolean fatal){
        String msg = e.getMessage();
        if(fatal){
            msg = msg + " \n\nthe program will now close";
        }
        Alert alert = new Alert(Alert.AlertType.ERROR,msg);
        alert.setResizable(true);
        alert.getDialogPane().setMinWidth(420);
        alert.setTitle("Error");
        alert.setHeaderText("something went wrong");
        alert.showAndWait();
        if(fatal){
            System.exit(666);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
