import db.DBUnit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import other.Point;

import java.util.List;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

public class WebTest {
    @Before
    public void prepare() {
        setScriptingEnabled(false);
        setBaseUrl("http://localhost:8080/mispi_lab3/");
    }

    @Test
    public void testStart() {
        beginAt("start.xhtml");
        assertElementPresentByXPath("/html/head");
        assertTitleEquals("lab3");
        assertElementPresentByXPath("/html/body");
        assertElementPresentByXPath("/html/body/header");
        assertElementPresent("content");
        assertElementPresent("j_idt7");
        assertElementPresentByXPath("/html/body/div[contains(@id, 'content')]/input[contains(@class, 'submitButton')]");
        assertElementPresent("date");
    }

    @Test
    public void testIndex() {
        beginAt("index.xhtml");
        assertElementPresentByXPath("/html/head");
        assertTitleEquals("lab3");
        assertElementPresentByXPath("/html/body");
        assertElementPresentByXPath("/html/body/header");
        assertElementPresent("content");
        assertElementPresent("flushLeft");
        assertFormPresent("pointCheckForm");
        assertElementPresent("pointCheckForm:xField");
        assertElementPresent("pointCheckForm:yField");
        assertButtonPresent("pointCheckForm:commandButton");
        assertElementPresent("flushRight");
        assertElementPresent("canvas");
    }

    @Test
    public void testForm() {
        beginAt("index.xhtml");
        setTextField("pointCheckForm:xField", "0.5");
        setTextField("pointCheckForm:yField", "0.5");
        clickButton("pointCheckForm:commandButton");
        setTextField("pointCheckForm:xField", "1");
        setTextField("pointCheckForm:yField", "1");
        clickButton("pointCheckForm:commandButton");

        DBUnit dbUnit = new DBUnit();
        List<Point> list = dbUnit.getResults();

        Assert.assertEquals(0.5, list.get(list.size()-2).getX(), 0);
        Assert.assertEquals(0.5, list.get(list.size()-2).getY(), 0);
        Assert.assertEquals(2, list.get(list.size()-2).getR(), 0);
        Assert.assertTrue(list.get(list.size() - 2).isInArea());

        Assert.assertEquals(1, list.get(list.size()-1).getX(), 0);
        Assert.assertEquals(1, list.get(list.size()-1).getY(), 0);
        Assert.assertEquals(2, list.get(list.size()-1).getR(), 0);
        Assert.assertFalse(list.get(list.size() - 1).isInArea());
    }
}
