package com.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Area")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Area")
    private Long Id_Area;

    @Column(name = "Type_of_area")
    private String Type_of_area; // R or C

    @Column(name = "Number_Of_Slot")
    private Integer Number_Of_Slot; // 20 slot for each area(customer, resident)

    @Column(name = "Area_Name")
    private String area_name; // name of area (resident area or customer area)

    @ManyToOne()
    @JoinColumn(name = "Id_Building")
    private Building building;

    public Area(String type_of_area, Integer number_Of_Slot, String area_name, Building building) {
        Type_of_area = type_of_area;
        Number_Of_Slot = number_Of_Slot;
        this.area_name = area_name;
        this.building = building;
    }

    @Override
    public String toString() {
        return "Area{" +
                "Id_Area=" + Id_Area +
                ", Type_of_area='" + Type_of_area + '\'' +
                ", Number_Of_Slot=" + Number_Of_Slot +
                ", area_name='" + area_name + '\'' +
                ", building=" + building +
                '}';
    }

    @OneToMany(mappedBy = "area")
    @JsonIgnore
    private List<Resident_Slot> List_resident_slot;

    @OneToMany(mappedBy = "area")
    @JsonIgnore
    private List<Customer_Slot> List_customer_slot;
}
