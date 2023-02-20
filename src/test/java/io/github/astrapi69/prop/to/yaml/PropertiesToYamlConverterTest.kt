/**
 * Copyright (C) 2015 Asterios Raptis
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package io.github.astrapi69.prop.to.yaml

import io.github.astrapi69.file.search.PathFinder
import io.github.astrapi69.prop.to.yaml.PropertiesToYamlConverter.convert
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

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
        assertEquals(actual, expected)
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
        assertEquals(actual, expected)
    }

    @Test
    fun testConvertPropertiesAsString() {
        var expected: String
        var actual: String
        var propertiesFile = File(PathFinder.getSrcTestResourcesDir(), "config.properties")
        var content = String(Files.readAllBytes(Paths.get(propertiesFile.toURI())))
        actual = convert(content)
        expected = "application:\n" +
                "  http:\n" +
                "    port: '18080'\n" +
                "  https:\n" +
                "    port: '18443'\n" +
                "configuration:\n" +
                "  type: DEVELOPMENT\n"
        assertEquals(actual, expected)

        propertiesFile = File(PathFinder.getSrcTestResourcesDir(), "list-or-array.properties")
        content = String(Files.readAllBytes(Paths.get(propertiesFile.toURI())))
        actual = convert(content)
        expected = "application:\n" +
                "  property:\n" +
                "  - first\n" +
                "  - second\n" +
                "  public-paths:\n" +
                "  - first: /v1/first\n" +
                "    second: /v1/second\n" +
                "  - first: /v1/public/first\n" +
                "    second: /v1/public/second\n"
        assertEquals(actual, expected)
    }
}
