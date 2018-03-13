package com.shepard.parser

import java.io.File
import java.io.InputStream
import java.util.*

interface IXmlParser {
    val openTag: Regex
    val enclosingTag: Regex
    val singleTag: Regex
    val attrRx: Regex
    val nameRx: Regex

    fun parseXml(path: String): Tag = parseXml(File(path).inputStream())

    fun parseXml(inputStream: InputStream): Tag {
        val stack = Stack<Tag>()
        inputStream.bufferedReader().forEachLine {
            when {
                openTag in it -> {
                    val tag = createTag(openTag, it)
                    if (!stack.empty()) stack.peek().children += tag
                    stack.push(tag)
                }
                enclosingTag in it -> if (stack.size > 1) stack.pop()
                singleTag in it -> {
                    val tag = createTag(singleTag, it)
                    if (!stack.empty()) stack.peek().children += tag
                }
            }
        }
        return stack.pop()
    }

    private fun createTag(regex: Regex, src: String): Tag {
        val rawTag = regex.find(src)?.value ?: throw IllegalArgumentException(src)
        val name = nameRx.find(rawTag)?.value ?: throw IllegalArgumentException(src)
        val attrs: Map<String, String> = attrRx.findAll(rawTag).toAttributes()
        return Tag(name, attrs)
    }
}

class XmlParser(override val openTag: Regex = Regex(" *<\\w+[\\w+=\"\\w+\" \\?*]+>"),
                override val enclosingTag: Regex = Regex(" *</\\w+>"),
                override val singleTag: Regex = Regex(" *<\\w+[\\w+=\"\\w+\" ]+/>"),
                override val attrRx: Regex = Regex("\\w+=\"[\\w ]+\\?*\""),
                override val nameRx: Regex = Regex("\\w+[ >]")) : IXmlParser

fun Sequence<MatchResult>.toAttributes(): Map<String, String> {
    val map = mutableMapOf<String, String>()
    forEach {
        val splitted = it.value.split("=")
        if (splitted.size != 2) throw IllegalArgumentException("Splitted contains more than 2 elements: [$splitted]")
        map[splitted[0]] = splitted[1].filter { it != '\"' }
    }
    return map
}

data class Tag(
        val name: String = "",
        val attributes: Map<String, String> = mutableMapOf(),
        val children: MutableList<Tag> = mutableListOf()
)

operator fun String.contains(regex: Regex) = this.matches(regex)