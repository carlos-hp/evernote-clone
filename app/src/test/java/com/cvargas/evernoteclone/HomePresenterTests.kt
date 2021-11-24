package com.cvargas.evernoteclone

import com.cvargas.evernoteclone.view.activities.home.Home
import com.cvargas.evernoteclone.view.activities.home.presentation.HomePresenter
import com.cvargas.evernoteclone.data.model.Note
import com.cvargas.evernoteclone.data.model.RemoteDataSource
import com.cvargas.evernoteclone.rules.RxSchedulerRule
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomePresenterTests {

    @Rule
    @JvmField
    // An instance of this class overrides the Schedulers used.
    // Its use was not necessary, because the Schedulers were requested via the interface.
    var testSchedulerRule = RxSchedulerRule()

    @Mock
    private lateinit var mockView: Home.View

    @Mock
    private lateinit var mockDataSource: RemoteDataSource

    private lateinit var homePresenter: HomePresenter

    private val fakeAllNotes: List<Note>
        get() = arrayListOf(
            Note(1, "Nota A", "Descricao Nota A", "01/10/2021", "Seja bem vindo Nota A"),
            Note(1, "Nota B", "Descricao Nota B", "01/11/2021", "Seja bem vindo Nota B"),
            Note(1, "Nota C", "Descricao Nota C", "01/12/2021", "Seja bem vindo Nota C"),
        )

    @Before
    fun setup() {
        homePresenter = HomePresenter(mockView, mockDataSource)
    }

    @Test
    fun `test must get all notes`() {
        //Give
        Mockito.doReturn(Schedulers.trampoline()).`when`(mockView).getBackgroundScheduler()
        Mockito.doReturn(Schedulers.trampoline()).`when`(mockView).getForegroundScheduler()
        Mockito.doReturn(Maybe.just(fakeAllNotes)).`when`(mockDataSource).listNotes()

        //When
        homePresenter.loadNotes()

        //Then
        Mockito.verify(mockDataSource).listNotes()
        Mockito.verify(mockView).displayNotes(fakeAllNotes)
    }

    @Test
    fun `test must show empty notes`() {
        val emptyArray = arrayListOf<Note>()
        //Give
        Mockito.doReturn(Schedulers.trampoline()).`when`(mockView).getBackgroundScheduler()
        Mockito.doReturn(Schedulers.trampoline()).`when`(mockView).getForegroundScheduler()
        Mockito.doReturn(Maybe.just(emptyArray)).`when`(mockDataSource).listNotes()

        //When
        homePresenter.loadNotes()

        //Then
        Mockito.verify(mockDataSource).listNotes()
        Mockito.verify(mockView).getBackgroundScheduler()
        Mockito.verify(mockView).getForegroundScheduler()
        Mockito.verify(mockView).displayNotes(emptyArray)
    }
}