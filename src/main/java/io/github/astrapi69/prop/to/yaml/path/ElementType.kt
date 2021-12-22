/**
 * Copyright (C) 2015 Asterios Raptis
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package io.github.astrapi69.prop.to.yaml.path

enum class ElementType(val value: Char) {
    VALUE_AT_KEY('.'), KEY_AT_KEY('&'), VALUE_AT_INDEX('[');
}
