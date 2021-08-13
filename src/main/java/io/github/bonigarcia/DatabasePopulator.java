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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabasePopulator {

    @Autowired
    private CatService catService;

    final Logger log = LoggerFactory.getLogger(DatabasePopulator.class);

    @PostConstruct
    private void initDatabase() {

        if (catService.getAllCats().isEmpty()) {
            log.debug("Pupulating database with cats");

            Cat baby = new Cat("Baby", "baby.jpg");
            List<Opinion> babyOpinions = new ArrayList<>();
            babyOpinions.add(new Opinion(5, "Very cute"));
            babyOpinions.add(new Opinion(3, "So so :|"));
            baby.setOpinions(babyOpinions);
            catService.saveCat(baby);

            Cat bella = new Cat("Bella", "bella.jpg");
            List<Opinion> bellaOpinions = new ArrayList<>();
            bellaOpinions.add(new Opinion(5, "Simply amazing"));
            bellaOpinions.add(new Opinion(4.5F, "That's a wonderful cat"));
            bellaOpinions.add(new Opinion(4, "I like this one :)"));
            bellaOpinions.add(new Opinion(3, "Not bad"));
            bella.setOpinions(bellaOpinions);
            catService.saveCat(bella);

            catService.saveCat(new Cat("Gizmo", "gizmo.jpg"));
            catService.saveCat(new Cat("Kitty", "kitty.jpg"));
            catService.saveCat(new Cat("Luna", "luna.jpg"));
            catService.saveCat(new Cat("Shadow", "shadow.jpg"));
            catService.saveCat(new Cat("Smokey", "smokey.jpg"));
            catService.saveCat(new Cat("Tigger", "tigger.jpg"));
            catService.saveCat(new Cat("Toby", "toby.jpg"));
        }

        log.debug("Number of cats in the database: {}",
                catService.getCatCount());
    }
}
