import java.io.IOException;
import java.util.ArrayList;

/**
 * Simulator class is designed to simulate the world cup in phases so that they can be simulated by steps by the GUI
 * Upon simulator being constructed the group stage is simulated. After that, individuals methods should be called to
 * simulate each round in this order:
 * {@link #simulateRoundOfSixteen()}
 * {@link #simulateQuarters()}
 * {@link #simulateSemis()}
 * {@link #simulateFinalAndThirdPlace()}
 * @author Saif Masoud, and Samuel Hernandez
 * TODO: Delete print statments when done with coding
 */
public class Simulator {

    // 8 groups with 4 teams each.
    private ArrayList<Group> groups;

    // The 32 participating teams.
    private ArrayList<Team> worldCupTeams;

    //TeamInfo object to get the teams info
    private TeamInfo teamInfo;

    //List of games played in the round of 16
    private ArrayList<Game> roundOf16Games;

    //List of games played in quarters
    private ArrayList<Game> quartersGames;

    //List of games played in semis
    private ArrayList<Game> semisGames;

    //First element is the final, second element is the game for the third place
    private ArrayList<Game> finalAndThirdGames;

    /**
     * TODO: When exception of team is figured out remove exception from constructor
     * Default constructor: Constructs a simulator, gets the world cup teams, and simulates the group stage
     */
    public Simulator() throws IOException {
        teamInfo = new TeamInfo();
        worldCupTeams = new ArrayList<>();
        loadWorldCupTeams();
        simulateGroups();
    }

    private void loadWorldCupTeams() {
        // This loads an arbitrary selection of 32 teams.
        // TODO: consider a method to select the 32 teams more realistically.
        int i=0;
        for (Team team : teamInfo.getTeamMap().values()) {
            worldCupTeams.add(team);
            i += 1;
            if (i==32) break;
        }
    }

    /**
     * Creates & simulates the 8 groups of the group-stage.
     */
    private void simulateGroups() {
        groups = new ArrayList<>();
        int nGroups = 8;
        int nTeams = worldCupTeams.size();
        int teamsPerGroup = nTeams / nGroups;

        // Dividing the teams over the groups.
        int teamIndex = 0;
        char curGroupChar = 'A';
        for (int i=0; i<nGroups; i++) {
            ArrayList<Team> curGroupTeams = new ArrayList<>();
            for (int j=0; j<teamsPerGroup; j++) {
                curGroupTeams.add(worldCupTeams.get(teamIndex));
                teamIndex += 1;
            }
            // Creating the group
            Group group = new Group(curGroupChar, curGroupTeams);
            groups.add(group);
            curGroupChar += 1;
        }
    }

    /**
     * Sets up the 8 games for round of sixteen.
     * Top team from group A plays against runner-up from group B.
     * Runner-up from group A plays against top team of group B.
     * Similarly C vs D, E vs F, G vs H
     * @return the games to be played
     * @author Samuel Hernandez and Saif Masoud
     */
    public ArrayList<Game> simulateRoundOfSixteen() {
        roundOf16Games = new ArrayList<Game>();
        for(int i = 0; i < 8; i++){

            //Get top ranking team in current group
            Team top = groups.get(i).getTeams().get(0);

            //Get runner up team from next group for A, C, E, G. OR from previous group for B, D, F, H
            Team runnerUp;
            if(i % 2 == 0)
                runnerUp = groups.get(i+1).getTeams().get(1);
            else
                runnerUp = groups.get(i-1).getTeams().get(1);

            roundOf16Games.add(new Game(top, runnerUp, false));
        }

        System.out.println("\nRounds of sixteen results");
        roundOf16Games.forEach(game -> System.out.println(game.getFinalScoreString()));
        return roundOf16Games;
    }

