package com.shepard

import com.shepard.parser.XmlParser
import com.shepard.quiz.Quiz

fun main(args: Array<String>) {
    val root = XmlParser().parseXml("src/main/resources/questions.xml")
    val quiz = Quiz(root)

    quiz.questions.forEach {
        println(it)
        var answerPos = readLine()?.toInt() ?: throw IllegalStateException()

        if (it.answers.getOrNull(--answerPos)?.correct == true) println("You are right")
        else println("You are wrong")
        println()
    }
}