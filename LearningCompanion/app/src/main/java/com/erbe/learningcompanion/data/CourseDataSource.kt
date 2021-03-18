package com.erbe.learningcompanion.data

import com.erbe.learningcompanion.data.model.Course
import com.erbe.learningcompanion.data.model.CourseLevel
import kotlinx.coroutines.flow.flowOf

fun getCourseList() = flowOf(
    listOf(
        Course(
            name = "Kotlin Fundamentals",
            description = "Learn the fundamentals of the new language developed by JetBrains",
            level = CourseLevel.BEGINNER,
            completed = true
        ),
        Course(
            name = "Swift Fundamentals",
            description = "Learn the fundamentals of a modern language for iOS development",
            level = CourseLevel.BEGINNER,
            completed = false
        ),
        Course(
            name = "Advanced Android",
            description = "Learn about some of the more advanced topics like dependency injection and app architecture",
            level = CourseLevel.ADVANCED,
            completed = false
        ),
        Course(
            name = "Jetpack Compose",
            description = "Learn how to build beautiful UIs with the new and modern declarative toolkit.",
            level = CourseLevel.BEGINNER,
            completed = false
        ),
        Course(
            name = "Hilt Dependency Injection",
            description = "Get to know the newest library for managing dependencies",
            level = CourseLevel.ADVANCED,
            completed = true
        ),
        Course(
            name = "Android Architecture Components",
            description = "Build your apps easier and faster with these new tools provided by the Google team",
            level = CourseLevel.BEGINNER,
            completed = false
        )
    )
)