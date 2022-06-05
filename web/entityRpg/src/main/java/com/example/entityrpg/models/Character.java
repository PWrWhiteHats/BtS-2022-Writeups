package com.example.entityrpg.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.UUID;

@Entity(name="chars")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private UUID UUID;
    @NonNull
    private String name;
    @NonNull
    private Integer attack;
    @NonNull
    private int def;
    @NonNull
    private Integer health;
    @NonNull
    private Integer currHealth;
    @NonNull
    private boolean isInBattle;
    @NonNull
    private boolean isInRegen;

    private String info;

    private Timestamp timeStampToEndRegen;

    private Timestamp timestampToEndFight;

    private int addAtk;
    private int addHealth;
    private int removeHealth;
    private int regenHealth;

    private boolean bossKilled;

    public boolean isBossKilled() {
        return bossKilled;
    }

    public void setBossKilled(boolean bossKilled) {
        this.bossKilled = bossKilled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public java.util.UUID getUUID() {
        return UUID;
    }

    public void setUUID(java.util.UUID UUID) {
        this.UUID = UUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public Integer getCurrHealth() {
        return currHealth;
    }

    public void setCurrHealth(Integer currHealth) {
        this.currHealth = currHealth;
    }

    public boolean isInBattle() {
        return isInBattle;
    }

    public void setInBattle(boolean inBattle) {
        isInBattle = inBattle;
    }

    public boolean isInRegen() {
        return isInRegen;
    }

    public void setInRegen(boolean inRegen) {
        isInRegen = inRegen;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Timestamp getTimeStampToEndRegen() {
        return timeStampToEndRegen;
    }

    public void setTimeStampToEndRegen(Timestamp timeStampToEndRegen) {
        this.timeStampToEndRegen = timeStampToEndRegen;
    }

    public Timestamp getTimestampToEndFight() {
        return timestampToEndFight;
    }

    public void setTimestampToEndFight(Timestamp timestampToEndFight) {
        this.timestampToEndFight = timestampToEndFight;
    }

    public int getAddAtk() {
        return addAtk;
    }

    public void setAddAtk(int addAtk) {
        this.addAtk = addAtk;
    }

    public int getAddHealth() {
        return addHealth;
    }

    public void setAddHealth(int addHealth) {
        this.addHealth = addHealth;
    }

    public int getRemoveHealth() {
        return removeHealth;
    }

    public void setRemoveHealth(int removeHealth) {
        this.removeHealth = removeHealth;
    }

    public int getRegenHealth() {
        return regenHealth;
    }

    public void setRegenHealth(int regenHealth) {
        this.regenHealth = regenHealth;
    }
}
