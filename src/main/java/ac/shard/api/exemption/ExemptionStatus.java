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
package ac.shard.api.exemption;

import java.util.List;
import java.util.Set;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;

/**
 * What exempts one online player, taken when {@link ac.shard.api.player.ShardPlayer#exemption()} is
 * called.
 *
 * <p>Permission reasons can lag behind a permission change, because permissions are re-read
 * periodically and after teleports and world changes.
 *
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface ExemptionStatus {
  /**
   * Returns whether the player is exempt at a scope.
   *
   * @param scope the scope to test
   * @return {@code true} if an exemption of this scope or a broader one is in effect
   */
  boolean isExempt(ExemptionScope scope);

  /**
   * Returns why the player is exempt at a scope.
   *
   * @param scope the scope to test
   * @return the reasons from this scope and broader ones, or an empty set if the player is not
   *     exempt at this scope
   */
  @Unmodifiable
  Set<ExemptionReason> reasons(ExemptionScope scope);

  /**
   * Returns the active granted exemptions of the player.
   *
   * @return the exemptions from plugins and admin commands, of any scope, or an empty list if there
   *     are none
   */
  @Unmodifiable
  List<Exemption> exemptions();
}
