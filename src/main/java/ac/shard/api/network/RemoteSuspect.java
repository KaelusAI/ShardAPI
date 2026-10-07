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
package ac.shard.api.network;

import ac.shard.api.mitigation.MitigationTier;
import java.time.Instant;
import java.util.UUID;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

/**
 * A suspicious player as last published by another server.
 *
 * <p>Values are those the remote server had at {@link #updatedAt()}.
 *
 * @see NetworkService#suspects()
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface RemoteSuspect {
  /**
   * Returns the name of the server that published the entry.
   *
   * @return the remote server's name
   */
  String server();

  /**
   * Returns the player's UUID.
   *
   * @return the UUID
   */
  UUID playerId();

  /**
   * Returns the player's name on the remote server.
   *
   * @return the player name
   */
  String playerName();

  /**
   * Returns the player's highest buffer value across the remote server's active models.
   *
   * @return the buffer value
   */
  double buffer();

  /**
   * Returns the player's ping on the remote server.
   *
   * @return the ping in milliseconds, as reported by the remote server
   */
  int ping();

  /**
   * Returns the player's mitigation tier on the remote server.
   *
   * @return the tier, or {@code null} if no mitigation rule matched the player or the remote server
   *     sent a tier this version does not know
   */
  @Nullable MitigationTier tier();

  /**
   * Returns the player's mitigation score on the remote server.
   *
   * @return the mitigation score
   */
  double score();

  /**
   * Returns when the remote server published the entry.
   *
   * @return the publish time, from the remote server's clock
   */
  Instant updatedAt();
}
