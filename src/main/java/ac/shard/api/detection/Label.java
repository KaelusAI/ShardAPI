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

import java.util.Set;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;

/**
 * One public output class of a model.
 *
 * <p>Instances describe the model profile current when they were built. Compare labels by {@link
 * #id()}.
 *
 * @see Model#labels()
 * @see DetectionService#label(LabelId)
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
@ApiStatus.Experimental
public interface Label {
  /**
   * Returns the identity of the label.
   *
   * @return the label id
   */
  LabelId id();

  /**
   * Returns the name shown to staff.
   *
   * @return the display name, or the key if no name is configured. Names of labels of a non-primary
   *     model start with {@link Model#shortName()}.
   */
  String displayName();

  /**
   * Returns whether the label describes legitimate play rather than a cheat class.
   *
   * @return {@code true} for a legit label
   */
  boolean isLegit();

  /**
   * Returns the buffer value strictly above which the label flags.
   *
   * @return the label's own flag threshold if it has one, otherwise {@link Model#flagThreshold()}
   */
  double flagThreshold();

  /**
   * Returns the roles that cover the label.
   *
   * @return the enabled roles of the model whose label set includes this label, possibly empty
   */
  @Unmodifiable
  Set<ModelRole> roles();
}
