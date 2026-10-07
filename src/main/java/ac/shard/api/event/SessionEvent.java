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

import ac.shard.api.player.ShardPlayer;
import org.jetbrains.annotations.ApiStatus;

/**
 * Event about a player with a live session.
 *
 * <p>The session was live when Shard created the event. Events delivered on the event thread can
 * reach the handler after the player has left, so check {@link ShardPlayer#isValid()} before acting
 * on the session.
 *
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface SessionEvent extends PlayerEvent {
  /**
   * Returns the session of the player.
   *
   * @return the player session
   */
  ShardPlayer player();
}
