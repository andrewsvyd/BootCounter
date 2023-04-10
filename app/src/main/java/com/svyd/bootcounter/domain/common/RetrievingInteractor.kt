package com.svyd.bootcounter.domain.common

import com.svyd.bootcounter.common.exception.Failure
import com.svyd.bootcounter.common.functional.Either
import com.svyd.bootcounter.common.functional.Either.Left
import com.svyd.bootcounter.common.functional.Either.Right
import com.svyd.bootcounter.common.mapper.TypeMapper

abstract class RetrievingInteractor<DomainType>(private val errorMapper: TypeMapper<Throwable, Failure>) {

    abstract suspend fun run(): DomainType

    /**
     * @param mapper   to map data model from domain to presentation layer type
     *                 it is important to move all data transformation to the background
     *                 execution without exposing data types from other modules
     */

    suspend operator fun <PresentationType> invoke(
        mapper: TypeMapper<DomainType, PresentationType>
    ): Either<Failure, PresentationType> {
        return try {
            Right(mapper.map(run()))
        } catch (throwable: Throwable) {
            Left(errorMapper.map(throwable))
        }
    }
}
