package com.github.mooziii.damageCrash

import net.axay.kspigot.main.KSpigot
import net.minecraft.network.protocol.game.ClientboundExplodePacket
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.phys.Vec3
import org.bukkit.entity.Player

/**
 * Entry point for the MChallenge-Addon plugin. We use [KSpigot] to use all KSpigot utilities later
 */
class CrashAddon : KSpigot() {
    companion object {
        lateinit var INSTANCE: CrashAddon
        lateinit var addonName: String
    }

    override fun load() {
        INSTANCE = this
        // Don't change your addons name to something different from your plugin name!
        // Users should be able to easily know the source of a mod.
        @Suppress("DEPRECATION")
        addonName = description.name
    }

    override fun startup() {
        // Load all data on startup
        AddonManager.loadMods()
    }

    fun crashPlayer(player: Player) {
        val version =
            server.javaClass.getPackage().name.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()[3]
        try {
            val craftPlayerClass = Class.forName("org.bukkit.craftbukkit.$version.entity.CraftPlayer")
            val getHandle = craftPlayerClass.getMethod("getHandle")
            val serverPlayer = getHandle.invoke(player) as ServerPlayer
            serverPlayer.connection.send(
                ClientboundExplodePacket(
                    Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Float.MAX_VALUE, listOf(),
                    Vec3(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE)
                )
            )
        } catch (e: Exception) {
            logger.warning("Failed to find CraftPlayer class! Using package version $version")
        }
    }
}

val PluginInstance by lazy { CrashAddon.INSTANCE }