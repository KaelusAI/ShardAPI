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
package ac.shard.api.event;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;

/**
 * Handle of one registered event handler.
 *
 * <p>A subscription closes when {@link #close()} is called, when {@link
 * EventBus#unsubscribeAll(Plugin)} runs for its owner, when the owner disables, or when Shard
 * disables. A closed subscription cannot be reopened. Every method is thread-safe.
 *
 * @see EventBus
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface Subscription extends AutoCloseable {
  /**
   * Returns the plugin that owns the subscription.
   *
   * @return the owner plugin
   */
  Plugin owner();

  /**
   * Returns the event type the subscription was registered for.
   *
   * @return the event type. The handler also receives its subtypes.
   */
  Class<? extends ShardEvent> eventType();

  /**
   * Returns the priority of the handler.
   *
   * @return the priority, lower runs earlier
   */
  int priority();

  /**
   * Returns whether the handler is a monitor.
   *
   * @return {@code true} if the handler runs after every non-monitor handler and may not change the
   *     outcome
   */
  boolean isMonitor();

  /**
   * Returns whether the subscription still receives events.
   *
   * @return {@code true} until the subscription closes
   */
  boolean isActive();

  /**
   * Closes the subscription.
   *
   * <p>Calling it again has no effect. Dispatches that have not reached the handler yet skip it. A
   * dispatch on another thread that has already passed the check may still call the handler once.
   */
  @Override
  void close();
}
