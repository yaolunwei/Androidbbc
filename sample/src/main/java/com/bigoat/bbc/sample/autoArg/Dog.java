package com.bigoat.bbc.sample.autoArg;

public class Dog extends Animal{
    private String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "sex='" + sex + '\'' +
                '}';
    }
}
