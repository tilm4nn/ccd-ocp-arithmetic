package net.objectzoo.ccd.ocp;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by tilmann on 21.06.2016.
 */
public class ArithmeticTest
{
    @Test
    public void test()
    {
        TestContainer testContainer = new TestContainer();

        testContainer.performParserTest(testContainer.getDefaultExpression());

        assertThat(testContainer.getResult(),
            is("((VAR01+3-VAR03)*VAR06/(7.7+VAR10)/VAR05)/2.1 = 0.014172335600907042"));
    }
}
