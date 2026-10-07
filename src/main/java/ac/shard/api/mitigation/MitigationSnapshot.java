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
package ac.shard.api.mitigation;

import java.util.Map;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.Nullable;

/**
 * Immutable mitigation state of one player, taken when {@link
 * ac.shard.api.player.ShardPlayer#mitigation()} is called.
 *
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface MitigationSnapshot {
  /**
   * Returns the tier of the applied rule.
   *
   * @return the tier, or {@link MitigationTier#NONE} if no rule is applied
   */
  MitigationTier tier();

  /**
   * Returns the ID of the applied rule.
   *
   * @return the rule ID, or {@code null} if no rule is applied
   */
  @Nullable String ruleId();

  /**
   * Returns the mitigation score.
   *
   * <p>The score is dimensionless, never negative and persisted across sessions. Higher is more
   * suspicious.
   *
   * @return the score
   */
  double score();

  /**
   * Returns the active effect per channel.
   *
   * <p>Channels the applied rule does not configure are absent, and the map is empty when no rule
   * is applied. Values by channel:
   *
   * <ul>
   *   <li>{@link MitigationChannel#MELEE}, {@link MitigationChannel#PROJECTILE}, {@link
   *       MitigationChannel#CRYSTAL} and {@link MitigationChannel#HEALING}: a multiplier in [0, 1]
   *   <li>{@link MitigationChannel#INCOMING}: a multiplier in [1, 4]
   *   <li>{@link MitigationChannel#CANCEL}: a chance in [0, 1]
   * </ul>
   *
   * <p>A multiplier of 1 or a chance of 0 has no effect.
   *
   * @return the effects keyed by channel
   */
  @Unmodifiable
  Map<MitigationChannel, Double> effects();

  /**
   * Returns why mitigation does not apply to the player.
   *
   * @return the reason, or {@code null} if the player is eligible for mitigation
   */
  @Nullable MitigationSkipReason skipReason();
}
