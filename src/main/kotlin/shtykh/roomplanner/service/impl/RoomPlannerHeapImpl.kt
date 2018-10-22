package shtykh.roomplanner.service.impl

import org.springframework.stereotype.Service
import shtykh.roomplanner.config.RoomConfig
import shtykh.roomplanner.model.RoomLevel
import shtykh.roomplanner.model.RoomPlan
import shtykh.roomplanner.model.RoomsUsage
import shtykh.roomplanner.service.RoomPlanner
import shtykh.roomplanner.service.RoomStateService

import java.util.*

import java.lang.Math.min
import java.util.Arrays.asList
import shtykh.roomplanner.model.RoomLevel.ECONOMY
import shtykh.roomplanner.model.RoomLevel.PREMIUM

@Service
class RoomPlannerHeapImpl(private val roomStateService: RoomStateService,
                          configurationProperties: RoomConfig) : RoomPlanner {

    private val minPremiumPayment: Int = configurationProperties.minPremiumPayment

    override fun plan(roomRequest: List<Int>): RoomPlan {
        if (roomRequest.isEmpty()) {
            return emptyPlan()
        }
        val availableRooms = roomStateService.availableRooms
        val premiumPayments = PriorityQueue(roomRequest.size, Comparator.reverseOrder<Int>())
        val economyPayments = PriorityQueue(roomRequest.size, Comparator.reverseOrder<Int>())
        initPaymentQueues(roomRequest, availableRooms, premiumPayments, economyPayments)
        val premium = fillPremium(premiumPayments, economyPayments, availableRooms)
        val economy = fillEconomy(economyPayments, availableRooms)
        return RoomPlan(asList(premium, economy))
    }

    override fun setAvailability(availabilities: Map<RoomLevel, Int>) {
        roomStateService.availableRooms = availabilities
    }

    private fun fillPremium(premiumPayments: Queue<Int>,
                            economyPayments: Queue<Int>,
                            availableRooms: Map<RoomLevel, Int>): RoomsUsage {
        val premium = RoomsUsage(PREMIUM, 0, 0)
        var premiumSlots = availableRooms.getOrDefault(PREMIUM, 0)
        // processing $premiumSlots top paying high payers
        while (premiumSlots > 0 && !premiumPayments.isEmpty()) {
            premium.add(premiumPayments.poll())
            premiumSlots--
        }
        // is any premium rooms left?
        if (premiumSlots > 0) {
            val economySlots = availableRooms.getOrDefault(ECONOMY, 0)
            var customersToUpgrade = min(economyPayments.size - economySlots, premiumSlots)
            while (customersToUpgrade > 0) {
                premium.add(economyPayments.poll())
                customersToUpgrade--
            }
        }
        return premium
    }

    private fun fillEconomy(queue: Queue<Int>,
                            availableRooms: Map<RoomLevel, Int>): RoomsUsage {
        val economy = RoomsUsage(ECONOMY, 0, 0)
        val economySlots = availableRooms.getOrDefault(ECONOMY, 0)
        var economyRoomsToFill = min(queue.size, economySlots)
        while (economyRoomsToFill > 0) {
            economy.add(queue.poll())
            economyRoomsToFill--
        }
        return economy
    }

    private fun initPaymentQueues(roomRequest: List<Int>,
                                  availableRooms: Map<RoomLevel, Int>,
                                  premiumPayments: Queue<Int>,
                                  economyPayments: Queue<Int>) {
        val premiumRooms = availableRooms.getOrDefault(PREMIUM, 0)
        val economyRooms = availableRooms.getOrDefault(ECONOMY, 0)
        if (premiumRooms != 0 || economyRooms != 0) {
            roomRequest.forEach { price ->
                if (price >= minPremiumPayment) {
                    if (premiumRooms > 0) {
                        premiumPayments.offer(price)
                    }
                } else {
                    economyPayments.offer(price)
                }
            }
        }
    }

    private fun emptyPlan(): RoomPlan {
        return RoomPlan(asList(RoomsUsage(PREMIUM, 0, 0),
            RoomsUsage(ECONOMY, 0, 0)))
    }
}
