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

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;

/**
 * Immutable description of an active model as of the model profile it came from.
 *
 * <p>Instances are not updated. A new profile fires {@link
 * ac.shard.api.event.config.ConfigurationChangeEvent}, and {@link DetectionService} returns new
 * instances on every call, so compare models by {@link #id()}.
 *
 * @see DetectionService#models()
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
@ApiStatus.Experimental
public interface Model {
  /**
   * Returns the model id.
   *
   * @return the id, matching {@code [a-z0-9_]{1,64}}
   */
  String id();

  /**
   * Returns the full title of the model.
   *
   * @return the display name
   */
  String displayName();

  /**
   * Returns the short title of the model.
   *
   * @return the short name, or {@link #displayName()} if the profile defines none
   */
  String shortName();

  /**
   * Returns whether this is the primary model of the profile.
   *
   * @return {@code true} for the primary model
   */
  boolean isPrimary();

  /**
   * Returns the roles the model holds on this server.
   *
   * <p>Roles the server disables locally are excluded. A role may cover only some labels, see
   * {@link Label#roles()}.
   *
   * @return the enabled roles, possibly empty
   */
  @Unmodifiable
  Set<ModelRole> roles();

  /**
   * Returns the default flag threshold of the model's buffers.
   *
   * @return the default flag threshold. Labels may override it, see {@link Label#flagThreshold()}.
   */
  double flagThreshold();

  /**
   * Returns the public labels of the model, in profile order.
   *
   * @return the labels, legit ones included, empty for a single-head model. Reserved bookkeeping
   *     labels are never listed.
   */
  @Unmodifiable
  List<Label> labels();

  /**
   * Returns the public label with the given key.
   *
   * @param key the label key
   * @return the label, empty if the model has no public label with this key
   */
  Optional<Label> label(String key);
}