    /**
     * Upon being called simulates the quarter games and returns the games that were played.
     * Sets up the games by taking the winners of the first two round of sixteen winners, then the next two
     * and so on. (0 vs 1. 2 vs 3, 4 vs 5, 6 vs 7)
     * @return the array list of games that were played
     * @author Samuel Hernandez and Saif Masoud
     */
    public ArrayList<Game> simulateQuarters() {
        quartersGames = new ArrayList<>();
        int next = 0;
        for (int i = 0; i < 4; i++) {
            Team a = roundOf16Games.get(next).getWinner();
            Team b = roundOf16Games.get(next+1).getWinner();
            quartersGames.add(new Game(a, b, false));
            next += 2;
        }
        System.out.println("\nQuarters results");
        quartersGames.forEach(game -> System.out.println(game.toString()));
        return quartersGames;
    }

    /**
     * Upon being called simulates the semi final games and returns the games that were played.
     * Winner of quarters game 0 vs game 1, 2 vs 3.
     * @return the array list of games that were played
     * @author Samuel Hernandez and Saif Masoud
     */
    public ArrayList<Game> simulateSemis() {
        semisGames = new ArrayList<>();
        Game game1 = new Game(quartersGames.get(0).getWinner(), quartersGames.get(1).getWinner(), false);
        Game game2 = new Game(quartersGames.get(2).getWinner(), quartersGames.get(3).getWinner(), false);
        semisGames.add(game1);
        semisGames.add(game2);
        System.out.println("\nSemis results");
        semisGames.forEach(game -> System.out.println(game.toString()));
        return semisGames;
    }

    /**
     * Upon being called simulates the final and third place games and returns the games that were played.
     * Winner of quarters game 0 vs game 1. Loser of game 0 vs game 1.
     * @return the array list of games that were played
     * @author Samuel Hernandez and Saif Masoud
     */
    public ArrayList<Game> simulateFinalAndThirdPlace() {
        finalAndThirdGames = new ArrayList<>();
        Game finalGame = new Game(semisGames.get(0).getWinner(), semisGames.get(1).getWinner(), false);
        Game third = new Game(semisGames.get(0).getLoser(), semisGames.get(1).getLoser(), false);
        finalAndThirdGames.add(finalGame);
        finalAndThirdGames.add(third);
        System.out.println("\nFinal and third results");
        finalAndThirdGames.forEach(game -> System.out.println(game.toString()));
        return finalAndThirdGames;
    }

    /**
     * Per requested by front end:
     * Method returns the teams in the order they should appear in the GUI:
     * Groups from left to right, top to bottom.
     * @return the ordered list of teams that made it into the round of 16
     * @author Samuel Hernandez
     */
    public ArrayList<Team> getTeamsInOrderInRoundOfSixteen(){
        ArrayList<Team> teamsOnRoundOf16 = new ArrayList<>();
        for(int i = 0; i < 8; i++){

            //Add the team on top  of ranking team in current group
            Team leader = groups.get(i).getTeams().get(0);
            System.out.println(leader.getCountry() + " as group leader");

            //Get runner up team from next group for A, C, E, G. OR from previous group for B, D, F, H
            Team runnerUp;
            if(i % 2 == 0)
                runnerUp = groups.get(i+1).getTeams().get(1);
            else
                runnerUp = groups.get(i-1).getTeams().get(1);

            System.out.println(runnerUp.getCountry() + " as group runner up");
            teamsOnRoundOf16.add(leader);
            teamsOnRoundOf16.add(runnerUp);
        };
        return teamsOnRoundOf16;
    }

    /**
     * Gets the array with the 8 groups in the world cup A, B ... H
     * @return the list of groups
     */
    public ArrayList<Group> getGroups() {
        return groups;
    }

    /**
     * Gets the teams that qualified to the world cup
     * @return the list of qualified teams
     */
    public ArrayList<Team> getQualifiedTeams() {
        return worldCupTeams;
    }

    @Override
    public String toString() {
        return "Simulator{" +
                "groups=" + groups +
                ", roundOf16Games=" + roundOf16Games +
                ", quartersGames=" + quartersGames +
                ", semisGames=" + semisGames +
                ", finalAndThirdGames=" + finalAndThirdGames +
                '}';
    }

    //Equals and setters do not make sense so they are not implemented on purpose




}
