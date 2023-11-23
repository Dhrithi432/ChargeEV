package com.evhub.model.domain.connectivity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "t_connectivity")
public class Connectivity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "connectivity_id")
    private Integer connectivityID;

    // TODO: 2023-11-15 Add other columns of this entity

    public Integer getConnectivityID() {
        return connectivityID;
    }

    public void setConnectivityID(Integer connectivityID) {
        this.connectivityID = connectivityID;
    }
}
