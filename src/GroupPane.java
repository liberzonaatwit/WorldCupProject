import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;


// TODO: Change class name to GroupPane
// TODO: Add description and comments
public class GroupPane extends GridPane {
        private Simulator simulator;
        private Team t;
        public GroupPane(Double height, Double width, Simulator simulator){
                this.simulator = simulator;
                this.stagemain();
                this.setMaxHeight(height);
                this.setMaxWidth(width);
        }


        public GridPane stagemain(){

                Group group1 = simulator.getGroups().get(0);
                TableView groupView1 = GroupPane.groupTable(group1,"Group A" );
                Group group2 = simulator.getGroups().get(1);
                TableView groupView2 = GroupPane.groupTable(group2,"Group B" );
                Group group3 = simulator.getGroups().get(2);
                TableView groupView3 = GroupPane.groupTable(group3,"Group C" );
                Group group4 = simulator.getGroups().get(3);
                TableView groupView4 = GroupPane.groupTable(group4,"Group D" );
                Group group5 = simulator.getGroups().get(4);
                TableView groupView5 = GroupPane.groupTable(group5,"Group E" );
                Group group6 = simulator.getGroups().get(5);
                TableView groupView6 = GroupPane.groupTable(group6,"Group F" );
                Group group7 = simulator.getGroups().get(6);
                TableView groupView7 = GroupPane.groupTable(group7,"Group G" );
                Group group8 = simulator.getGroups().get(7);
                TableView groupView8 = GroupPane.groupTable(group8,"Group H" );

                GridPane a = new GridPane();
                GridPane center = new GridPane();

                VBox one1  = new VBox(groupView1);
                VBox two2  = new VBox(groupView2);
                VBox three3  = new VBox(groupView3);
                VBox four4  = new VBox(groupView4);
                VBox five5  = new VBox(groupView5);
                VBox six6  = new VBox(groupView6);
                VBox seven7  = new VBox(groupView7);
                VBox eight8  = new VBox(groupView8);
                center.add(one1,1,0);
                center.add(two2,2,0);
                center.add(three3,1,1);
                center.add(four4,2,1);
                center.add(five5,1,2);
                center.add(six6,2,2);
                center.add(seven7,1,3);
                center.add(eight8,2,3);
                center.setHgap(10); //horizontal gap in pixels => that's what you are asking for
                center.setVgap(10); //vertical gap in pixels
                center.setPadding(new Insets(10, 10, 10, 10));
                center.setTranslateY(-55);
                this.setAlignment(Pos.CENTER);

                this.getChildren().addAll(center);
                return a;
        }
        public static TableView groupTable(Group group,String c ) {
                TableView tableView = new TableView<>();

                // Add the columns:
                TableColumn<Team, String> groupname   = new TableColumn<>(c);
                TableColumn<Team, String> countryCol = new TableColumn<>("Country");
                countryCol.setCellValueFactory(data ->
                        new SimpleStringProperty(data.getValue().getCountry()));

                TableColumn<Team, String> pointsCol = new TableColumn<>("Points");
                pointsCol.setCellValueFactory(data ->
                        new SimpleStringProperty(Integer.toString(data.getValue().groupPoints())));

                TableColumn<Team, String> winsCol = new TableColumn<>("Wins");
                winsCol.setCellValueFactory(data ->
                        new SimpleStringProperty(Integer.toString(data.getValue().groupWins())));

                TableColumn<Team, String> drawsCol = new TableColumn<>("Draws");
                drawsCol.setCellValueFactory(data ->
                        new SimpleStringProperty(Integer.toString(data.getValue().groupDraws())));

                TableColumn<Team, String> lossesCol = new TableColumn<>("Losses");
                lossesCol.setCellValueFactory(data ->
                        new SimpleStringProperty(Integer.toString(data.getValue().groupLosses())));

                TableColumn<Team, String> gf = new TableColumn<>("GF");
                gf.setCellValueFactory(data ->
                        new SimpleStringProperty(Integer.toString(data.getValue().groupWins())));

                TableColumn<Team, String> ga = new TableColumn<>("GD");
                ga.setCellValueFactory(data ->
                        new SimpleStringProperty(Integer.toString(data.getValue().groupDraws())));

                TableColumn<Team, String> gd = new TableColumn<>("GA");
                gd.setCellValueFactory(data ->
                        new SimpleStringProperty(Integer.toString(data.getValue().groupLosses())));



                groupname.getColumns().addAll(countryCol, winsCol, drawsCol, lossesCol,gf,ga,gd,pointsCol);
                tableView.getColumns().addAll(groupname);
                tableView.setFixedCellSize(25);
                tableView.prefHeightProperty().bind(Bindings.size(tableView.getItems()).multiply(tableView.getFixedCellSize()).add(55));
                tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                // Add the teams
                for (Team team : group.getTeams())
                        tableView.getItems().add(team);


                return tableView;

        }




}