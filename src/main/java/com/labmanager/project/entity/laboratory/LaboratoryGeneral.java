package com.labmanager.project.entity.laboratory;


import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@Table(name = "lab_general")
public class LaboratoryGeneral {

    @Id
    @Column(name = "id_lab_general")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name_lab")
    private String nameLab;

    @Column(name = "name_school")
    private String nameSchool;


    @Type(ListArrayType.class)
    @Column(name = "field", columnDefinition = "character varying[]")
    private List<String> field;

    @Column(name = "ranking")
    private int ranking;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_lab_details")
    private LaboratoryDetail laboratoryDetail;


    public LaboratoryDetail getLaboratoryDetail() {
        return laboratoryDetail;
    }

    public void setLaboratoryDetail(LaboratoryDetail laboratoryDetail) {
        this.laboratoryDetail = laboratoryDetail;
    }


    public LaboratoryGeneral(String nameLab, String nameSchool, List<String> field, int ranking) {
        this.nameLab = nameLab;
        this.nameSchool = nameSchool;
        this.field = field;
        this.ranking = ranking;
    }

    @Override
    public String toString() {
        return "LaboratoryGeneral{" +
                "id=" + id +
                ", nameLab='" + nameLab + '\'' +
                ", nameSchool='" + nameSchool + '\'' +
                ", field=" + field +
                ", ranking=" + ranking +
                ", laboratoryDetail="  +
                '}';
    }

    public LaboratoryGeneral() {
    }

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

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public LaboratoryGeneral setAllFeature ( LaboratoryGeneral laboratoryGeneral){
        this.field = laboratoryGeneral.getField();
        this.ranking = laboratoryGeneral.getRanking();
        this.nameSchool = laboratoryGeneral.getNameSchool();
        this.nameLab = laboratoryGeneral.getNameLab();
        if(laboratoryGeneral.getLaboratoryDetail() != null){
            this.laboratoryDetail.setLaboratory(laboratoryGeneral.getLaboratoryDetail());
        }
        return this;
    }



}
