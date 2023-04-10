package com.svyd.bootcounter.data.repository.boot

import com.svyd.bootcounter.data.repository.boot.model.BootEvent

interface BootEventRepository {
    fun saveEvent(event: BootEvent)
    fun retrieveLastEvents() : Pair<BootEvent, BootEvent>
    fun retrieveAllEvents() : List<BootEvent>
}