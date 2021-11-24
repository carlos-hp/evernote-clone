package com.cvargas.evernoteclone.base

import org.mockito.ArgumentCaptor

open class BaseTest {
    open fun <T> captureArg(argument: ArgumentCaptor<T>): T = argument.capture()
}