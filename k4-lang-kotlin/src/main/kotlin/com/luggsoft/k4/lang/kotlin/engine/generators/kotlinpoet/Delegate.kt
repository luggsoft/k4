package com.luggsoft.k4.lang.kotlin.engine.generators.kotlinpoet

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.PropertySpec.Builder

fun Builder.delegate(block: CodeBlock.Builder.() -> Unit): Builder
{
    return CodeBlock.builder().also(block).build().let(this::delegate)
}