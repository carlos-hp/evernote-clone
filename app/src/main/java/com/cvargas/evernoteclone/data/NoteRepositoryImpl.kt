package com.cvargas.evernoteclone.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cvargas.evernoteclone.data.model.Note
import com.cvargas.evernoteclone.data.source.RemoteDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NoteRepositoryImpl(
    private val remoteDataSource: RemoteDataSource = RemoteDataSource()
) : NoteRepository {

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

    override fun createNote(note: Note): LiveData<Note> {
        val data = MutableLiveData<Note>()
        val disposable = remoteDataSource.createNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                data.postValue(note)
            }, { throwable ->
                throwable.printStackTrace()
            })
        compositeDisposable.add(disposable)
        return data
    }
}