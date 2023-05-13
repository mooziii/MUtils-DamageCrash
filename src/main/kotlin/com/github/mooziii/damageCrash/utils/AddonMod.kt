package com.github.mooziii.damageCrash.utils

import com.github.mooziii.damageCrash.AddonManager
import com.github.mooziii.damageCrash.mods.CrashOnSkillIssue
import de.miraculixx.challenge.api.modules.challenges.ChallengeTags
import de.miraculixx.challenge.api.modules.challenges.CustomChallengeData
import de.miraculixx.challenge.api.settings.ChallengeData
import de.miraculixx.challenge.api.utils.Icon
import de.miraculixx.challenge.api.utils.IconNaming
import com.github.mooziii.damageCrash.CrashAddon
import java.util.*

/**
 * All of our addon mods. Each mod is unique by his [uuid] but not bound to it on each startup. It is only important that the [uuid] is the same at all time of one session.
 * @param uuid The unique ID. Don't choose a simple hard coded [uuid], it could conflict with other addons
 */
enum class AddonMod(val uuid: UUID) {
    DAMAGE_CRASH(UUID.randomUUID());

    /**
     * Holds all mod data. Should only be called once at startup to ship all data to the MUtils API
     * @see AddonManager.loadMods
     */
    fun getModData(): CustomChallengeData {
        return when (this) {
            DAMAGE_CRASH -> CustomChallengeData(
                uuid,
                CrashOnSkillIssue(),
                ChallengeData(),
                Icon("TNT", naming = IconNaming(cmp("Damage Crash"), listOf(cmp("You client crashes if you take damage"), cmp("damage")))),
                setOf(ChallengeTags.FUN, ChallengeTags.FREE, ChallengeTags.HARD, ChallengeTags.ADDON),
                CrashAddon.addonName
            )
        }
    }
}