package de.alpharogroup.proptoyaml;

import de.alpharogroup.file.search.PathFinder;
import de.alpharogroup.prop.to.yaml.PropertiesToYamlConverter;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.*;

public class PropertiesToYamlConverterTest {

    @Test
    public void testConvertListObjects() {
        String expected;
        String actual;
        File propertiesFile = new File(PathFinder.getSrcTestResourcesDir(), "list-or-array.properties");
        actual = PropertiesToYamlConverter.convert(propertiesFile);
        expected = "application:\n" +
                "  property:\n" +
                "  - first\n" +
                "  - second\n" +
                "  public-paths:\n" +
                "  - first: /v1/first\n" +
                "    second: /v1/second\n" +
                "  - first: /v1/public/first\n" +
                "    second: /v1/public/second\n";
        assertEquals(actual, expected);
    }
    @Test
    public void testConvert()
    {
        String expected;
        String actual;
        File propertiesFile = new File(PathFinder.getSrcTestResourcesDir(), "config.properties");
        actual = PropertiesToYamlConverter.convert(propertiesFile);
        expected = "application:\n" +
                "  http:\n" +
                "    port: '18080'\n" +
                "  https:\n" +
                "    port: '18443'\n" +
                "configuration:\n" +
                "  type: DEVELOPMENT\n";
        assertEquals(actual, expected);
    }

}