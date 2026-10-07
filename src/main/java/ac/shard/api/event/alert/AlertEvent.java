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
package ac.shard.api.event.alert;

import ac.shard.api.Initiator;
import ac.shard.api.alert.AlertType;
import ac.shard.api.event.Cancellable;
import ac.shard.api.event.ShardEvent;
import java.util.UUID;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

/**
 * Fired before a staff alert is delivered, whether raised on this server or received from another
 * server of the network.
 *
 * <p>Dispatched synchronously on the thread that raised the alert:
 *
 * <ul>
 *   <li>the calling thread for {@link ac.shard.api.alert.AlertService#send}
 *   <li>a network I/O thread for {@link AlertType#BRAND}
 *   <li>the player's owning thread or the global region thread for the other built-in types
 *   <li>the global region thread (the main thread outside Folia) for remote alerts
 * </ul>
 *
 * <p>Do not call thread-bound Bukkit API from a handler. Cancelling skips delivery to staff and the
 * console and, for a local alert, the publish to other servers.
 *
 * @see ac.shard.api.alert.AlertService
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface AlertEvent extends ShardEvent, Cancellable {
  /**
   * Returns the channel the alert is delivered on.
   *
   * @return the alert type
   */
  AlertType type();

  /**
   * Returns the player the alert is about, as set by {@link
   * ac.shard.api.alert.Alert.Builder#subject(UUID)}.
   *
   * @return the subject, or {@code null} for remote alerts and for alerts Shard raises itself
   */
  @Nullable UUID subject();

  /**
   * Returns the rendered message with formatting removed. A remote alert includes the prefix that
   * names its origin server.
   *
   * @return the message as plain text
   */
  String plainText();

  /**
   * Returns the network name of the server that raised the alert.
   *
   * @return {@link ac.shard.api.network.NetworkService#serverName()} for a local alert, the name
   *     the sending server published under for a remote alert
   */
  String originServer();

  /**
   * Returns whether the alert was received from another server.
   *
   * @return {@code true} for an alert received over the network
   */
  boolean isRemote();

  /**
   * Returns who raised the alert.
   *
   * @return the sending plugin for alerts sent through the API, Shard for built-in alerts, or
   *     {@code null} for remote alerts
   */
  @Nullable Initiator initiator();
}
