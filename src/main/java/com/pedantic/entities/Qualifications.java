package com.pedantic.entities;

import java.time.LocalDate;

import javax.persistence.Embeddable;

@Embeddable
public class Qualifications {
    /**
     * 1 Employee tem um Map de qualificações usando a @notação 
    @ElementCollection, o Map é um array de Objetos, quando
     * Usamos a @notação Embeddable criamos uma Collection de Objetos
     */
    private String school;
    private LocalDate dataCompleted;
    private String qualificationAwarded;

    /****************** GETs and SETs************** */
    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public LocalDate getDataCompleted() {
        return dataCompleted;
    }

    public void setDataCompleted(LocalDate dataCompleted) {
        this.dataCompleted = dataCompleted;
    }

    public String getQualificationAwarded() {
        return qualificationAwarded;
    }

    public void setQualificationAwarded(String qualificationAwarded) {
        this.qualificationAwarded = qualificationAwarded;
    }

}
