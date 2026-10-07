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

import java.time.Instant;
import java.util.UUID;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

/**
 * A player Shard has seen, online or not.
 *
 * <p>Instances are immutable and reflect the source at the time of the query.
 *
 * @see PlayerService#lookup(String)
 * @see PlayerService#search(String, int)
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface KnownPlayer {
  /**
   * Returns the player's UUID.
   *
   * @return the UUID
   */
  UUID playerId();

  /**
   * Returns the player's last known name.
   *
   * @return the name
   */
  String name();

  /**
   * Returns when the player was last seen.
   *
   * <p>For a player found online, this is the time of the query.
   *
   * @return the last-seen time, or {@code null} if the source does not know it
   */
  @Nullable Instant lastSeen();
}
