package de.alpharogroup.prop.to.yaml.path

enum class ElementType(val value: Char) {
    VALUE_AT_KEY('.'), KEY_AT_KEY('&'), VALUE_AT_INDEX('[');
}