package com.shepard

import com.shepard.parser.XmlParser
import com.shepard.quiz.Answer
import com.shepard.quiz.Question
import com.shepard.quiz.Quiz
import org.junit.Test

class TestParse {

    @Test
    fun xml_parser_test() {
        val xmlParser = XmlParser()
        val tag = xmlParser.parseXml("src/main/resources/questions.xml")

        println(tag)
    }

    @Test
    fun test_answer() {
        val map = mapOf("text" to "correct", "correct" to "true")
        val answer = Answer(map)
        println(answer)
    }

    @Test
    fun test_question() {
        val xmlParser = XmlParser()
        val tag = xmlParser.parseXml("src/main/resources/questions.xml")

        val question = Question(tag.children[0])
        println(question)
    }
}
