package fragment.submissions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * User: alexb
 * Date: 19/05/2014
 * Time: 13:49
 */
public class AlexBruckner {

    private static enum Direction {
        LEFT,
        RIGHT
    }

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

    private String fragmentProblem;
    private String fragmentSolution;

    private List<Fragment> fragments;

    public AlexBruckner(String fragmentProblem) {

        this.fragmentProblem = fragmentProblem;

        this.fragments = new ArrayList<>();

        // store individual fragments
        for (String fragment : fragmentProblem.split(";")) {
            this.fragments.add(new Fragment(fragment));
        }

        sortFragmentsByLength();

        solve();
    }

    private void solve() {

        // find all links to the right

        for (Fragment leftFragment : fragments) {

            for (Fragment rightFragment : fragments) {
                // don't test yourself
                if (leftFragment == rightFragment) {
                    continue;
                }

                // now find the highest match left to right
                if (match(leftFragment, rightFragment)) {
                    break;
                }

            }
        }

        // find links
        Fragment current = null;
        for (Fragment fragment : fragments) {
            if (fragment.getLeft() == null) {
                current = fragment;
                break;
            }
        }

        StringBuilder sb = new StringBuilder();
        int len = 0;
        while (current != null) {
            sb.append(current.fragment.substring(len));
            len = current.getMatchLength();
            current = current.getRight();
        }

        fragmentSolution = sb.toString();

    }

    private boolean match(Fragment left, Fragment right){
        // now find the highest match left to right
        for (String sub : getSubstrings(left.toString(), Direction.RIGHT)) {
            for (String match : getSubstrings(right.toString(), Direction.LEFT)) {
                if (sub.equals(match)){
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
        substrings.remove(substrings.size()-1);

        return substrings;

    }

    public String getFragmentProblem() {
        return fragmentProblem;
    }

    public String getFragmentSolution() {
        return fragmentSolution;
    }

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
