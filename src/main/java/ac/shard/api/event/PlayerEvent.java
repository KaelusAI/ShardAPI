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
package ac.shard.api.event;

import java.util.UUID;
import org.jetbrains.annotations.ApiStatus;

/**
 * Event about a player who may be offline when it fires.
 *
 * @see SessionEvent
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface PlayerEvent extends ShardEvent {
  /**
   * Returns the UUID of the player.
   *
   * @return the player UUID
   */
  UUID playerId();

  /**
   * Returns the name of the player.
   *
   * <p>For an offline player the name comes from Shard's player directory or the server's cache.
   * The UUID string is used only when neither knows the player.
   *
   * @return the player name, or {@code playerId().toString()} if the name is unknown
   */
  String playerName();
}
