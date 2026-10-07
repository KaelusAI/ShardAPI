/*
 * This file is part of ShardAPI, licensed under the Apache License 2.0.
 *
 *  Copyright (c) 2026 KaelusAI
 *  Copyright (c) contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package ac.shard.api.detection;

import java.util.regex.Pattern;

/**
 * Identity of a label, made of a model id and a label key.
 *
 * <p>A label id may name a label that no active model has, for example one read from history.
 * Equality compares both components.
 *
 * @param modelId the model id, matching {@code [a-z0-9_]{1,64}}
 * @param key the label key, matching {@code [a-z0-9_]{1,64}} without a leading {@code _}
 * @see Label#id()
 * @since 2.1.0
 */
public record LabelId(String modelId, String key) {
  private static final Pattern MODEL_ID = Pattern.compile("[a-z0-9_]{1,64}");
  private static final Pattern KEY = Pattern.compile("[a-z0-9][a-z0-9_]{0,63}");

  /**
   * Creates a label id.
   *
   * @param modelId the model id
   * @param key the label key
   * @throws IllegalArgumentException unless {@code modelId} matches {@code [a-z0-9_]{1,64}} and
   *     {@code key} matches {@code [a-z0-9_]{1,64}} without a leading {@code _}
   */
  public LabelId {
    if (!MODEL_ID.matcher(modelId).matches()) {
      throw new IllegalArgumentException("Invalid model id: " + modelId);
    }
    if (!KEY.matcher(key).matches()) {
      throw new IllegalArgumentException("Invalid label key: " + key);
    }
  }

  /**
   * Creates a label id.
   *
   * @param modelId the model id
   * @param key the label key
   * @return the label id
   * @throws IllegalArgumentException unless {@code modelId} matches {@code [a-z0-9_]{1,64}} and
   *     {@code key} matches {@code [a-z0-9_]{1,64}} without a leading {@code _}
   */
  public static LabelId of(String modelId, String key) {
    return new LabelId(modelId, key);
  }
}
