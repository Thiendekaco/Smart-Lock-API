package com.labmanager.project.dto;

import java.util.List;

public class RoleMemberDTO {
    private String nameLab;

    private String nameSchool;

    private List<String> field;

    private int ranking;


    public String getNameLab() {
        return nameLab;
    }

    public void setNameLab(String nameLab) {
        this.nameLab = nameLab;
    }

    public String getNameSchool() {
        return nameSchool;
    }

    public void setNameSchool(String nameSchool) {
        this.nameSchool = nameSchool;
    }

    public List<String> getField() {
        return field;
    }

    public void setField(List<String> field) {
        this.field = field;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public RoleMemberDTO() {
    }

    public RoleMemberDTO(String nameLab, String nameSchool, List<String> field, int ranking) {
        this.nameLab = nameLab;
        this.nameSchool = nameSchool;
        this.field = field;
        this.ranking = ranking;
    }



}
