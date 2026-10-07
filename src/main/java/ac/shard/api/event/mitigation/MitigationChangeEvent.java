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
package ac.shard.api.event.mitigation;

import ac.shard.api.Initiator;
import ac.shard.api.event.SessionEvent;
import ac.shard.api.mitigation.MitigationTier;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

/**
 * Fired after the active mitigation rule of a player changed.
 *
 * <p>Sources:
 *
 * <ul>
 *   <li>the rule engine committed a change during its periodic evaluation
 *   <li>a plugin called {@link ac.shard.api.mitigation.MitigationService#release}
 *   <li>{@code /shard reload} removed the running rule or changed its tier
 * </ul>
 *
 * <p>Rules that end because Shard disables do not fire it.
 *
 * <p>Dispatched on the Shard event thread. Not cancellable.
 *
 * @see ac.shard.api.mitigation.MitigationService
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface MitigationChangeEvent extends SessionEvent {
  /**
   * Returns the tier before the change.
   *
   * @return the previous tier
   */
  MitigationTier previousTier();

  /**
   * Returns the tier after the change.
   *
   * @return the new tier, {@link MitigationTier#NONE} after a release
   */
  MitigationTier tier();

  /**
   * Returns the rule active before the change.
   *
   * @return the previous rule id, or {@code null} if none was active
   */
  @Nullable String previousRuleId();

  /**
   * Returns the rule active after the change.
   *
   * @return the new rule id, or {@code null} if none is active
   */
  @Nullable String ruleId();

  /**
   * Returns who caused the change.
   *
   * @return the releasing plugin, or {@code null} when the rule engine caused the change
   */
  @Nullable Initiator initiator();
}
