package io.sourcherry.kdtree

import java.util.*
import kotlin.Comparator
import kotlin.random.Random


object Main {

    @JvmStatic
    fun main(args: Array<String>) {

        val tree = KdTree(
            listOf(
                Comparators.byPrice,
                Comparators.byDate,
                Comparators.byDuration
            )
        )

        fillTree(tree)
    }

    private fun fillTree(tree: KdTree<Ticket>) {
        for (i in 0 until 100) {
            tree.add(genTicket())
        }
    }

    private fun genTicket(): Ticket {
        val date = Date(System.currentTimeMillis() + Random.nextLong(0, 1000000))
        val duration = Random.nextInt(0, 200000)

        return Ticket(
            date = date,
            agency = "OneTwoGo",
            duration = duration,
            tariff = genTariff()
        )
    }

    private fun genTariff(): Tariff {
        val type = Random.nextInt(0, 3).apply {

        Tariff.Type.ECONOM
        }
        val price = Random.nextInt(50, 5000)
        return Tariff(type, price)
    }

    data class Ticket(
        val date: Date,
        val agency: String,
        val duration: Int,
        val tariff: Tariff
    )

    data class Tariff(
        val type: Type,
        val price: Int
    ) {
        enum class Type {
            ECONOM, LUX, BUSNESS, FIRST_CLASS
        }
    }

    object Comparators {
        val byPrice = Comparator<Ticket> { ticket1, ticket2 ->
            compareValuesBy(ticket1.tariff.price, ticket2.tariff.price)
        }

        val byDate = Comparator<Ticket> { ticket1, ticket2 ->
            compareValuesBy(ticket1.date, ticket2.date)
        }

        val byDuration = Comparator<Ticket> { ticket1, ticket2 ->
            compareValuesBy(ticket1.duration, ticket2.duration)
        }
    }
}
