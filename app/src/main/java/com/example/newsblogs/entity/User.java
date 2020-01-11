package com.example.newsblogs.entity;

public class User {
    private String name;
    private String password;
    private String words;
    private int grade;

    public User(){

    }

    public User(String name, String password){
        this.name = name;
        this.password = password;
    }

    public User(String name, String password,String words){
        this.name = name;
        this.password = password;
        this.words = words;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
