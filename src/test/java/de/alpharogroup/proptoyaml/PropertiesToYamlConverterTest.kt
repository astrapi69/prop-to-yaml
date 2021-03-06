package de.alpharogroup.proptoyaml

import de.alpharogroup.file.search.PathFinder
import de.alpharogroup.prop.to.yaml.PropertiesToYamlConverter.convert
import org.testng.Assert
import org.testng.annotations.Test
import java.io.File

class PropertiesToYamlConverterTest {
    @Test
    fun testConvertListObjects() {
        val expected: String
        val actual: String
        val propertiesFile = File(PathFinder.getSrcTestResourcesDir(), "list-or-array.properties")
        actual = convert(propertiesFile)
        expected = "application:\n" +
                "  property:\n" +
                "  - first\n" +
                "  - second\n" +
                "  public-paths:\n" +
                "  - first: /v1/first\n" +
                "    second: /v1/second\n" +
                "  - first: /v1/public/first\n" +
                "    second: /v1/public/second\n"
        Assert.assertEquals(actual, expected)
    }

    @Test
    fun testConvert() {
        val expected: String
        val actual: String
        val propertiesFile = File(PathFinder.getSrcTestResourcesDir(), "config.properties")
        actual = convert(propertiesFile)
        expected = "application:\n" +
                "  http:\n" +
                "    port: '18080'\n" +
                "  https:\n" +
                "    port: '18443'\n" +
                "configuration:\n" +
                "  type: DEVELOPMENT\n"
        Assert.assertEquals(actual, expected)
    }
}