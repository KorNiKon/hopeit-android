package io.kornikon.hopeit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@JsonIgnoreProperties(value = { "age" ,"desc" ,"cashTarget" ,"cashNow" ,"category" ,"deadline" ,"lastChance"})
public class Kid {
    private String id;
    private String name;
    private Integer age;
    private String desc;
    private Double cashTarget;
    private Double cashNow;
    private String category;
    private String deadline;
    private Boolean lastChance;

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Integer getAge() {
        return age;
    }

    public String getDesc() {
        return desc;
    }

    public Double getCashTarget() {
        return cashTarget;
    }

    public Double getCashNow() {
        return cashNow;
    }

    public String getCategory() {
        return category;
    }

    public String getDeadline() {
        return deadline;
    }

    public Boolean getLastChance() {
        return lastChance;
    }

}