/*
 * (C) Copyright 2017 Boni Garcia (https://bonigarcia.github.io/)
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

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Opinion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double stars;
    private String comment;

    @Transient
    private boolean inCookies;

    protected Opinion() {
    }

    protected Opinion(double stars, String comment) {
        this.stars = stars;
        this.comment = comment;
    }

    public double getHalfRoundedStars() {
        return Math.round(stars / 0.5) * 0.5;
    }

    public double getStars() {
        return stars;
    }

    public String getComment() {
        return comment;
    }

    public boolean isInCookies() {
        return inCookies;
    }

    public void setInCookies(boolean inCookies) {
        this.inCookies = inCookies;
    }

    @Override
    public String toString() {
        return "Opinion [id=" + id + ", stars=" + stars + ", comment=" + comment
                + ", inCookies=" + inCookies + "]";
    }

}
