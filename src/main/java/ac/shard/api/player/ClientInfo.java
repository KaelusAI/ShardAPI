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

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

/**
 * What the client of one connection reported about itself.
 *
 * <p>Values are fixed when {@link ShardPlayer#client()} is called.
 *
 * @see ShardPlayer#client()
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface ClientInfo {
  /**
   * Returns the protocol version number of the client.
   *
   * @return the protocol version number
   */
  int protocolVersion();

  /**
   * Returns the brand the client sent, such as {@code "vanilla"}.
   *
   * <p>Clients are not required to send a brand, so it may never arrive.
   *
   * @return the brand, or {@code null} if the client has not sent one yet
   */
  @Nullable String brand();

  /**
   * Returns whether the player joined from Bedrock Edition through Geyser or Floodgate.
   *
   * @return {@code true} for a Bedrock player
   */
  boolean isBedrock();
}
