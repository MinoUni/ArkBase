package com.arkbase.operator.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

//  ? Check if enum can be replaced by table or smth else
@Getter
@RequiredArgsConstructor
public enum Trait {
  // * Caster class subclasses
  CORE_CASTER("Attack deals [Arts damage]"),
  SPLASH_CASTER("Attack deals [Splash Arts Damage]"),
  CHAIN_CASTER(
      "Attacks deal [Arts damage] and jump between 3 enemies. "
          + "Each jump deals 15% less damage and inflicts a brief [Slow]"),
  PHALANX_CASTER(
      "Normally does not attack, but has greatly increased DEF and RES. "
          + "When skill is active, attacks deal [Splash Arts damage]"),
  MYSTIC_CASTER(
      "Attacks deal [Arts damage]. When unable to find a target, "
          + "attacks can be stored up and fired all at once (Up to 3 charges)"),
  BLAST_CASTER("Deals [Splash Arts damage] in a long line"),
  DRONE_CASTER(
      "Controls a drone to deal [Arts damage] to an enemy. "
          + "When the drone continuously attacks the same enemy, "
          + "its damage will increase (up to 110% of the operator's ATK)"),

  // * Guard class subclasses
  SWORDMASTER("Regular attacks deal damage twice"),
  DREADNOUGHT("Can block single enemy"),
  INSTRUCTOR(
      "Can attack distant enemies, attack increased to 120% "
          + "when attacking enemies not blocked by self"),
  CENTURION("Attack multiple enemies equal to block count"),
  LORD(
      "Can perform ranged attack(attack decreased to 80% "
          + "when attacking enemies with ranged attack)"),
  ARTS_FIGHTER("Attack deals [Arts damage]"),
  MUSHA("Can't be healed by other operators, heal 70 HP on every hit on enemies"),
  REAPER(
      "Can't be healed by allies, attack all enemies within range and "
          + "recovers 50 HP with every enemy hit (up to Block count"),
  FIGHTER("Can block single enemy"),
  LIBERATOR(
      "Doesn't attack and have 0 block count normally, gradually increase "
          + "attack power up to +200% after 40 seconds when skill is not active, "
          + "attack increase resets after skill ends"),
  CRUSHER("Attack multiple enemies equal to block count"),

  // * Medic class subclasses
  ST_MEDIC("Heal friendly unit"),
  MULTI_TARGET_MEDIC("Heal up to 3 friendly unit at once"),
  THERAPIST(
      "Larger healing range, however attack power will be "
          + "reduced to 80% when healing distant target"),
  WANDERING_MEDIC(
      "Heal friendly unit, recovers [Elemental damage] equal to 50% of Attack Power"
          + "(can heal the [Elemental damage] of unhurt units)"),
  INCARNATION_MEDIC(
      "Attacks deal [Arts Damage] and heal the HP of an ally within "
          + "attack range for 50% of the damage dealt"),
  CHAIN_HEALER(
      "Restores HP of allies, bouncing between 3 allies. Healing reduced by 25% per bounce."),

  // * Sniper class subclasses
  MARKSMAN("Prioritize attacking air units"),
  ARTILLERYMAN("Attack deals [Physical Splash Damage]"),
  SPREADSHOOTER("Attack all enemies within range, attack increased to 150% to enemies a row ahead"),
  HEAVYSHOOTER("High accuracy point-blank shot"),
  FLINGER(
      "Attacks deals physical splash damage twice to ground enemies "
          + "in a small area(2nd hit is an aftershock with halved damage)"),
  DEADEYE("Prioritize lowest defense enemies within attack range"),
  BESIEGER("Targets highest weight enemy"),
  HUNTER(
      "Attacks require ammo and increase ATK by 120%. "
          + "When not attacking, gradually reload ammo (up to 8)"),

  // * Specialist class subclasses
  EXECUTOR("Greatly reduced [Redeployment Time]"),
  Merchant(
      "Decreased [Redeployment Time], no cost refund on retreat, "
          + "uses 3 DP on every 3 second(automatically retreat when insufficient DP)"),
  HOOKMASTER("Skill can [Displace] enemy, can be placed on higher ground"),
  AMBUSHER(
      "Attack all enemies within range, 50% Physical & Arts evasion, "
          + "lower priority to be attacked by enemies"),
  PUSH_STROKER("Attack multiple enemies equal to block count, can be placed on higher ground"),
  TRAPMASTER("Can use traps in battle, can't deploy trap in tiles with enemies in it"),
  DOLLKEEPER(
      "Does not retreat when receiving lethal damage, instead swaps to a [Substitute] "
          + "to continue fighting ([Substitute] has 0 Block Count). "
          + "After a certain period, the [Substitute] will be replaced by the original"),
  GEEK("Self HP will decreases over time"),

  // * Supporter class subclasses
  DECEL_BINDER("Attack deals Arts damage and inflicts Slow"),
  SUMMONER("Attack deals Arts damage, can use summons in battle"),
  HEXER("Attack deals Arts damage"),
  BARD(
      "Does not attack, continuously heal all ally in range(10% of self ATK every second), "
          + "self unaffected by [Inspire] effects"),
  RITUALIST("Attacks deal [Arts damage], and can inflict [Elemental Damage]"),
  ABJURER(
      "Deals [Arts damage]; When skill is active, attacks instead restore"
          + " the HP of allies (heal amount is equal to 75% of ATK)"),
  ARTIFICER("Can block 2 enemies, can use [Support Devices] in battle"),

  // * Defender
  PROTECTOR("Can block 3 enemies"),
  GUARDIAN("Skills heal friendly unit"),
  ARTS_PROTECTOR("Attack cause Arts damage on skill"),
  JUGGERNAUT("Can't be healed by allies"),
  DUELIST("Can only gain SP when blocking"),
  FORTRESS("Prioritize long range splash attack when not blocking"),
  SENTINEL_GUARD("Blocks 3 enemies and attacks from long range."),

  // * Vanguard class subclasses
  AGENT("Has reduced [Redeployment Time], can use ranged attacks"),
  PIONEER("Can block 2 enemies"),
  CHARGER("Obtain 1 DP on every kill, full cost refund on retreat"),
  TACTICIAN(
      "Can summon [Tactical point] within attack range once, "
          + "damage increased to 150% when attacking enemies blocked by summon"),
  STANDARD_BEARER("Reduce block count to 0 when using skills");

  private final String trait;
}
