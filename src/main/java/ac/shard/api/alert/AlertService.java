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

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;

/**
 * Staff alert subscriptions and sending of plugin alerts through Shard's alert channels.
 *
 * <p>Subscriptions are held in memory for online players and cleared when the player quits. On
 * join, Shard subscribes staff who have both the permission of a type and that permission with an
 * {@code .enable-on-join} suffix.
 *
 * @see ac.shard.api.event.alert.AlertEvent
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface AlertService {
  /**
   * Returns whether a player is subscribed to an alert type. Safe to call from any thread.
   *
   * @param staff the online player
   * @param type the alert type
   * @return {@code true} if the player is subscribed
   */
  boolean isSubscribed(Player staff, AlertType type);

  /**
   * Subscribes or unsubscribes a player for an alert type without notifying the player.
   *
   * <p>The permission of the type is not checked here. A subscribed player without it receives
   * nothing. The subscription lasts until the player quits, and a player who is no longer online is
   * not subscribed.
   *
   * @param staff the online player
   * @param type the alert type
   * @param subscribed {@code true} to subscribe, {@code false} to unsubscribe
   */
  void setSubscribed(Player staff, AlertType type, boolean subscribed);

  /**
   * Starts a new alert.
   *
   * @param initiator the plugin recorded as the alert's initiator
   * @return a new builder
   */
  Alert.Builder builder(Plugin initiator);

  /**
   * Sends an alert, on the calling thread.
   *
   * <p>The steps are:
   *
   * <ul>
   *   <li>If any handler listens, an {@link ac.shard.api.event.alert.AlertEvent} is fired
   *       synchronously. Cancelling it stops the steps below.
   *   <li>The message goes to every subscribed online player who has the permission of the type,
   *       and to the console if console alerts are enabled.
   *   <li>If {@link Alert#isNetwork()} is set and the network is enabled, the alert is published to
   *       other servers. {@link AlertType#CUSTOM} alerts are always published, {@link
   *       AlertType#FLAG} and {@link AlertType#SUSPICIOUS} alerts only if the server shares that
   *       type, and other types never.
   * </ul>
   *
   * @param alert the alert to send
   */
  void send(Alert alert);
}
