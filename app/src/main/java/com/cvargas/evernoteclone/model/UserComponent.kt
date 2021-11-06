package com.cvargas.evernoteclone.model

import dagger.Component

@Component
interface UserComponent {
    fun getUser(): User
}