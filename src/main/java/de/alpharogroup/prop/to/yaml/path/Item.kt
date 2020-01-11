/**
 * Copyright (C) 2015 Asterios Raptis
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.alpharogroup.prop.to.yaml.path

import de.alpharogroup.collections.list.ListExtensions
import de.alpharogroup.collections.list.ListFactory

class Item {
    private val elements: MutableList<Element?>

    constructor(elements: MutableList<Element?>) {
        this.elements = elements
    }

    constructor() {
        elements = ListFactory.newArrayList()
    }

    fun size(): Int {
        return elements.size
    }

    fun getElement(element: Int): Element? {
        return if (element >= 0 && element < elements.size) {
            elements[element]
        } else null
    }

    fun append(s: Element?): Item {
        elements.add(s)
        return Item(elements)
    }

    fun dropFirst(dropCount: Int): Item {
        if (dropCount >= size()) {
            return Item()
        }
        if (dropCount == 0) {
            return this
        }
        ListExtensions.removeFirst(elements)
        return Item(elements)
    }

    val isEmpty: Boolean
        get() = elements.isEmpty()
}