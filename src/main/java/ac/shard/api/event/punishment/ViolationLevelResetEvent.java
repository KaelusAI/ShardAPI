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
package ac.shard.api.event.punishment;

import ac.shard.api.Initiator;
import ac.shard.api.event.PlayerEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

/**
 * Fired after violation levels of a player were reset in storage.
 *
 * <p>Sources:
 *
 * <ul>
 *   <li>the {@code /shard punish reset} command, which resets every group
 *   <li>{@link ac.shard.api.punishment.PunishmentService#resetViolationLevel} and {@link
 *       ac.shard.api.punishment.PunishmentService#resetViolationLevels}
 *   <li>a {@code [reset]} punishment action, which resets the group it belongs to
 * </ul>
 *
 * <p>Covers offline players.
 *
 * <p>Dispatched on the Shard event thread. Not cancellable.
 *
 * @see ac.shard.api.punishment.PunishmentService
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface ViolationLevelResetEvent extends PlayerEvent {
  /**
   * Returns who requested the reset.
   *
   * @return the command sender, the plugin, or Shard for a {@code [reset]} action
   */
  Initiator initiator();

  /**
   * Returns the group whose level was reset.
   *
   * @return the group key, or {@code null} when every group was reset
   */
  @Nullable String groupKey();
}
