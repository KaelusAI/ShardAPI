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

import ac.shard.api.detection.DetectionSnapshot;
import ac.shard.api.exemption.ExemptionStatus;
import ac.shard.api.mitigation.MitigationSnapshot;
import java.time.Instant;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

/**
 * Live handle of one connection.
 *
 * <p>Every getter is safe to call from any thread. Getters other than {@link #bukkitPlayer()}
 * return immutable snapshots taken at the time of the call. After the session ends, {@link
 * #isValid()} returns {@code false} and getters return the final state.
 *
 * @see PlayerService#player(UUID)
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface ShardPlayer {
  /**
   * Returns the player's UUID.
   *
   * @return the UUID
   */
  UUID playerId();

  /**
   * Returns the player's name.
   *
   * @return the name
   */
  String name();

  /**
   * Returns the Bukkit player of this connection.
   *
   * <p>Interact with it only on its owning thread, which is the entity scheduler on Folia.
   *
   * @return the Bukkit player
   */
  Player bukkitPlayer();

  /**
   * Returns when Shard registered the connection.
   *
   * @return the registration time
   */
  Instant joinedAt();

  /**
   * Returns what the client reported about itself.
   *
   * @return the client details
   */
  ClientInfo client();

  /**
   * Returns whether the session is still live.
   *
   * @return {@code true} until the session ends
   */
  boolean isValid();

  /**
   * Returns whether the player's persisted buffers have been restored.
   *
   * <p>Until then, {@link #detection()} lacks buffers from earlier sessions.
   *
   * @return {@code true} once restoration has finished
   */
  boolean isReady();

  /**
   * Returns the detection state of the player.
   *
   * @return a snapshot of the detection state
   */
  DetectionSnapshot detection();

  /**
   * Returns the mitigation state of the player.
   *
   * @return a snapshot of the mitigation state
   */
  MitigationSnapshot mitigation();

  /**
   * Returns what exempts the player.
   *
   * @return a snapshot of the exemption state
   */
  ExemptionStatus exemption();
}
