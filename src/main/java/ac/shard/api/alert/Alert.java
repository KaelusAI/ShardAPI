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
package ac.shard.api.alert;

import ac.shard.api.Initiator;
import java.util.UUID;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;

/**
 * A staff alert built by a plugin, ready to be passed to {@link AlertService#send(Alert)}.
 *
 * <p>Instances are immutable and come from {@link AlertService#builder(org.bukkit.plugin.Plugin)}.
 * Alerts received from other servers are not represented by this type. They are visible through
 * {@link ac.shard.api.event.alert.AlertEvent}.
 *
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface Alert {
  /**
   * Returns who created the alert.
   *
   * @return the plugin initiator passed to {@link AlertService#builder(org.bukkit.plugin.Plugin)}
   */
  Initiator initiator();

  /**
   * Returns the channel the alert is delivered on.
   *
   * @return the alert type
   */
  AlertType type();

  /**
   * Returns the player the alert is about.
   *
   * @return the player's UUID, or {@code null} if none was set
   */
  @Nullable UUID subject();

  /**
   * Returns the alert text as MiniMessage markup.
   *
   * <p>Shard parses the markup as is and adds no prefix.
   *
   * @return the message
   */
  String message();

  /**
   * Returns whether {@link AlertService#send(Alert)} also publishes the alert to other servers.
   *
   * @return {@code true} if the alert is meant for the network
   */
  boolean isNetwork();

  /**
   * Collects the parameters of an alert.
   *
   * <p>A builder is not thread-safe. Each setter returns this builder.
   *
   * @see AlertService#builder(org.bukkit.plugin.Plugin)
   * @since 2.1.0
   */
  @ApiStatus.NonExtendable
  interface Builder {
    /**
     * Sets the channel. Defaults to {@link AlertType#CUSTOM}.
     *
     * @param type the alert type
     * @return this builder
     */
    @Contract("_ -> this")
    Builder type(AlertType type);

    /**
     * Sets the player the alert is about. No subject is set by default.
     *
     * @param playerId the player's UUID
     * @return this builder
     */
    @Contract("_ -> this")
    Builder subject(UUID playerId);

    /**
     * Sets the alert text. Required.
     *
     * @param miniMessage the text as MiniMessage markup
     * @return this builder
     */
    @Contract("_ -> this")
    Builder message(String miniMessage);

    /**
     * Sets whether the alert is also published to other servers. Defaults to {@code false}.
     *
     * @param network {@code true} to publish the alert to the network
     * @return this builder
     */
    @Contract("_ -> this")
    Builder network(boolean network);

    /**
     * Creates the alert. It is not sent until passed to {@link AlertService#send(Alert)}.
     *
     * @return the alert
     * @throws IllegalStateException if no message was set
     */
    Alert build();
  }
}
