/*
 * (C) Copyright 2017 Boni Garcia (http://bonigarcia.github.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.github.bonigarcia;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String pictureFileName;

    @ElementCollection(fetch = FetchType.EAGER)
    @OneToMany(cascade = CascadeType.ALL)
    private List<Opinion> opinions = new ArrayList<>();

    protected Cat() {
    }

    public Cat(String name, String pictureFileName) {
        this.name = name;
        this.pictureFileName = pictureFileName;
    }

    public void rate(int stars, String comment) {
        opinions.add(new Opinion(stars, comment));
    }

    public double getAverageRate() {
        double totalStars = 0;
        double average = 0;
        for (Opinion opinion : opinions) {
            totalStars += opinion.getStars();
        }
        if (opinions.size() > 0) {
            average = totalStars / opinions.size();
        }
        return average;
    }

    public double getHalfRoundedAverageRate() {
        return Math.round(getAverageRate() / 0.5) * 0.5;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureFileName() {
        return pictureFileName;
    }

    public void setPictureFileName(String pictureFileName) {
        this.pictureFileName = pictureFileName;
    }

    public long getId() {
        return id;
    }

    public List<Opinion> getOpinions() {
        return opinions;
    }

    public void setOpinions(List<Opinion> opinions) {
        this.opinions = opinions;
    }

    @Override
    public String toString() {
        return "Cat [id=" + id + ", name=" + name + ", pictureFileName="
                + pictureFileName + ", comments=" + opinions + "]";
    }

}
