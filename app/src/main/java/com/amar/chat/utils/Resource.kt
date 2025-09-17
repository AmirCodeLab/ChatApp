package com.amar.chat.utils

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Idle<T> : Resource<T>()
    class Loading<T>(data: T? = null): Resource<T>(data)
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
}

sealed class Response<T> (val data: T? = null, val error: String?=null) {
    class Loading<T> : Response<T>()
    class Success<T>(data: T? = null) : Response<T>(data = data)
    class Error<T>(error: String? = null) :  Response<T>(error = error)
}