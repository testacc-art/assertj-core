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
 * Copyright 2012-2022 the original author or authors.
 */
package org.assertj.core.internal.objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.error.ShouldHaveNoNullFields.shouldHaveNoNullFieldsExcept;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.util.AssertionsUtil.expectAssertionError;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.core.util.Lists.newArrayList;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.Objects;
import org.assertj.core.internal.ObjectsBaseTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for <code>{@link Objects#assertHasNoNullFieldsOrPropertiesExcept(AssertionInfo, Object, String...)}</code>.
 *
 * @author Natália Struharová
 */
public class Objects_assertHasNoNullFieldsOrPropertiesExcept_Test extends ObjectsBaseTest {
  protected static final AssertionInfo INFO = someInfo();

  @Test
  void should_pass_if_actual_has_no_null_fields_except_given() {
    // GIVEN
    Object actual = new Objects_assertHasNoNullFieldsOrPropertiesExcept_Test.Data();
    // WHEN/THEN
    objects.assertHasNoNullFieldsOrPropertiesExcept(INFO, actual, "field2", "field3");
  }

  @Test
  void should_fail_if_actual_is_null() {
    // GIVEN
    Object actual = null;
    String fieldName = "field1";
    // WHEN
    AssertionError error = expectAssertionError(() -> objects.assertHasNoNullFieldsOrPropertiesExcept(INFO, actual, fieldName));
    // THEN
    assertThat(error).hasMessage(actualIsNull());
  }

  @Test
  void should_fail_if_ignored_fields_are_null() {
    // GIVEN
    Object actual = new Objects_assertHasNoNullFieldsOrPropertiesExcept_Test.Data();
    String fieldName = "field3";
    String illegalNullFieldName = "field2";

    // WHEN
    AssertionError error = expectAssertionError(() -> objects.assertHasNoNullFieldsOrPropertiesExcept(INFO, actual, fieldName));
    // THEN
    assertThat(error).hasMessage(shouldHaveNoNullFieldsExcept(actual, newArrayList(illegalNullFieldName),
                                                              newArrayList(fieldName)).create());
  }

  @SuppressWarnings("unused")
  private static class Data {

    private Object field1 = "foo";
    private Object field2 = null;
    private Object field3 = null;
    private static Object staticField;

    @Override
    public String toString() {
      return "data";
    }

    public Object getField3() {
      return "bar";
    }

  }
}
