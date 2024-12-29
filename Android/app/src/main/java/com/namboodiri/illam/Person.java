package com.namboodiri.illam;

import java.util.ArrayList;

/**
 * Represents a person in a family tree with their relationships and personal details.
 * Used to track family connections and genealogical information.
 */
public class Person {
    // Basic personal information
    String name;
    String gender;
    String year;
    int score;      // Scoring metric for the person (purpose determined by application)
    String imageUrl;

    // Family relationships
    String father;  // Father's name
    String mother;  // Mother's name
    ArrayList<String> spouses = new ArrayList<>();   // List of spouse names
    ArrayList<String> children = new ArrayList<>();  // List of children's names
    ArrayList<String> siblings = new ArrayList<>();  // List of sibling names

    public String getImageUrl() {
        return imageUrl;
    }
}
