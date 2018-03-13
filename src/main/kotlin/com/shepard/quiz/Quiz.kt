package com.shepard.quiz

import com.shepard.parser.Tag
import com.shepard.parser.getValue


class Quiz(val root: Tag) {
    val questions: List<Question> by root
}

class Question(val tag: Tag) {
    val text: String by tag
    val answers: List<Answer> by tag

    override fun toString() = "$text \n" + answers.joinToString(separator = "\n") { "${answers.indexOf(it) + 1}) $it" }
}


class Answer(map: Map<String, String>) {
    val text: String by map
    val correct: Boolean by map

    override fun toString() = text

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other is Answer && text == other.text && correct == other.correct) return true
        return false
    }

    override fun hashCode() = 37 * 14 + text.hashCode() + if (correct) 1 else 0
}
