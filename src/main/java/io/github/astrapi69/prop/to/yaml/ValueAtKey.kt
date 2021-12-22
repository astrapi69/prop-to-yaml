/**
 * Copyright (C) 2015 Asterios Raptis
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package io.github.astrapi69.prop.to.yaml

import io.github.astrapi69.prop.to.yaml.path.Element
import io.github.astrapi69.prop.to.yaml.path.ElementType

class ValueAtKey(override val valueCode: String?) : Element {
    override fun toNavigationString(): String {
        return if (valueCode!!.indexOf('.') >= 0) {
            "[" + valueCode + "]"
        } else "." + valueCode
    }

    override fun toPropString(): String? {
        return valueCode
    }

    override val type: ElementType
        get() = ElementType.VALUE_AT_KEY

    override fun toIndex(): Int? {
        return null
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + if (valueCode == null) 0 else valueCode.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        if (javaClass != other.javaClass) return false
        val otherValue = other as ValueAtKey
        return if (valueCode == null) {
            otherValue.valueCode == null
        } else valueCode == otherValue.valueCode
    }

}
