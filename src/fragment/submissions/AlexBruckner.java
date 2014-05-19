package fragment.submissions;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * User: alexb
 * Date: 19/05/2014
 * Time: 13:49
 */
public class AlexBruckner {

    public AlexBruckner(String fragmentProblem) {
        // store individual fragments
        for (String fragment : fragmentProblem.split(";")) {
            registerFragment(fragment);
        }
    }

    private void registerFragment(String fragment) {

    }

    public String toString(){
        return "TODO";
    }


    public static void main(String[] args) {
        //TODO usage!!!
        try (BufferedReader in = new BufferedReader(new FileReader(args[0]))) {
            String fragmentProblem;
            while ((fragmentProblem = in.readLine()) != null) {
                System.out.println(new AlexBruckner(fragmentProblem));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
