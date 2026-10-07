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

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

/**
 * Control over damage mitigation.
 *
 * <p>If Shard disables before a future completes, it completes exceptionally with {@link
 * IllegalStateException}.
 *
 * @see MitigationSnapshot
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface MitigationService {
  /**
   * Ends the applied rule of an online player and returns the tier to {@link MitigationTier#NONE}.
   *
   * <p>The release runs on the rule engine's thread, which is the main thread, or the global region
   * thread on Folia. Called from the main thread of a non-Folia server, it runs before the call
   * returns. If mitigation logging is enabled, the ended period is written to the history. Rules
   * may engage again on later evaluations. Fires {@link
   * ac.shard.api.event.mitigation.MitigationChangeEvent}.
   *
   * @param initiator the plugin requesting the release, reported in the event
   * @param playerId the player's UUID
   * @return a future completing with {@code true} if a rule was released, or {@code false} if no
   *     rule was applied or the player is offline
   * @throws IllegalStateException if Shard is disabled
   */
  CompletableFuture<Boolean> release(Plugin initiator, UUID playerId);

  /**
   * Resets the persisted mitigation score of a player to 0.
   *
   * <p>The in-memory score of an online player is cleared before the call returns, and a score
   * still being restored after the player joined does not bring it back. A stored score is set to 0
   * if one exists, online or offline, and the future completes after that write. The applied rule
   * is not released, see {@link #release(Plugin, UUID)}. No event is fired.
   *
   * <p>The future completes off the server thread. While the database is unavailable, the write is
   * kept by Shard and stored later. Any other storage error completes the future exceptionally with
   * {@link ac.shard.api.ShardStorageException}.
   *
   * @param initiator the plugin requesting the reset
   * @param playerId the player's UUID, online or offline
   * @return a future completing with {@code null} once the score is saved
   * @throws IllegalStateException if Shard is disabled
   */
  CompletableFuture<@Nullable Void> clearScore(Plugin initiator, UUID playerId);
}
