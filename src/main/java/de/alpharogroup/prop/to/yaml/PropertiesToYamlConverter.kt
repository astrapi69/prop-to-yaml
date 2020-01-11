/**
 * Copyright (C) 2015 Asterios Raptis
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.alpharogroup.prop.to.yaml

import de.alpharogroup.prop.to.yaml.path.Item
import de.alpharogroup.prop.to.yaml.path.PathElementExtensions
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.StringReader
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

object PropertiesToYamlConverter {
    @JvmStatic
    fun convert(file: File): String {
        val p = Properties()
        val content = String(Files.readAllBytes(Paths.get(file.toURI())))
        p.load(StringReader(content))
        return convert(p)
    }
    @JvmStatic
    fun convert(p: Properties): String {
        val propertiesMap: MutableMap<String, Collection<String>> = HashMap()
        for ((key, value) in p) {
            val s: MutableSet<String> = LinkedHashSet()
            s.add(value as String)
            propertiesMap[key as String] = s
        }
        return convert(propertiesMap)
    }

    fun convert(properties: Map<String, Collection<String>>): String {
        if (properties.isEmpty()) {
            return ""
        }
        val root = Builder(Item())
        for ((key, value) in properties) {
            for (v in value) {
                root.addProperty(PathElementExtensions.fromProperty(key), v)
            }
        }
        val `object` = root.build()
        val options = DumperOptions()
        options.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
        options.isPrettyFlow = true
        val yaml = Yaml(options)
        return yaml.dump(`object`)
    }
}