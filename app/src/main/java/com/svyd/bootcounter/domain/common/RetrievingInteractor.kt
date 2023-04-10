package com.svyd.bootcounter.domain.common

import com.svyd.bootcounter.common.exception.Failure
import com.svyd.bootcounter.common.functional.Either
import com.svyd.bootcounter.common.functional.Either.Left
import com.svyd.bootcounter.common.functional.Either.Right
import com.svyd.bootcounter.common.mapper.TypeMapper

abstract class RetrievingInteractor<DomainType>(private val errorMapper: TypeMapper<Throwable, Failure>) {

    abstract suspend fun run(): DomainType

    suspend operator fun invoke() : Either<Failure, DomainType> {
        return try {
            Right(run())
        }
        catch (throwable : Throwable) {
            Left(errorMapper.map(throwable))
        }
    }
}
