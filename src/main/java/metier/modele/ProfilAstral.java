/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Arthur
 */
@Entity
public class ProfilAstral implements Serializable{
    private String zodiacAstro;
    private String chineseAstro;
    private String color;
    private String animal;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public ProfilAstral(String zodiacAstro, String chineseAstro, String color, String animal) {
        this.zodiacAstro = zodiacAstro;
        this.chineseAstro = chineseAstro;
        this.color = color;
        this.animal = animal;
    }
    
    public ProfilAstral()
    {
        
    }

    public String getZodiacAstro() {
        return zodiacAstro;
    }

    public void setZodiacAstro(String zodiacAstro) {
        this.zodiacAstro = zodiacAstro;
    }

    public String getChineseAstro() {
        return chineseAstro;
    }

    public void setChineseAstro(String chineseAstro) {
        this.chineseAstro = chineseAstro;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    @Override
    public String toString() {
        return "ProfilAstral{" + "zodiacAstro=" + zodiacAstro + ", chineseAstro=" + chineseAstro + ", color=" + color + ", animal=" + animal + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
