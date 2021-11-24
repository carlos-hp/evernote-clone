package com.cvargas.evernoteclone.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cvargas.evernoteclone.data.model.Note
import com.cvargas.evernoteclone.data.model.RemoteDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NoteRepositoryImpl : NoteRepository {

    private val remoteDataSource = RemoteDataSource()
    private val compositeDisposable = CompositeDisposable()

    override fun getAllNotes(): LiveData<List<Note>?> {
        val data = MutableLiveData<List<Note>?>()
        val disposable = remoteDataSource.listNotes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                data.postValue(list)
            }, { throwable ->
                throwable.printStackTrace()
                data.postValue(null)
            })
        compositeDisposable.add(disposable)
        return data
    }

    override fun getNote(noteId: Int): LiveData<Note?> {
        val data = MutableLiveData<Note?>()
        val disposable = remoteDataSource.getNote(noteId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                data.postValue(list)
            }, { throwable ->
                throwable.printStackTrace()
                data.postValue(null)
            })
        compositeDisposable.add(disposable)
        return data
    }

    override fun createNote(note: Note): Unit {
        val disposable = remoteDataSource.createNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        compositeDisposable.add(disposable)
    }
}