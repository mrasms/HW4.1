package com.company;

import java.util.Random;

public class Main {

    public static int[] heroesHealth = {300, 280, 250};
    public static int[] heroesDamage = {20, 15, 25,};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic"};
    public static int bossHealth = 1000;
    public static int bossDamage = 50;
    public static String bossDefenceType = "";
    public static int healerHealth = 410;
    public static int roundNumber = 0;

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefenceType = heroesAttackType[randomIndex];
        System.out.println("Boss chose: " + bossDefenceType);
    }

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            round();
        }
    }

    public static void round() {
        roundNumber++;
        chooseBossDefence();
        bossHits();
        heroesHit();
        healerHits();
        printStatistics();
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] < bossDamage) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
        if (healerHealth > 0) {
            if (healerHealth <= bossDamage) {
                healerHealth = 0;
            } else {
                healerHealth = healerHealth - bossDamage;
            }
        }
    }

    public static boolean healerIsDead() {
        if (healerHealth <= 0) {
            return true;
        } else return false;

    }

    public static void healerHits() {
        int healerHit = (int) (Math.random() * (100));

        for (int i = 0; i < heroesHealth.length; i++) {
            if (healerIsDead() == true) {
                break;
            } else if (heroesHealth[i] == 0) {
                continue;
            } else if (heroesHealth[i] < 100) {
                heroesHealth[i] = heroesHealth[i] + healerHit;
                System.out.println("Medic will heal " + heroesAttackType[i] + " in the next round for " + healerHit + " health points");
            }
            break;
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (bossDefenceType == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(8) + 2; // 2,3,4,5,6,7,8,9
                    if (bossHealth < heroesDamage[i] * coeff) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    System.out.println("Critical damage: " + heroesDamage[i] * coeff);
                } else {
                    if (bossHealth < heroesDamage[i]) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }


    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 ) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead ) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println(roundNumber + " ROUND ----------------------");
        System.out.println("Boss health: " + bossHealth + " [" + bossDamage + "]");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " [" + heroesDamage[i] + "]");
        }
        if (!healerIsDead()) {
            System.out.println("Medic health: " + healerHealth);
        } else System.out.println("Medic health: " + healerHealth);
        System.out.println();
    }
}
