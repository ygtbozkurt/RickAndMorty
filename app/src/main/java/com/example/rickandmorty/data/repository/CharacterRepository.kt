package com.example.rickandmorty.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.rickandmorty.data.local.CharacterDao
import com.example.rickandmorty.data.remote.CharacterRemoteDataSource
import com.example.rickandmorty.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


class CharacterRepository @Inject constructor(
    private val remoteDataSource: CharacterRemoteDataSource,
    private val localDataSource: CharacterDao
) {

    fun getCharacter(id: Int) = performGetOperation(
        databaseQuery = { localDataSource.getCharacter(id) },
        networkCall = { remoteDataSource.getCharacter(id) },
        saveCallResult = { localDataSource.insert(it) }
    )

    fun getCharacters() = performGetOperation(
        databaseQuery = { localDataSource.getAllCharacters() },
        networkCall = { remoteDataSource.getCharacters() },
        saveCallResult = { localDataSource.insertAll(it.results) }
    )

    private fun <T, A> performGetOperation(
        databaseQuery: () -> LiveData<T>,
        networkCall: suspend () -> Resource<A>,
        saveCallResult: suspend (A) -> Unit
    ): LiveData<Resource<T>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val source = databaseQuery.invoke().map { Resource.success(it) }
            emitSource(source)

            val responseStatus = networkCall.invoke()
            if (responseStatus.status == Resource.Status.SUCCESS) {
                saveCallResult(responseStatus.data!!)

            } else if (responseStatus.status == Resource.Status.ERROR) {
                emit(Resource.error(responseStatus.message!!))
                emitSource(source)
            }
        }
}