package com.solonezowaty.core.network

import arrow.core.Either
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class EitherCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) return null
        check(returnType is ParameterizedType) { "Return type must be a parameterized type." }

        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) != Either::class.java) return null
        check(responseType is ParameterizedType) { "Response type must be a parameterized type." }

        val leftType = getParameterUpperBound(0, responseType)
        if (getRawType(leftType) != Throwable::class.java) return null

        val rightType = getParameterUpperBound(1, responseType)
        return EitherCallAdapter<Any>(rightType)
    }
}

private class EitherCallAdapter<R>(
    private val successType: Type
) : CallAdapter<R, Call<Either<Throwable, R>>> {

    override fun adapt(call: Call<R>): Call<Either<Throwable, R>> = EitherCall(call, successType)

    override fun responseType(): Type = successType
}

class EitherCall<R>(
    private val delegate: Call<R>,
    private val successType: Type
) : Call<Either<Throwable, R>> {

    override fun enqueue(callback: Callback<Either<Throwable, R>>) = delegate.enqueue(
        object : Callback<R> {

            override fun onResponse(call: Call<R>, response: Response<R>) {
                callback.onResponse(this@EitherCall, Response.success(response.toEither()))
            }

            private fun Response<R>.toEither(): Either<Throwable, R> {
                if (!isSuccessful) {
                    val errorBody = errorBody()?.string() ?: ""
                    return Either.Left(Throwable(errorBody))
                }

                body()?.let { body -> return Either.Right(body) }

                return Either.Left(Throwable("Unknown error"))
            }

            override fun onFailure(call: Call<R>, throwable: Throwable) {
                callback.onResponse(this@EitherCall, Response.success(Either.Left(throwable)))
            }
        }
    )

    override fun clone(): Call<Either<Throwable, R>> = EitherCall(delegate.clone(), successType)

    override fun execute(): Response<Either<Throwable, R>> {
        throw UnsupportedOperationException()
    }

    override fun isExecuted(): Boolean = isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request =delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}