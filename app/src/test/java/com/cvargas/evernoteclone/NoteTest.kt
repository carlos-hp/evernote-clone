package com.cvargas.evernoteclone

import com.cvargas.evernoteclone.data.model.Note
import org.junit.Assert
import org.junit.Test

class NoteTest {

    @Test
    fun `test should format date pattern to month and year`() {
        val note = Note(title = "Nota A", body = "Nota A Conteudo", date = "20/02/2019")

        Assert.assertEquals("Fev 2019", note.createdDate)
    }

    @Test
    fun `test should format date cases empty`() {
        val note = Note(title = "Nota A", body = "Nota A Conteudo", date = "")

        Assert.assertEquals("", note.createdDate)
    }

    @Test
    fun `test should format date case null`() {
        val note = Note(title = "Nota A", body = "Nota A Conteudo")

        Assert.assertEquals("", note.createdDate)
    }
}