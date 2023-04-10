package com.svyd.bootcounter.domain.interactor

import com.svyd.bootcounter.common.exception.Failure
import com.svyd.bootcounter.common.mapper.TypeMapper
import com.svyd.bootcounter.data.repository.boot.BootEventRepository
import com.svyd.bootcounter.data.repository.boot.model.BootEvent
import com.svyd.bootcounter.domain.common.RetrievingInteractor

class LastEventsInteractor(
    private val repository: BootEventRepository,
    errorMapper: TypeMapper<Throwable, Failure>
) : RetrievingInteractor<Pair<BootEvent, BootEvent>>(errorMapper) {

    override suspend fun run(): Pair<BootEvent, BootEvent> = repository.retrieveLastEvents()
}
