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
package ac.shard.api.detection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.Nullable;

/**
 * Models and labels of the current model profile, and reset of player buffers.
 *
 * <p>Model information comes from the profile the inference backend last sent and is read from
 * memory. Each call builds new {@link Model} and {@link Label} instances.
 *
 * @see DetectionSnapshot
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface DetectionService {
  /**
   * Returns the active models, primary model first.
   *
   * @return the active models, empty until the first model profile is received
   */
  @Unmodifiable
  List<Model> models();

  /**
   * Returns the primary model.
   *
   * @return the primary model, empty until the first model profile is received
   */
  Optional<Model> primaryModel();

  /**
   * Returns the active model with the given id.
   *
   * @param modelId the model id
   * @return the model, empty if no active model has this id
   */
  Optional<Model> model(String modelId);

  /**
   * Returns the public label with the given id.
   *
   * @param id the label id
   * @return the label, empty if its model is not active or has no public label with this key
   */
  Optional<Label> label(LabelId id);

  /**
   * Clears every buffer of the player, online or offline.
   *
   * <p>The in-memory buffers of an online player are cleared before the call returns, and buffers
   * still being restored after the player joined do not bring them back. Stored buffers are deleted
   * off the server thread if buffer persistence is enabled. A {@link
   * ac.shard.api.event.detection.BufferResetEvent} is then published with a {@code null} model and
   * label, and the future completes.
   *
   * @param initiator the plugin recorded as the initiator of the reset
   * @param playerId the player's UUID
   * @return a future that completes with {@code null} after storage is written. It completes
   *     exceptionally with {@link ac.shard.api.ShardStorageException} if storage fails, or with
   *     {@link IllegalStateException} if Shard is disabled before it completes.
   * @throws IllegalStateException if Shard is disabled
   */
  CompletableFuture<@Nullable Void> resetBuffers(Plugin initiator, UUID playerId);

  /**
   * Clears every buffer one model holds for the player, online or offline, the unattributed buffer
   * included.
   *
   * <p>The in-memory buffers of an online player are cleared before the call returns, and buffers
   * still being restored after the player joined do not bring them back. Stored buffers of this
   * model are deleted off the server thread if buffer persistence is enabled, and those of other
   * models are kept. A {@link ac.shard.api.event.detection.BufferResetEvent} is then published with
   * this model id and a {@code null} label, and the future completes.
   *
   * @param initiator the plugin recorded as the initiator of the reset
   * @param playerId the player's UUID
   * @param modelId the id of an active model
   * @return a future that completes with {@code null} after storage is written. It completes
   *     exceptionally with {@link ac.shard.api.ShardStorageException} if storage fails, or with
   *     {@link IllegalStateException} if Shard is disabled before it completes.
   * @throws IllegalArgumentException if no active model has this id, including when no model
   *     profile has been received yet
   * @throws IllegalStateException if Shard is disabled
   */
  CompletableFuture<@Nullable Void> resetBuffers(Plugin initiator, UUID playerId, String modelId);

  /**
   * Clears the buffer of one label for the player, online or offline.
   *
   * <p>The in-memory buffer of an online player is cleared before the call returns, and buffers
   * still being restored after the player joined do not bring it back. The stored buffer of this
   * label is deleted off the server thread if buffer persistence is enabled, and the others are
   * kept. A {@link ac.shard.api.event.detection.BufferResetEvent} is then published with this
   * label, and the future completes. The label is not checked against the active models, and the
   * future completes normally if the player has no buffer for it.
   *
   * @param initiator the plugin recorded as the initiator of the reset
   * @param playerId the player's UUID
   * @param label the label whose buffer is cleared
   * @return a future that completes with {@code null} after storage is written. It completes
   *     exceptionally with {@link ac.shard.api.ShardStorageException} if storage fails, or with
   *     {@link IllegalStateException} if Shard is disabled before it completes.
   * @throws IllegalStateException if Shard is disabled
   */
  CompletableFuture<@Nullable Void> resetBuffer(Plugin initiator, UUID playerId, LabelId label);
}
