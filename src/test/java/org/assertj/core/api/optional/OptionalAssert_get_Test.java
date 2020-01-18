/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2020 the original author or authors.
 */
package org.assertj.core.api.optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.error.OptionalShouldBePresent.shouldBePresent;
import static org.assertj.core.presentation.UnicodeRepresentation.UNICODE_REPRESENTATION;
import static org.assertj.core.test.AlwaysEqualComparator.ALWAY_EQUALS;
import static org.assertj.core.util.AssertionsUtil.expectAssertionError;
import static org.assertj.core.util.FailureMessages.actualIsNull;

import java.util.Optional;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.OptionalAssert;
import org.assertj.core.util.introspection.PropertyOrFieldSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for <code>{@link OptionalAssert#get()}</code>.
 *
 * @author Filip Hrisafov
 */
@DisplayName("OptionalAssert get")
class OptionalAssert_get_Test {

  private final Optional<String> optional = Optional.of("Frodo");

  @Test
  void should_fail_if_optional_is_null() {
    // GIVEN
    Optional<String> optional = null;
    // WHEN
    AssertionError assertionError = expectAssertionError(() -> assertThat(optional).get());
    // THEN
    then(assertionError).hasMessage(actualIsNull());
  }

  @Test
  void should_fail_if_optional_is_empty() {
    // GIVEN
    Optional<String> optional = Optional.empty();
    // WHEN
    AssertionError assertionError = expectAssertionError(() -> assertThat(optional).get());
    // THEN
    then(assertionError).hasMessage(shouldBePresent(optional).create());
  }

  @Test
  void should_pass_if_optional_contains_a_value() {
    // WHEN
    AbstractObjectAssert<?, String> result = assertThat(optional).get();
    // THEN
    result.isEqualTo("Frodo");
  }

  @Test
  void should_keep_existing_assertion_state() {
    // GIVEN
    OptionalAssert<String> assertion = assertThat(optional).as("description")
                                                           .withFailMessage("error message")
                                                           .withRepresentation(UNICODE_REPRESENTATION)
                                                           .usingComparator(ALWAY_EQUALS);
    // WHEN
    AbstractObjectAssert<?, String> result = assertion.get();
    // THEN
    then(result).hasFieldOrPropertyWithValue("objects", extractObjectField(assertion))
                .extracting(AbstractAssert::getWritableAssertionInfo)
                .isEqualToComparingFieldByField(assertion.info);
  }

  private static Object extractObjectField(AbstractAssert<?, ?> assertion) {
    return PropertyOrFieldSupport.EXTRACTION.getValueOf("objects", assertion);
  }

}
