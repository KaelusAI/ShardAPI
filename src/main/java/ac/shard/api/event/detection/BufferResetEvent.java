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
package ac.shard.api.event.detection;

import ac.shard.api.Initiator;
import ac.shard.api.detection.LabelId;
import ac.shard.api.event.PlayerEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

/**
 * Fired after detection buffers of a player were reset by a command or through {@link
 * ac.shard.api.detection.DetectionService}.
 *
 * <p>Resets through the API also cover offline players. The event follows the reset of the
 * in-memory buffers and, for API resets, the write to storage. Buffers consumed by a flag do not
 * fire it.
 *
 * <p>Dispatched on the Shard event thread. Not cancellable.
 *
 * @see ac.shard.api.detection.DetectionService
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface BufferResetEvent extends PlayerEvent {
  /**
   * Returns who requested the reset.
   *
   * @return the command sender or plugin
   */
  Initiator initiator();

  /**
   * Returns the model whose buffers were reset.
   *
   * @return the model id, or {@code null} when the buffers of every model were reset
   */
  @Nullable String modelId();

  /**
   * Returns the single label whose buffer was reset.
   *
   * @return the label, or {@code null} when every buffer of the model, or of every model, was reset
   */
  @Nullable LabelId label();
}
