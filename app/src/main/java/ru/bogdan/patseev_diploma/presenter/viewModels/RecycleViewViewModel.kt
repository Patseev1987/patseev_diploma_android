package ru.bogdan.patseev_diploma.presenter.viewModels

import androidx.lifecycle.ViewModel
import ru.bogdan.patseev_diploma.domain.models.StorageRecord
import ru.bogdan.patseev_diploma.domain.models.Worker
import ru.bogdan.patseev_diploma.domain.models.enums.Department
import ru.bogdan.patseev_diploma.domain.models.enums.ToolType
import ru.bogdan.patseev_diploma.domain.models.enums.WorkerType
import ru.bogdan.patseev_diploma.domain.models.tools.CuttingTool
import ru.bogdan.patseev_diploma.domain.models.tools.MeasureTool
import ru.bogdan.patseev_diploma.domain.models.tools.Place
import ru.bogdan.patseev_diploma.domain.models.tools.Tool
import java.lang.RuntimeException
import java.time.LocalDate

class RecycleViewViewModel : ViewModel() {

    private val records: List<StorageRecord>

    init {
        val tools = mutableListOf<Tool>()
        val cutter = CuttingTool(
            "6161-1212",
            "Cutter",
            "This Cutter for turing equipment",
            null,
            ToolType.CUTTING,
            "https://iconduck.com/icons/21805/cog",
            Place("1", "2", "3")
        )
        val cutter2 = CuttingTool(
            "6161-4040",
            "Cutter Spec",
            "This Cutter for turing equipment",
            null,
            ToolType.CUTTING,
            "https://iconduck.com/icons/21805/cog",
            Place("1", "2", "3")
        )
        val cutter3 = CuttingTool(
            "2020-1212",
            "Cutter Des",
            "This Cutter for turing equipment",
            null,
            ToolType.CUTTING,
            "https://static-00.iconduck.com/assets.00/settings-icon-982x1024-ly8qbieh.png",
            Place("1", "2", "3")
        )

        val measure = MeasureTool(
            "8700-0001",
            "measure tool",
            "This measure",
            "range 0-25",
            ToolType.MEASURE,
            LocalDate.now(),
            "https://uxwing.com/wp-content/themes/uxwing/download/buildings-architecture-real-estate/home-color-icon.png",
            Place("1", "2", "3")
        )

        tools.add(measure)
        tools.add(cutter)
        tools.add(cutter2)
        tools.add(cutter3)

        val worker1 = Worker(
            1,
            "Ivan",
            "Ivanov",
            "Ivanich",
            LocalDate.now(),
            Department.DEPARTMENT_17,
            WorkerType.WORKER
        )
        val worker2 = Worker(
            1,
            "Serge",
            "Sergeev",
            "Ivanich",
            LocalDate.now(),
            Department.DEPARTMENT_19,
            WorkerType.STORAGE_WORKER
        )
        val worker3 = Worker(
            1,
            "Andrei",
            "Andreev",
            "Ivanich",
            LocalDate.now(),
            Department.DEPARTMENT_19,
            WorkerType.STORAGE_WORKER
        )

        val workers = listOf(worker1, worker2, worker3)

        val storageRecord1 = StorageRecord(
            worker1, cutter2, 100
        )
        val storageRecord2 = StorageRecord(
            worker1, cutter, 10
        )
        val storageRecord3 = StorageRecord(
            worker1, cutter3, 1000
        )
        val storageRecord4 = StorageRecord(
            worker1, measure, 2
        )
        val storageRecord5 = StorageRecord(
            worker2, cutter2, 40
        )
        val storageRecord6 = StorageRecord(
            worker2, cutter, 100
        )
        val storageRecord7 = StorageRecord(
            worker2, cutter3, 400
        )
        val storageRecord8 = StorageRecord(
            worker2, measure, 7
        )
        val storageRecord9 = StorageRecord(
            worker3, cutter2, 8
        )
        val storageRecord10 = StorageRecord(
            worker3, measure, 1
        )
        val storageRecord11 = StorageRecord(
            worker3, cutter, 1000
        )

        records = listOf(
            storageRecord1,
            storageRecord2,
            storageRecord3,
            storageRecord4,
            storageRecord5,
            storageRecord6
        )

    }


    fun getList(position: Int): List<StorageRecord> {
        when (position) {
            0 -> {
                return records.filter { it.tool.type == ToolType.CUTTING }
            }

            1 -> {
                return records.filter { it.tool.type == ToolType.MEASURE}
            }

            2 -> {
                return records.filter { it.tool.type == ToolType.HELPERS }
            }

            else -> {
                throw RuntimeException("Unknown position in ViewPager")
            }
        }
    }
}

