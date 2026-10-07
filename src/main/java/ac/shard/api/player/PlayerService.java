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
package ac.shard.api.player;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;

/**
 * Online sessions and the player directory.
 *
 * <p>Session methods read memory and are thread-safe. Directory methods query Shard's database,
 * which every server using it shares, and their futures complete off the server thread. While the
 * database is unavailable, results can be incomplete. Any other storage error completes the future
 * exceptionally with {@link ac.shard.api.ShardStorageException}. If Shard disables first, the
 * future completes exceptionally with {@link IllegalStateException}.
 *
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface PlayerService {
  /**
   * Returns the session of an online player.
   *
   * <p>The session is present from the moment Shard attaches to the player until disconnect
   * cleanup.
   *
   * @param playerId the player's UUID
   * @return the session, or empty if the player has no attached session
   */
  Optional<ShardPlayer> player(UUID playerId);

  /**
   * Returns the session of an online player, looked up by the player's UUID.
   *
   * @param player the Bukkit player
   * @return the session, or empty if the player has no attached session
   * @see #player(UUID)
   */
  Optional<ShardPlayer> player(Player player);

  /**
   * Returns the sessions of all online players.
   *
   * @return a copy taken at the time of the call, in no particular order
   */
  @Unmodifiable
  Collection<ShardPlayer> players();

  /**
   * Resolves a player by name or UUID string.
   *
   * <p>Sources are tried in order, and the first match wins:
   *
   * <ul>
   *   <li>an online player whose name matches, ignoring case
   *   <li>Shard's database, by UUID, or by exact name ignoring case with the most recently seen
   *       player first
   *   <li>the server's user cache
   * </ul>
   *
   * @param nameOrId a player name or a UUID in standard string form. Surrounding whitespace is
   *     ignored.
   * @return a future completing with the player, or empty if no source knows it or the input is
   *     blank
   * @throws IllegalStateException if Shard is disabled
   */
  CompletableFuture<Optional<KnownPlayer>> lookup(String nameOrId);

  /**
   * Finds players in Shard's database whose name starts with a prefix, ignoring case.
   *
   * @param namePrefix the prefix, matched literally without wildcards
   * @param limit the maximum number of results, from 1 to 100
   * @return a future completing with the matches, most recently seen first, or an empty list if
   *     none match
   * @throws IllegalArgumentException if {@code limit} is outside 1 to 100
   * @throws IllegalStateException if Shard is disabled
   */
  CompletableFuture<List<KnownPlayer>> search(String namePrefix, int limit);
}
