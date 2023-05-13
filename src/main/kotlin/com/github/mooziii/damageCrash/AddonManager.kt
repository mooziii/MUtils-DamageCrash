package com.github.mooziii.damageCrash

import de.miraculixx.challenge.api.MChallengeAPI
import de.miraculixx.challenge.api.settings.ChallengeData
import com.github.mooziii.damageCrash.utils.AddonMod
import com.github.mooziii.damageCrash.utils.cmp
import com.github.mooziii.damageCrash.utils.plus
import com.github.mooziii.damageCrash.utils.prefix
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.axay.kspigot.extensions.console
import net.kyori.adventure.text.format.NamedTextColor
import java.io.File

/**
 * The core of your addon to communicate with the MUtils API
 */
object AddonManager {
    /**
     * Loads all data from disk and tries to connect to MUtils API. On successfully connection, all mod data is added to MUtils
     *
     * Should only be called at the server start/addon start. User changes are directly applied to the given [ChallengeData]
     */
    fun loadMods() {
        // Try to connect to MUtils API
        val api = MChallengeAPI.instance
        if (api == null) {
            console.sendMessage(prefix + cmp("Failed to connect with MUtils-Challenge API!", NamedTextColor.RED))
            return
        }

        // Add all mods to MUtils
        AddonMod.values().forEach { mod ->
            val prodData = api.addChallenge(mod.uuid, mod.getModData())
            if (prodData == null) {
                console.sendMessage(prefix + cmp("Failed to inject ${mod.name} to MChallenge!"))
                return@forEach
            }
        }

        // Finished
        console.sendMessage(prefix + cmp("Successfully hooked in!"))
    }
}