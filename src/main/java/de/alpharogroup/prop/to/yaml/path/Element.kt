package de.alpharogroup.prop.to.yaml.path

interface Element {
    fun toNavigationString(): String
    fun toPropString(): String?
    fun toIndex(): Int?
    val type: ElementType
    val valueCode: String?
    val typeCode: Char
        get() = type.value
}