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

import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.ApiStatus;

/**
 * Cross-server state shared between Shard servers through Redis.
 *
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface NetworkService {
  /**
   * Returns whether alert sharing over the network is running.
   *
   * @return {@code true} if the network is enabled in the configuration and Redis was available at
   *     startup
   */
  boolean isEnabled();

  /**
   * Returns the name this server publishes under.
   *
   * @return the configured server name, or the default name if the network is disabled
   */
  String serverName();

  /**
   * Fetches the suspicious players published by other servers.
   *
   * <p>Entries published under this server's own name are excluded. An entry expires once the
   * server that published it stops refreshing it. Malformed entries are skipped.
   *
   * @return a future that completes off the server thread with the suspects, empty when the network
   *     or suspicious-player sharing is disabled. It completes exceptionally with {@link
   *     ac.shard.api.ShardStorageException} if Redis cannot be read, or with {@link
   *     IllegalStateException} if Shard is disabled before it completes.
   * @throws IllegalStateException if Shard is disabled
   */
  CompletableFuture<List<RemoteSuspect>> suspects();
}
