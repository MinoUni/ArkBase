package com.arkbase.operator;

import com.arkbase.converter.ArchetypeConverter;
import com.arkbase.converter.RarityConverter;
import com.arkbase.converter.SubclassConverter;
import com.arkbase.converter.TraitConverter;
import com.arkbase.enums.Rarity;
import com.arkbase.material.Material;
import com.arkbase.skill.Skill;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "operators")
public class Operator {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "code_name", nullable = false, unique = true, length = 50)
  private String codeName;

  @Column(nullable = false, length = 10)
  @Convert(converter = ArchetypeConverter.class)
  private Archetype archetype;

  @Column(nullable = false, length = 25)
  @Convert(converter = SubclassConverter.class)
  private Subclass subclass;

  @Column(nullable = false, length = 2)
  @Convert(converter = RarityConverter.class)
  private Rarity rarity;

  @Column(nullable = false)
  @Convert(converter = TraitConverter.class)
  private Trait trait;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 6)
  private Position position;

  @Enumerated(EnumType.STRING)
  @Column(name = "attack_type", nullable = false, length = 8)
  private AttackType attackType;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "operators_materials",
      joinColumns = @JoinColumn(name = "operator_id"),
      inverseJoinColumns = @JoinColumn(name = "material_id"))
  private Set<Material> materials = new HashSet<>();

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "operators_skills",
      joinColumns = @JoinColumn(name = "operator_id"),
      inverseJoinColumns = @JoinColumn(name = "skill_id"))
  private Set<Skill> skills = new HashSet<>();

  public Operator(
      String codeName,
      String archetype,
      String subclass,
      String rarity,
      String trait,
      String position,
      String attackType) {
    this.codeName = codeName;
    this.archetype = Archetype.valueOf(archetype.toUpperCase());
    this.subclass = Subclass.valueOf(subclass.toUpperCase());
    this.rarity = Rarity.valueOf(rarity.toUpperCase());
    this.trait = Trait.valueOf(trait.toUpperCase());
    this.position = Position.valueOf(position.toUpperCase());
    this.attackType = AttackType.valueOf(attackType.toUpperCase());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Operator other)) {
      return false;
    }
    return Objects.equals(id, other.id) && Objects.equals(codeName, other.codeName);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  @Getter
  @RequiredArgsConstructor
  public enum Archetype {
    VANGUARD("Vanguard"),
    GUARD("Guard"),
    DEFENDER("Defender"),
    SNIPER("Sniper"),
    CASTER("Caster"),
    MEDIC("Medic"),
    SUPPORTER("Supporter"),
    SPECIALIST("Specialist");

    private final String archetype;
  }

  public enum AttackType {
    PHYSICAL_DAMAGE,
    ARTS_DAMAGE
  }

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
    HUNTER("Hunter"),
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
    SPREADSHOOTER(
        "Attack all enemies within range, attack increased to 150% to enemies a row ahead"),
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

  public enum Position {
    MELEE,
    RANGED
  }
}
