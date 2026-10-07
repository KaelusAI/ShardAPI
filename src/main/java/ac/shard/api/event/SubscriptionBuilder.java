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

import java.util.function.Consumer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

/**
 * Collects the options of a subscription before it is registered.
 *
 * <p>Obtained from {@link EventBus#subscription(org.bukkit.plugin.Plugin, Class)}. A builder is not
 * thread-safe and is meant to be used by one thread.
 *
 * @param <E> the event type the handler receives
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface SubscriptionBuilder<E extends ShardEvent> {
  /**
   * Sets the priority of the handler.
   *
   * <p>Handlers with a lower value run earlier. Any {@code int} is allowed. The default is {@link
   * Priority#NORMAL}.
   *
   * @param priority the priority, see {@link Priority} for common values
   * @return this builder
   */
  @Contract("_ -> this")
  SubscriptionBuilder<E> priority(int priority);

  /**
   * Sets whether to skip the handler for an event that is already cancelled when its turn comes.
   *
   * <p>Applies only to events implementing {@link Cancellable}. The default is {@code false}.
   *
   * @param ignore {@code true} to skip the handler for cancelled events
   * @return this builder
   */
  @Contract("_ -> this")
  SubscriptionBuilder<E> ignoreCancelled(boolean ignore);

  /**
   * Makes the handler a monitor.
   *
   * <p>Monitors run after every non-monitor handler, in their own priority order, and see the final
   * outcome. A monitor may not change it: {@link Cancellable#setCancelled(boolean)} throws {@link
   * IllegalStateException} when called from a monitor.
   *
   * @return this builder
   */
  @Contract("-> this")
  SubscriptionBuilder<E> monitor();

  /**
   * Registers the handler with the collected options.
   *
   * <p>The handler receives events dispatched after this call returns.
   *
   * @param handler the handler to call for each event of type {@code E} or a subtype
   * @return the handle of the new, active subscription
   * @throws IllegalArgumentException if the owner plugin is not enabled
   */
  Subscription subscribe(Consumer<? super E> handler);
}
