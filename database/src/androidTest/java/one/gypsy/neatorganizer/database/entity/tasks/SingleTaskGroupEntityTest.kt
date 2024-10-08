package one.gypsy.neatorganizer.database.entity.tasks

import one.gypsy.neatorganizer.database.DatabaseTest
import one.gypsy.neatorganizer.database.dao.tasks.SingleTaskGroupsDao
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

internal class SingleTaskGroupEntityTest : DatabaseTest() {

    private lateinit var taskGroupsDao: SingleTaskGroupsDao

    @Before
    override fun setup() {
        super.setup()
        taskGroupsDao = database.singleTaskGroupsDao()
    }

    @Test
    fun shouldInsertSingleTaskGroup() {
        // given
        val taskGroup = SingleTaskGroupEntity(
            name = "foobar",
            id = 1L,
            createdAt = 123124
        )

        // when
        taskGroupsDao.insert(taskGroup)
        val insertedGroup = taskGroupsDao.getSingleTaskGroupById(1L)

        // then
        assertThat(insertedGroup).isEqualToComparingFieldByField(taskGroup)
    }

    @Test
    fun shouldRemoveSingleTaskGroup() {
        // given
        val taskGroup = SingleTaskGroupEntity(
            name = "foobar",
            id = 1L,
            createdAt = 123124
        )

        // when
        taskGroupsDao.insert(taskGroup)
        taskGroupsDao.delete(taskGroup)
        val fetchedGroup = taskGroupsDao.getSingleTaskGroupById(1L)

        // then
        assertThat(fetchedGroup).isNull()
    }

    @Test
    fun shouldUpdateSingleTaskGroup() {
        // given
        val taskGroup = SingleTaskGroupEntity(
            name = "foobar",
            id = 1L,
            createdAt = 123124
        )

        // when
        taskGroupsDao.insert(taskGroup)
        val updatedTaskGroup = taskGroup.copy(name = "bar")
        taskGroupsDao.update(updatedTaskGroup)
        val fetchedUpdatedGroup = taskGroupsDao.getSingleTaskGroupById(1L)

        // then
        assertThat(fetchedUpdatedGroup).isEqualTo(updatedTaskGroup)
    }

    @Test
    fun shouldGetAllSingleTaskGroups() {
        // given
        val taskGroups = arrayOf(
            SingleTaskGroupEntity(
                id = 1L,
                name = "foobar",
                createdAt = 123124
            ),
            SingleTaskGroupEntity(
                id = 2L,
                name = "foobar2",
                createdAt = 123124
            ),
            SingleTaskGroupEntity(
                id = 3L,
                name = "foobar3",
                createdAt = 123124
            ),
        )

        // when
        taskGroupsDao.insert(*taskGroups)
        val fetchedTaskGroupsObservable = taskGroupsDao.getAllSingleTaskGroups()

        // then
        fetchedTaskGroupsObservable.observeForever {
            assertThat(it).containsExactlyInAnyOrderElementsOf(taskGroups.toList())
        }
    }

    @Test
    fun shouldGetSingleTaskGroupByIdObservable() {
        // given
        val taskGroup = SingleTaskGroupEntity(
            id = 1L,
            name = "foobar",
            createdAt = 123124
        )

        // when
        taskGroupsDao.insert(taskGroup)
        val taskGroupedObservable = taskGroupsDao.getSingleTaskGroupByIdObservable(1L)

        // then
        taskGroupedObservable.observeForever {
            assertThat(it).isEqualToComparingFieldByField(taskGroup)
        }
    }

    @Test
    fun shouldGetSingleTaskGroupById() {
        // given
        val taskGroup = SingleTaskGroupEntity(
            id = 1L,
            name = "foobar",
            createdAt = 123124
        )

        // when
        taskGroupsDao.insert(taskGroup)
        val fetchedTaskGroup = taskGroupsDao.getSingleTaskGroupById(1L)

        // then
        assertThat(fetchedTaskGroup).isEqualToComparingFieldByField(taskGroup)
    }

    @Test
    fun shouldDeleteTaskGroupById() {
        // given
        val taskGroup = SingleTaskGroupEntity(
            id = 1L,
            name = "foobar",
            createdAt = 123124
        )

        // when
        taskGroupsDao.insert(taskGroup)
        taskGroupsDao.deleteTaskGroupById(1L)
        val fetchedTaskGroup = taskGroupsDao.getSingleTaskGroupById(1L)

        // then
        assertThat(fetchedTaskGroup).isNull()
    }
}
