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
package io.github.bonigarcia.test.unit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import io.github.bonigarcia.Cat;
import io.github.bonigarcia.CatRepository;
import io.github.bonigarcia.CatService;
import io.github.bonigarcia.mockito.MockitoExtension;

@Tag("unit")
@Tag("FR5")
@DisplayName("Adding comments to cats")
@ExtendWith(MockitoExtension.class)
class RateCatWithCommentsTest {

    @InjectMocks
    CatService catService;

    @Mock
    CatRepository catRepository;

    // Test data
    Cat dummy = new Cat("dummy", "dummy.png");
    int stars = 5;

    @DisplayName("Rating cats with comments")
    @ParameterizedTest(name = "Rating cat with comment: \"{0}\"")
    @ValueSource(strings = { "foo", "bar" })
    void testRatingWithComments(String comment) {
        when(catRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(dummy));
        Cat dummyCat = catService.rateCat(stars, comment, dummy);
        assertThat(
                catService.getOpinions(dummyCat).iterator().next().getComment(),
                equalTo(comment));
    }

    @DisplayName("Rating cats with empty comments")
    @Test
    void testRatingWithEmptyComments() {
        when(catRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(dummy));
        Cat dummyCat = catService.rateCat(stars, dummy);
        assertThat(
                catService.getOpinions(dummyCat).iterator().next().getComment(),
                isEmptyString());
    }

}