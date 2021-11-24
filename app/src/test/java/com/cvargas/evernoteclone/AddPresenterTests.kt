package com.cvargas.evernoteclone

import com.cvargas.evernoteclone.view.activities.add.Add
import com.cvargas.evernoteclone.view.activities.add.presentation.AddPresenter
import com.cvargas.evernoteclone.base.BaseTest
import com.cvargas.evernoteclone.data.model.Note
import com.cvargas.evernoteclone.data.model.RemoteDataSource
import com.cvargas.evernoteclone.rules.RxSchedulerRule
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.times

@RunWith(MockitoJUnitRunner::class)
class AddPresenterTests : BaseTest() {

    @Rule
    @JvmField
    // An instance of this class overrides the Schedulers used.
    // Its use was not necessary, because the Schedulers were requested via the interface.
    var testSchedulerRule = RxSchedulerRule()

    @Mock
    private lateinit var mockView: Add.View

    @Mock
    private lateinit var mockDataSource: RemoteDataSource

    @Captor
    private lateinit var noteArgCaptor: ArgumentCaptor<Note>

    private lateinit var addPresenter: AddPresenter

    private val fakeNote: Note
        get() = Note(title = "Nota A", body = "Seja bem vindo Nota A", date = "01/10/2021")

    @Before
    fun setup() {
        addPresenter = AddPresenter(1, mockView, mockDataSource)
    }

    @Test
    fun `test must display note`() {
        //Give
        Mockito.doReturn(Schedulers.trampoline()).`when`(mockView).getBackgroundScheduler()
        Mockito.doReturn(Schedulers.trampoline()).`when`(mockView).getForegroundScheduler()
        Mockito.doReturn(Single.just(fakeNote)).`when`(mockDataSource).getNote(1)

        //When
        addPresenter.getNoteDetail()

        //Then
        Mockito.verify(mockView, times(1)).getBackgroundScheduler()
        Mockito.verify(mockView, times(1)).getForegroundScheduler()
        Mockito.verify(mockView).displayNote(fakeNote.title!!, fakeNote.body!!)
    }

    @Test
    fun `test must not add note with empty body`() {
        val exception = Exception("O título deve ser informado!")
        //Give
        Mockito.doReturn(Schedulers.trampoline()).`when`(mockView).getBackgroundScheduler()
        Mockito.doReturn(Schedulers.trampoline()).`when`(mockView).getForegroundScheduler()

        //When
        addPresenter.createNote("", "")

        //Then
        Mockito.verify(mockView, times(1)).getBackgroundScheduler()
        Mockito.verify(mockView, times(1)).getForegroundScheduler()
        Mockito.verify(mockView).displayError(exception.message!!)
    }

    @Test
    fun `test must create a new note`() {
        //Give
        Mockito.doReturn(Schedulers.trampoline()).`when`(mockView).getBackgroundScheduler()
        Mockito.doReturn(Schedulers.trampoline()).`when`(mockView).getForegroundScheduler()
        Mockito.doReturn(Completable.complete()).`when`(mockDataSource).createNote(
            captureArg(
                noteArgCaptor
            )
        )

        //When
        addPresenter.createNote(fakeNote.title!!, fakeNote.body!!)

        //Then
        Mockito.verify(mockDataSource).createNote(captureArg(noteArgCaptor))
        //Additional
        MatcherAssert.assertThat(noteArgCaptor.value.title, CoreMatchers.equalTo(fakeNote.title))
        MatcherAssert.assertThat(noteArgCaptor.value.body, CoreMatchers.equalTo(fakeNote.body))

        Mockito.verify(mockView, times(1)).getBackgroundScheduler()
        Mockito.verify(mockView, times(1)).getForegroundScheduler()
        Mockito.verify(mockView).returnToHome()
    }

    @Test
    fun `test must show error messsage when creation failure`() {
        //Give
        Mockito.doReturn(Schedulers.trampoline()).`when`(mockView).getBackgroundScheduler()
        Mockito.doReturn(Schedulers.trampoline()).`when`(mockView).getForegroundScheduler()
        Mockito.doReturn(Completable.error(Exception("Erro ao criar a Nota!")))
            .`when`(mockDataSource)
            .createNote(anyOrNull())

        //When
        addPresenter.createNote(fakeNote.title!!, fakeNote.body!!)

        //Then
        Mockito.verify(mockDataSource).createNote(captureArg(noteArgCaptor))
        //Additional
        MatcherAssert.assertThat(noteArgCaptor.value.title, CoreMatchers.equalTo(fakeNote.title))
        MatcherAssert.assertThat(noteArgCaptor.value.body, CoreMatchers.equalTo(fakeNote.body))

        Mockito.verify(mockView, times(1)).getBackgroundScheduler()
        Mockito.verify(mockView, times(1)).getForegroundScheduler()
        Mockito.verify(mockView).displayError("Erro ao criar a Nota!")
    }
}