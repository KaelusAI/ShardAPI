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

import ac.shard.api.detection.LabelId;
import ac.shard.api.event.Cancellable;
import ac.shard.api.event.PlayerEvent;
import ac.shard.api.player.ShardPlayer;
import ac.shard.api.punishment.PunishmentGroup;
import java.util.Set;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.Nullable;

/**
 * Fired when a flag is recorded in a punishment group whose violation level is at or above its
 * lowest action level.
 *
 * <p>The actions that run are those of the highest action level not above {@link
 * #violationLevel()}, so the event fires on every further flag until a reset or expiry brings the
 * level below the lowest action level. The level is already recorded when the event fires and stays
 * recorded whatever the outcome. The player may have left by then.
 *
 * <p>Dispatched synchronously off the server thread, never on a tick or region thread, so do not
 * touch world state from a handler. Cancelling skips every action of this flag in this group:
 * console commands, alert, log, broadcast, wait and reset.
 *
 * @see ac.shard.api.event.detection.FlagEvent
 * @see ac.shard.api.punishment.PunishmentService
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface PunishmentEvent extends PlayerEvent, Cancellable {
  /**
   * Returns the group the flag was recorded in.
   *
   * @return the punishment group
   */
  PunishmentGroup group();

  /**
   * Returns the violation level of the group after this flag, counted within the group's expiry
   * window.
   *
   * @return the level, at least the lowest of {@link PunishmentGroup#actionLevels()}
   */
  int violationLevel();

  /**
   * Returns the public labels of the flag.
   *
   * @return the labels, possibly empty
   */
  @Unmodifiable
  Set<LabelId> labels();

  /**
   * Returns the live session of the player.
   *
   * @return the session, or {@code null} if the player has left
   */
  @Nullable ShardPlayer session();
}
