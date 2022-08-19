package com.cvargas.evernoteclone

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MediatorLiveData
import com.cvargas.evernoteclone.data.NoteRepository
import com.cvargas.evernoteclone.data.model.Note
import com.cvargas.evernoteclone.viewmodel.FormViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class FormViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: FormViewModel

    @Mock
    lateinit var repository: NoteRepository

    @Before
    fun setup(){
        viewModel = FormViewModel(repository)
    }

    @Test
    fun `test must not save note without title`(){
        viewModel.title.set("")
        viewModel.body.set("")

        viewModel.createNote()

        Assert.assertEquals(false, viewModel.saved.value?.first)
        Assert.assertEquals("O Titulo não pode ser em vazio!", viewModel.saved.value?.second)
    }

    @Test
    fun `test must save note`() {
        val title = "Nota_1000"
        val body = "Body_Body"
        viewModel.title.set(title)
        viewModel.body.set(body)

        viewModel.createNote()

        Assert.assertEquals(true, viewModel.saved.value?.first)
        Assert.assertEquals("", viewModel.saved.value?.second)

        verify(repository).createNote(Note(title=title, body = body))
    }

    @Test
    fun `test must get a note`() {
        val note = Note(id = 1, title = "Nota_1000", desc = "Body_Body")

        val liveData = MediatorLiveData<Note>()
        liveData.value = note

        doReturn(liveData).`when`(repository).getNote(noteId = 1)

        viewModel.getNote(noteId = 1)

        Assert.assertEquals(note, viewModel.getNote(noteId = 1).value)
    }
}