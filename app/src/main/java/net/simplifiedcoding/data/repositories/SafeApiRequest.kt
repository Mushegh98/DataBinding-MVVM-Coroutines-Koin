package net.simplifiedcoding.data.repositories

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

abstract class SafeApiRequest {

    suspend fun<T: Any> apiRequest(call: suspend () -> Response<T>) : T{
        val response = call.invoke()
        if(response.isSuccessful){
            return response.body()!!
        }else{
            //@todo handle api exception
            throw ApiException(response.code().toString())
        }
    }

}

class ApiException(message: String): IOException(message)


enum class Status{
    SUCCESS ,LOADING ,ERROR
}



data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }
        fun <T> error(msg: String?, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }
        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}



suspend fun <T> createSafeCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> Response<T>
): Resource<T> {
    return withContext(dispatcher) {
        try {
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                Resource.success(response.body())
            } else {
                Resource.error(
                    "Response is fail, code is ${response.code()}, error body is ${response.errorBody()}"
                    , null
                )
            }
        } catch (throwable: Throwable) {
            Resource.error(throwable.message, null)
        }
    }
}