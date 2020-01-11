/**
 * Copyright (C) 2015 Asterios Raptis
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.alpharogroup.prop.to.yaml

import de.alpharogroup.prop.to.yaml.path.Element
import de.alpharogroup.prop.to.yaml.path.ElementType

class ValueAtIndex(private val index: Int) : Element {
    override fun toNavigationString(): String {
        return "[$index]"
    }

    override fun toPropString(): String? {
        return "[$index]"
    }

    override val type: ElementType
        get() = ElementType.VALUE_AT_INDEX

    override fun toIndex(): Int? {
        return index
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + index
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (javaClass != other.javaClass) return false
        val otherValue = other as ValueAtIndex
        return index == otherValue.index
    }

    override val valueCode: String?
        get() = "" + index

}