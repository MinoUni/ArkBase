package com.arkbase.operator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Subclass {
  // * Caster class
  CORE_CASTER("Core Caster"),
  SPLASH_CASTER("Splash Caster"),
  CHAIN_CASTER("Chain Caster"),
  PHALANX_CASTER("Phalanx Caster"),
  MYSTIC_CASTER("Mystic Caster"),
  BLAST_CASTER("Blast Caster"),
  DRONE_CASTER("Drone Caster"),
  // * Guard class
  SWORDMASTER("Swordmaster"),
  DREADNOUGHT("Dreadnought"),
  INSTRUCTOR("Instructor"),
  CENTURION("Centurion"),
  LORD("Lord"),
  ARTS_FIGHTER("Arts Fighter"),
  MUSHA("Musha"),
  REAPER("Reaper"),
  FIGHTER("Fighter"),
  LIBERATOR("Liberator"),
  CRUSHER("Crusher"),
  // * Medic
  ST_MEDIC("ST Medic"),
  MULTI_TARGET_MEDIC("Multi-Target Medic"),
  THERAPIST("Therapist"),
  WANDERING_MEDIC("Wandering Medic"),
  INCARNATION_MEDIC("Incarnation Medic"),
  CHAIN_HEALER("Chain Healer"),
  // * Sniper
  MARKSMAN("Marksman"),
  ARTILLERYMAN("Artilleryman"),
  SPREADSHOOTER("Spreadshooter"),
  HEAVYSHOOTER("Heavyshooter"),
  FLINGER("Flinger"),
  DEADEYE("Deadeye"),
  BESIEGER("Besieger"),
  // * Specialist
  EXECUTOR("Executor"),
  Merchant("Merchant"),
  HOOKMASTER("Hookmaster"),
  AMBUSHER("Ambusher"),
  PUSH_STROKER("Push Stroker"),
  TRAPMASTER("Trapmaster"),
  DOLLKEEPER("Dollkeeper"),
  GEEK("Geek"),
  // * Supporter
  DECEL_BINDER("Decel Binder"),
  SUMMONER("Summoner"),
  HEXER("Hexer"),
  BARD("Bard"),
  RITUALIST("Ritualist"),
  ABJURER("Abjurer"),
  ARTIFICER("Artificer"),
  // * Defender
  PROTECTOR("Protector"),
  GUARDIAN("Guardian"),
  ARTS_PROTECTOR("Arts Protector"),
  JUGGERNAUT("Juggernaut"),
  DUELIST("Duelist"),
  FORTRESS("Fortress"),
  SENTINEL_GUARD("Sentinel Guard"),
  // * Vanguard
  AGENT("Agent"),
  PIONEER("Pioneer"),
  CHARGER("Charger"),
  TACTICIAN("Tactician"),
  STANDARD_BEARER("Standard Bearer");

  private final String subclass;
}
