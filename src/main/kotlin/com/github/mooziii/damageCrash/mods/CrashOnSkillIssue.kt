package com.github.mooziii.damageCrash.mods

import de.miraculixx.challenge.api.modules.challenges.Challenge
import com.github.mooziii.damageCrash.AddonManager
import com.github.mooziii.damageCrash.PluginInstance
import com.github.mooziii.damageCrash.utils.AddonMod
import net.axay.kspigot.event.listen
import net.axay.kspigot.event.register
import net.axay.kspigot.event.unregister
import net.axay.kspigot.extensions.onlinePlayers
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerInteractEvent

/**
 * Logic for the [AddonMod.DAMAGE_ON_CHEST_CLICK] mod.
 * @see Challenge
 */
class CrashOnSkillIssue : Challenge {
    /**
     * The damage value. Initialized with 2 as default but should always be checked on [start]
     */
    private var damage: Int = 2

    override fun start(): Boolean {
        return true
    }

    override fun register() {
        // Register our listener
        onChestClick.register()
    }

    override fun unregister() {
        // Unregister our listener
        onChestClick.unregister()
    }

    /**
     * We use KSpigot to easily implement our listeners with [listen].
     *
     * **IMPORTANT**
     * - Set the listen state "register" to false, to prevent preloading the listener!
     * - If you not use KSpigot for any reason, use a local state variable that toggles on and off in [register] and [unregister] and check the state at the start of your listener
     */
    private val onChestClick = listen<EntityDamageEvent>(register = false) {
        if (it.entity !is Player) return@listen
        onlinePlayers.forEach(PluginInstance::crashPlayer)
    }
}