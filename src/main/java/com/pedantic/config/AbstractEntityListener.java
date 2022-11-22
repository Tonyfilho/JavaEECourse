package com.pedantic.config;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.pedantic.entities.AbstractEntity;

/**Crio-se classe para serem LISTENERS de Atributos de Entidades
 * Usando as @notações
 */
/**
 * São LIFECYCLEs com funcões de CALLBACKs
 * 
 * @PostPersist
 * @PreUpdate
 * @PostUpdate
 * @PostLoad
 *           Que diz para este campo ser persistido na DB
 *           no RunTime este Metodo ser Pre Percistido na DB
 */
public class AbstractEntityListener {

    @PrePersist
    public void setCreatedOn(AbstractEntity abstractEntity) {
        abstractEntity.setCreatedOn(LocalDateTime.now());
    }

    @PreUpdate
    public void  setUpdateOn(AbstractEntity abstractEntity) {
       abstractEntity.setUpdatedOn(LocalDateTime.now());
    }

}
