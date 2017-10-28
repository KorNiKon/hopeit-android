package io.kornikon.hopeit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "age" ,"desc" ,"cashTarget" ,"cashNow" ,"category" ,"deadline" ,"lastChance"})
public class RestUsers {
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

}