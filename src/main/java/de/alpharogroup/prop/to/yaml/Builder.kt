package de.alpharogroup.prop.to.yaml

import de.alpharogroup.prop.to.yaml.path.Element
import de.alpharogroup.prop.to.yaml.path.Item
import java.util.*
import java.util.stream.Collectors

class Builder(val item: Item?) {
    val scalars: MutableList<String> = ArrayList()
    val listItems: MutableMap<Int?, Builder> = TreeMap()
    val mapEntries: MutableMap<String?, Builder> = TreeMap()
    fun addProperty(item: Item?, value: String) {
        if (item!!.isEmpty) {
            scalars.add(value)
        } else {
            val element = item.getElement(0)
            val subBuilder: Builder?
            subBuilder = if (element is ValueAtIndex) {
                getSubBuilder(listItems, element, element.toIndex())
            } else {
                getSubBuilder(mapEntries, element, element!!.toPropString())
            }
            subBuilder!!.addProperty(item.dropFirst(1), value)
        }
    }

    private fun <T> getSubBuilder(subBuilders: MutableMap<T, Builder>, element: Element?, key: T): Builder? {
        var existing = subBuilders[key]
        if (existing == null) {
            subBuilders[key] = Builder(item!!.append(element)).also { existing = it }
        }
        return existing
    }

    fun build(): Any {
        if (!scalars.isEmpty()) {
            if (listItems.isEmpty() && mapEntries.isEmpty()) {
                return if (scalars.size > 1) {
                    scalars
                } else {
                    scalars[0]
                }
            } else {
                scalars.clear()
            }
        }
        if (!listItems.isEmpty() && !mapEntries.isEmpty()) {
            for ((key, value) in listItems) {
                mapEntries[key.toString()] = value
            }
            listItems.clear()
        }
        return if (!listItems.isEmpty()) {
            val list: MutableList<Any> = listItems.values.stream().map { childBuilder: Builder -> childBuilder.build() }.collect(Collectors.toList())
            list
        } else {
            val map: MutableMap<String?, Any> = TreeMap()
            for ((key, value) in mapEntries) {
                map[key] = value.build()
            }
            map
        }
    }

}