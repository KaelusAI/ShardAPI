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

import ac.shard.api.detection.ModelState;
import ac.shard.api.detection.Verdict;
import ac.shard.api.event.SessionEvent;
import org.jetbrains.annotations.ApiStatus;

/**
 * Fired after a model replied for a player and the reply was added to the buffers.
 *
 * <p>Dispatched on the Shard event thread after any {@link FlagEvent} caused by the same reply. Not
 * cancellable. Under load these events are the first to be dropped, so a handler may miss some
 * replies.
 *
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface VerdictEvent extends SessionEvent {
  /**
   * Returns the reply of the model.
   *
   * @return the verdict
   */
  Verdict verdict();

  /**
   * Returns the buffers after the reply was added. With shared buffers they belong to the owning
   * model, which may differ from {@link Verdict#modelId()}.
   *
   * @return the buffer state of the model that owns the buffers
   */
  ModelState state();
}
