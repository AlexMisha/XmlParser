package com.shepard

import com.shepard.parser.XmlParser
import org.junit.Test
import java.io.File

class HelloTest {

    @Test
    fun test() {
        val rx = Regex("<\\w+[\\w+=\"\\w+\" ]+>")
        val attrRx = Regex("\\w+=\"\\w+\"")
        val nameRx = Regex("\\w+ ")

        val text = File("src/main/resources/questions.xml").readText()
        val parsed = rx.findAll(text, 0)
        parsed.forEach { tag ->
            println(tag.value)
            val name = nameRx.find(tag.value, 0)?.value ?: throw NullPointerException()
            println(name)
            val parsedAttr = attrRx.findAll(tag.value, 0)
            parsedAttr.forEach {
                println(it.value)
                val sliced = it.value.split("=")
                println(sliced)
            }
        }
    }

    @Test
    fun test2() {
        val rx = Regex("<\\w+ [\\w+=\"\\w+\" ]+>")
        val line = File("src/main/resources/questions.xml").readLines()
        line.forEach {
            println(rx in it)
        }
    }

    @Test
    fun xml_parser_test() {
        val xmlParser = XmlParser()
        val tag = xmlParser.parseXml("src/main/resources/questions.xml")

        println(tag)
    }
}
