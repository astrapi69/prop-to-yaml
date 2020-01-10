package de.alpharogroup.prop.to.yaml.path

import de.alpharogroup.collections.list.ListFactory
import de.alpharogroup.prop.to.yaml.ValueAtIndex
import de.alpharogroup.prop.to.yaml.ValueAtKey
import java.util.*

object PathElementExtensions {
    fun valueAt(key: String?): Element {
        return ValueAtKey(key)
    }

    fun valueAt(index: Int): Element {
        return ValueAtIndex(index)
    }

    fun fromProperty(propName: String?): Item {
        val elements = ListFactory.newArrayList<Element?>()
        val delimiter = ".[]"
        val tokens = StringTokenizer(propName, delimiter, true)
        try {
            while (tokens.hasMoreTokens()) {
                val token = tokens.nextToken(delimiter)
                if (token == "." || token == "]") {
                } else if (token == "[") {
                    val bracketed = tokens.nextToken("]")
                    if (bracketed == "]") {
                    } else {
                        try {
                            val index = bracketed.toInt()
                            elements.add(valueAt(index))
                        } catch (e: NumberFormatException) {
                            elements.add(valueAt(bracketed))
                        }
                    }
                } else {
                    elements.add(valueAt(token))
                }
            }
        } catch (e: NoSuchElementException) { // ignore
        }
        return Item(elements)
    }
}