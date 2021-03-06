package fragment.submissions;

import junit.framework.Assert;
import org.junit.Test;

/**
 * User: alexb
 * Date: 19/05/2014
 * Time: 14:16
 */
public class AlexBrucknerTest {

    @Test
    public void test1(){
        String example = "O draconia;conian devil! Oh la;h lame sa;saint!";
        String expected = "O draconian devil! Oh lame saint!";
        AlexBruckner problem = new AlexBruckner(example);
        System.out.println("Problem: " + problem.getFragmentProblem());
        System.out.println("Solution: " + problem.getFragmentSolution());
        Assert.assertEquals("not right", expected, String.valueOf(problem.getFragmentSolution()));
    }

    @Test
    public void test2(){
        String example = "m quaerat voluptatem.;pora incidunt ut labore et d;, consectetur, adipisci velit;olore magnam aliqua;idunt ut labore et dolore magn;uptatem.;i dolorem ipsum qu;iquam quaerat vol;psum quia dolor sit amet, consectetur, a;ia dolor sit amet, conse;squam est, qui do;Neque porro quisquam est, qu;aerat voluptatem.;m eius modi tem;Neque porro qui;, sed quia non numquam ei;lorem ipsum quia dolor sit amet;ctetur, adipisci velit, sed quia non numq;unt ut labore et dolore magnam aliquam qu;dipisci velit, sed quia non numqua;us modi tempora incid;Neque porro quisquam est, qui dolorem i;uam eius modi tem;pora inc;am al";
        String expected = "Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem.";
        AlexBruckner problem = new AlexBruckner(example);
        System.out.println("Problem: " + problem.getFragmentProblem());
        System.out.println("Solution: " + problem.getFragmentSolution());
        Assert.assertEquals("not right", expected, String.valueOf(problem.getFragmentSolution()));
    }


}
