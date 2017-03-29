package com.example

import org.springframework.boot.SpringApplication
import kotlin.reflect.KClass

fun run(type: KClass<*>, vararg args: String) = SpringApplication.run(type.java, *args)