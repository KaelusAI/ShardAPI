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
package ac.shard.api;

import ac.shard.api.alert.AlertService;
import ac.shard.api.detection.DetectionService;
import ac.shard.api.event.EventBus;
import ac.shard.api.exemption.ExemptionService;
import ac.shard.api.history.HistoryService;
import ac.shard.api.mitigation.MitigationService;
import ac.shard.api.network.NetworkService;
import ac.shard.api.player.PlayerService;
import ac.shard.api.punishment.PunishmentService;
import org.jetbrains.annotations.ApiStatus;

/**
 * Root of the Shard API, obtained from {@link ShardProvider#get()}.
 *
 * <p>Shard registers one instance when it enables and keeps the same instance across {@code /shard
 * reload} and profile syncs. Every method is thread-safe.
 *
 * <p>After Shard disables, every method of this interface throws {@link IllegalStateException}, and
 * so does every service method that returns a {@link java.util.concurrent.CompletableFuture}.
 * Futures still pending at that moment complete exceptionally with {@link IllegalStateException}.
 *
 * @see ShardProvider
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface Shard {
  /**
   * Returns the version of the API classes loaded at runtime.
   *
   * <p>The API classes ship inside the Shard plugin, so this version can differ from the {@link
   * ApiVersion#COMPILED_MAJOR} and {@link ApiVersion#COMPILED_MINOR} constants the caller was
   * compiled against.
   *
   * @return the runtime API version
   * @throws IllegalStateException if Shard is disabled
   */
  ApiVersion apiVersion();

  /**
   * Returns the version string of the Shard plugin.
   *
   * <p>The value is informational and unrelated to {@link #apiVersion()}. Use {@link #apiVersion()}
   * to detect API features.
   *
   * @return the plugin version
   * @throws IllegalStateException if Shard is disabled
   */
  String pluginVersion();

  /**
   * Returns the service for online sessions and the player directory.
   *
   * @return the player service
   * @throws IllegalStateException if Shard is disabled
   */
  PlayerService players();

  /**
   * Returns the service for detection models, labels and buffers.
   *
   * @return the detection service
   * @throws IllegalStateException if Shard is disabled
   */
  DetectionService detection();

  /**
   * Returns the service for punishment groups and violation levels.
   *
   * @return the punishment service
   * @throws IllegalStateException if Shard is disabled
   */
  PunishmentService punishments();

  /**
   * Returns the service for damage mitigation.
   *
   * @return the mitigation service
   * @throws IllegalStateException if Shard is disabled
   */
  MitigationService mitigation();

  /**
   * Returns the service for exemptions granted by plugins.
   *
   * @return the exemption service
   * @throws IllegalStateException if Shard is disabled
   */
  ExemptionService exemptions();

  /**
   * Returns the service for staff alerts.
   *
   * @return the alert service
   * @throws IllegalStateException if Shard is disabled
   */
  AlertService alerts();

  /**
   * Returns the service for stored violation and mitigation history.
   *
   * @return the history service
   * @throws IllegalStateException if Shard is disabled
   */
  HistoryService history();

  /**
   * Returns the service for state shared with other servers of the network.
   *
   * @return the network service
   * @throws IllegalStateException if Shard is disabled
   */
  NetworkService network();

  /**
   * Returns the bus for subscribing to Shard events.
   *
   * @return the event bus
   * @throws IllegalStateException if Shard is disabled
   */
  EventBus events();
}
