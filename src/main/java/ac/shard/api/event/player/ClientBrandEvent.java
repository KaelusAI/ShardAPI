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
package ac.shard.api.event.player;

import ac.shard.api.event.SessionEvent;
import org.jetbrains.annotations.ApiStatus;

/**
 * Fired when the client reported its brand, at most once per session.
 *
 * <p>Always follows the {@link SessionStartEvent} of the same session. A brand received before the
 * session started is reported right after that event. Clients that never send a brand produce no
 * event.
 *
 * <p>Dispatched on the Shard event thread. Not cancellable.
 *
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface ClientBrandEvent extends SessionEvent {
  /**
   * Returns the brand with color codes and a trailing {@code " (Velocity)"} removed. An empty
   * payload or one over 64 bytes is reported as {@code "invalid (<n> bytes)"}.
   *
   * @return the client brand
   */
  String brand();
}
