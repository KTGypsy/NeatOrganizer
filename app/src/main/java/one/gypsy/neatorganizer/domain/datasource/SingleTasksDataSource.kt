package one.gypsy.neatorganizer.domain.datasource

import one.gypsy.neatorganizer.domain.dto.SingleTaskEntry

interface SingleTasksDataSource {
    suspend fun add(singleTaskEntry: SingleTaskEntry): Long
}