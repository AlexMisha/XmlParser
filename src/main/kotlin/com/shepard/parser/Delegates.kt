package com.shepard.parser

import com.shepard.quiz.Answer
import com.shepard.quiz.Question
import com.shepard.quiz.Quiz
import kotlin.reflect.KProperty

operator fun Tag.getValue(quiz: Quiz, property: KProperty<*>) = quiz.root.children.map { Question(it) }

inline operator fun <reified T> Tag.getValue(question: Question, property: KProperty<*>): T {
    if (property.name == "text") return this.attributes[property.name] as T

    return question.tag.children
            .map { Answer(it.attributes) } as T
}

inline operator fun <reified T> Map<String, String>.getValue(answer: Answer, property: KProperty<*>): T {
    if (property.name == "correct") return this[property.name]?.toBoolean() as T
    return this[property.name] as T
}