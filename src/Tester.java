import java.io.IOException;
import java.util.ArrayList;

public class Tester {
    public static void main(String[] args) throws IOException {

        Simulator simulator = new Simulator();
        ArrayList<Team> teamList = simulator.Qualifiers();
        for(int i = 0; i < teamList.size(); i++){
            System.out.print(i + " ");
            System.out.println(teamList.get(i).getCountry());
        }

    }
}
