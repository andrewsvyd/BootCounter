package com.svyd.bootcounter.domain.interactor

import com.svyd.bootcounter.common.exception.Failure
import com.svyd.bootcounter.common.functional.Either
import com.svyd.bootcounter.common.functional.Either.Left
import com.svyd.bootcounter.common.functional.Either.Right
import com.svyd.bootcounter.common.mapper.TypeMapper

abstract class ActionInteractor<Params>(private val errorMapper: TypeMapper<Throwable, Failure>) {

    abstract suspend fun run(params: Params)

    suspend operator fun invoke(params: Params) : Either<Failure, Unit> {
        return try {
            Right(run(params))
        }
        catch (throwable : Throwable) {
            Left(errorMapper.map(throwable))
        }
    }
}
