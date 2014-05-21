package fragment.submissions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * CHP Tchnical Test
 * <p/>
 * Write a program to reassemble a given set of text fragments into their original sequence. For this challenge your program should have a main method accepting one argument – the path to a well-formed UTF-8 encoded text file. Each line in the file represents a test case of the main functionality of your program: read it, process it and println to the console the corresponding defragmented output.
 * Each line contains text fragments separated by a semicolon, ‘;’. You can assume that every fragment has length at least 2.
 * <p/>
 * Example input 1:
 * O draconia;conian devil! Oh la;h lame sa;saint!
 * <p/>
 * Example output 1:
 * O draconian devil! Oh lame saint!
 * <p/>
 * Example input 2:
 * m quaerat voluptatem.;pora incidunt ut labore et d;, consectetur, adipisci velit;olore magnam aliqua;idunt ut labore et dolore magn;uptatem.;i dolorem ipsum qu;iquam quaerat vol;psum quia dolor sit amet, consectetur, a;ia dolor sit amet, conse;squam est, qui do;Neque porro quisquam est, qu;aerat voluptatem.;m eius modi tem;Neque porro qui;, sed quia non numquam ei;lorem ipsum quia dolor sit amet;ctetur, adipisci velit, sed quia non numq;unt ut labore et dolore magnam aliquam qu;dipisci velit, sed quia non numqua;us modi tempora incid;Neque porro quisquam est, qui dolorem i;uam eius modi tem;pora inc;am al
 * <p/>
 * Example output 2:
 * Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem.
 * <p/>
 * Examples:
 * - "ABCDEF" and "DEFG" overlap with overlap length 3
 * - "ABCDEF" and "XYZABC" overlap with overlap length 3
 * - "ABCDEF" and "BCDE" overlap with overlap length 4
 * - "ABCDEF" and "XCDEZ" do *not* overlap (they have matching characters in the middle, but the overlap does not extend to the end of either string).
 * <p/>
 * <p/>
 * User: alexb
 * Date: 19/05/2014
 * Time: 13:49
 */
public class AlexBruckner {

    /**
     * Direction, cen be LEFT or RIGHT
     */
    private static enum Direction {
        LEFT,
        RIGHT
    }

    /**
     * Holds a single fragment string as well as links to left and right matching fragments
     * The matchLength is stored here too to indicate the char count of the overlap to
     * the matching fragment on the right.
     */
    private static class Fragment {
        private String fragment;
        private Fragment left, right;
        private int matchLength;

        public Fragment(String fragment) {
            this.fragment = fragment;
        }

        public String toString() {
            return fragment;
        }

        public void setRight(Fragment right) {
            this.right = right;
        }

        public void setLeft(Fragment left) {
            this.left = left;
        }

        private Fragment getLeft() {
            return left;
        }

        private void setMatchLength(int length) {
            this.matchLength = length;
        }

        private int getMatchLength() {
            return matchLength;
        }

        private Fragment getRight() {
            return right;
        }
    }

    /**
     * the original problem line
     */
    private String fragmentProblem;

    /**
     * the correctly ordered solution (excluding the overlaps)
     */
    private String fragmentSolution;

    /**
     * here we store semi-colon separated the fragment line
     */
    private List<Fragment> fragments;

    /**
     * @param fragmentProblem - the problem line
     */
    public AlexBruckner(String fragmentProblem) {

        this.fragmentProblem = fragmentProblem;

        this.fragments = new ArrayList<>();

        /*
         * store individual fragments
         */
        for (String fragment : fragmentProblem.split(";")) {
            this.fragments.add(new Fragment(fragment));
        }

        /*
         * first sort the fragments (longest first)
         */
        sortFragmentsByLength();

        /*
         * solve problem with what we've got
         */
        solve();
    }

    /**
     * here we solve the problem using the prepared input line
     */
    private void solve() {
        matchAllFragments();
        Fragment current = findTheFirstFragment();
        createSolutionString(current);
    }

    private void createSolutionString(Fragment current) {
        // create the solution string
        StringBuilder sb = new StringBuilder();
        int len = 0;
        while (current != null) {
            sb.append(current.fragment.substring(len));
            len = current.getMatchLength();
            current = current.getRight();
        }

        fragmentSolution = sb.toString();

    }

    private Fragment findTheFirstFragment() {
        // find the beginning fragment (ie the one that has no left link)
        Fragment current = null;
        for (Fragment fragment : fragments) {
            if (fragment.getLeft() == null) {
                current = fragment;
                break;
            }
        }
        return current;
    }

    /**
     * for all fragments, check all other fragments on whether they
     * have matching start or end substrings and link them accordingly
     */
    private void matchAllFragments() {
        for (Fragment leftFragment : fragments) {

            for (Fragment rightFragment : fragments) {
                // don't compare same fragment
                if (leftFragment == rightFragment) {
                    continue;
                }

                // now find the highest match left to right
                if (match(leftFragment, rightFragment)) {
                    break;
                }
            }
        }
    }

    /**
     * this compares two fragments trying the highest substrings (left to right) first
     */
    private boolean match(Fragment left, Fragment right) {
        // now find the highest match left to right

        for (String sub : getSubstrings(left.toString(), Direction.RIGHT)) {
            for (String match : getSubstrings(right.toString(), Direction.LEFT)) {
                // if we have found a match with the right substring length,
                // link and remember that length
                if (sub.equals(match)) {
                    if (left.getRight() == null) {
                        left.setRight(right);
                        left.setMatchLength(match.length());
                    }
                    if (right.getLeft() == null) {
                        right.setLeft(left);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private void sortFragmentsByLength() {
        Collections.sort(fragments, new Comparator<Fragment>() {
            @Override
            public int compare(Fragment s1, Fragment s2) {
                return s2.fragment.length() - s1.fragment.length();
            }
        });
    }

    /**
     * generate a list of substrings for a given fragment,
     * the direction determines whether we want substrings to the right or to the left.
     */
    private List<String> getSubstrings(String fragment, Direction direction) {

        List<String> substrings = new ArrayList<>();

        for (int i = 0; i < fragment.length(); i++) {
            String overlap;
            if (direction == Direction.LEFT) {
                overlap = fragment.substring(0, fragment.length() - i);
            } else {
                overlap = fragment.substring(i);
            }
            substrings.add(overlap);
        }

        // don't keep the total overlap
        // we assume maximum overlap is total size - 1
        substrings.remove(0);
        substrings.remove(substrings.size() - 1);

        return substrings;

    }

    public String getFragmentProblem() {
        return fragmentProblem;
    }

    public String getFragmentSolution() {
        return fragmentSolution;
    }

    /**
     * main method as expected by the bot
     * args[0] is the input file
     */
    public static void main(String[] args) {
        try (BufferedReader in = new BufferedReader(new FileReader(args[0]))) {
            String fragmentProblem;
            while ((fragmentProblem = in.readLine()) != null) {
                System.out.println(new AlexBruckner(fragmentProblem).getFragmentSolution());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
