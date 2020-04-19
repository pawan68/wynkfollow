//package com.learnnbuild.wynkfollow.entities;
//
//import com.learnnbuild.wynkfollow.model.Sex;
//
//import javax.persistence.*;
//
//@Table(name = "person")
//@Entity
//public class Person {
//
//    @Id
//    @GeneratedValue
//    private String personId;
//    private String name;
//    private Integer age;
//    private String fatherName;
//    private String motherName;
//    private Sex sex;
//
//    @OneToOne
//    @JoinColumn(name = "address_id")
//    private Address address;
//    private String phoneNumber;
//    private String email;
//
//    public Person() {
//    }
//
//    public Person(String name, Integer age, Sex sex) {
//        this.name = name;
//        this.age = age;
//        this.sex = sex;
//    }
//
//    public String getPersonId() {
//        return personId;
//    }
//
//    public void setPersonId(String personId) {
//        this.personId = personId;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Integer getAge() {
//        return age;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }
//
//    public String getFatherName() {
//        return fatherName;
//    }
//
//    public void setFatherName(String fatherName) {
//        this.fatherName = fatherName;
//    }
//
//    public String getMotherName() {
//        return motherName;
//    }
//
//    public void setMotherName(String motherName) {
//        this.motherName = motherName;
//    }
//
//    public Sex getSex() {
//        return sex;
//    }
//
//    public void setSex(Sex sex) {
//        this.sex = sex;
//    }
//
//    public Address getAddress() {
//        return address;
//    }
//
//    public void setAddress(Address address) {
//        this.address = address;
//    }
//}
