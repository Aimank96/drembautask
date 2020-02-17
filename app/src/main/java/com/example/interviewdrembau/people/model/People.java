package com.example.interviewdrembau.people.model;

public class People {

//    we use time in milis for the sort in  firestore
    private String id = String.valueOf(System.currentTimeMillis());
    private String name;
    private int age;

    public People(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }

        if (this.getId() != null) {
            return this.getId().equals(((People) obj).getId());
        }
        return super.equals(obj);
    }}