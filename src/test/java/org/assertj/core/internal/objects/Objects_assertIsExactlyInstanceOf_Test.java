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
package org.assertj.core.internal.objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.error.ShouldBeExactlyInstanceOf.shouldBeExactlyInstance;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.Objects;
import org.assertj.core.internal.ObjectsBaseTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for <code>{@link Objects#assertIsExactlyInstanceOf(AssertionInfo, Object, Class)}</code>.
 * 
 * @author Joel Costigliola
 * @author Nicolas François
 */
public class Objects_assertIsExactlyInstanceOf_Test extends ObjectsBaseTest {

  @Test
  public void should_pass_if_actual_is_exactly_instance_of_type() {
    objects.assertIsExactlyInstanceOf(someInfo(), "Yoda", String.class);
  }

  @Test
  public void should_throw_error_if_type_is_null() {
    assertThatNullPointerException().isThrownBy(() -> objects.assertIsExactlyInstanceOf(someInfo(), "Yoda", null))
                                    .withMessage("The given type should not be null");
  }

  @Test
  public void should_fail_if_actual_is_null() {
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> objects.assertIsExactlyInstanceOf(someInfo(), null, String.class))
                                                   .withMessage(actualIsNull());
  }

  @Test
  public void should_fail_if_actual_is_not_exactly_instance_of_type() {
    AssertionInfo info = someInfo();

    Throwable error = catchThrowable(() -> objects.assertIsExactlyInstanceOf(info, "Yoda", Object.class));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldBeExactlyInstance("Yoda", Object.class));
  }
}
