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
package ac.shard.api.punishment;

import ac.shard.api.detection.LabelId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.Nullable;

/**
 * Punishment groups and violation levels.
 *
 * <p>Group methods read the loaded configuration in memory. Violation-level methods query Shard's
 * database, which every server using it shares, and their futures complete off the server thread.
 * While the database is unavailable, results can be incomplete. Any other storage error completes
 * the future exceptionally with {@link ac.shard.api.ShardStorageException}. If Shard disables
 * first, the future completes exceptionally with {@link IllegalStateException}.
 *
 * @see PunishmentGroup
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface PunishmentService {
  /**
   * Returns every configured group.
   *
   * @return all groups, nested ones included, depth first with each parent before its children and
   *     each fallback group after its siblings
   */
  @Unmodifiable
  List<PunishmentGroup> groups();

  /**
   * Returns the group with a path key.
   *
   * @param key the path key
   * @return the group, or empty if no group has this key
   * @see PunishmentGroup#key()
   */
  Optional<PunishmentGroup> group(String key);

  /**
   * Returns the group a flag on a label would be routed to under the current configuration.
   *
   * @param label the label
   * @return the group, or empty if no group takes the label
   */
  Optional<PunishmentGroup> route(LabelId label);

  /**
   * Returns the violation level of a player in one group.
   *
   * <p>The level is the number of flags recorded in the group within its {@linkplain
   * PunishmentGroup#expiry() expiry window}. An unknown key is not rejected, and flags stored under
   * it are counted without expiry.
   *
   * @param playerId the player's UUID, online or offline
   * @param groupKey the group's path key
   * @return a future completing with the level, or 0 if no flags count
   * @throws IllegalStateException if Shard is disabled
   */
  CompletableFuture<Integer> violationLevel(UUID playerId, String groupKey);

  /**
   * Returns the violation levels of a player in every configured group.
   *
   * @param playerId the player's UUID, online or offline
   * @return a future completing with non-zero levels keyed by group path key, or an empty map if
   *     the player has none
   * @throws IllegalStateException if Shard is disabled
   */
  CompletableFuture<Map<String, Integer>> violationLevels(UUID playerId);

  /**
   * Clears the violation level of a player in one group.
   *
   * <p>Deletes the player's stored flags in the group, then fires {@link
   * ac.shard.api.event.punishment.ViolationLevelResetEvent}. Subgroups keep their levels.
   *
   * @param initiator the plugin requesting the reset, reported in the event
   * @param playerId the player's UUID, online or offline
   * @param groupKey the group's path key
   * @return a future completing with {@code null} once the flags are deleted
   * @throws IllegalArgumentException if no configured group has this key
   * @throws IllegalStateException if Shard is disabled
   */
  CompletableFuture<@Nullable Void> resetViolationLevel(
      Plugin initiator, UUID playerId, String groupKey);

  /**
   * Clears the violation levels of a player in every group.
   *
   * <p>Deletes all stored flags of the player, including those under keys no longer configured,
   * then fires one {@link ac.shard.api.event.punishment.ViolationLevelResetEvent}.
   *
   * @param initiator the plugin requesting the reset, reported in the event
   * @param playerId the player's UUID, online or offline
   * @return a future completing with {@code null} once the flags are deleted
   * @throws IllegalStateException if Shard is disabled
   */
  CompletableFuture<@Nullable Void> resetViolationLevels(Plugin initiator, UUID playerId);
}
