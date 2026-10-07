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

import java.util.List;
import java.util.function.Consumer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;

/**
 * Shard's own event bus, separate from Bukkit events.
 *
 * <p>Events implementing {@link Cancellable} are dispatched synchronously on the thread making the
 * decision, which each event documents. Such handlers must not block or do I/O. All other events
 * are dispatched on a single Shard thread, in the order Shard produced them. That thread is never a
 * tick, region or network thread. Subscribing to a supertype receives every subtype, so such a
 * handler may run on several threads concurrently.
 *
 * <p>Delivery of non-cancellable events is best effort. If handlers fall far behind, Shard drops
 * events, {@link ac.shard.api.event.detection.VerdictEvent} first.
 *
 * <p>Handlers run in ascending priority, ties in subscription order, then monitors in the same
 * order. A handler that throws is logged through its owner's logger and dispatch continues. A
 * subscription added during a dispatch receives events from the next dispatch. A subscription
 * closed during a dispatch is skipped if the dispatch has not reached it yet.
 *
 * <p>Subscriptions close when their owner disables. When Shard disables, events not yet delivered
 * may be discarded, and every subscription closes. All methods are thread-safe.
 *
 * @see ac.shard.api.Shard#events()
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface EventBus {
  /**
   * Registers a handler with priority {@link Priority#NORMAL}, not as a monitor, and called for
   * cancelled events too.
   *
   * @param owner the plugin that owns the subscription. Its disable closes the subscription.
   * @param type the event type to receive, subtypes included
   * @param handler the handler to call for each event
   * @param <E> the event type
   * @return the handle of the new, active subscription
   * @throws IllegalArgumentException if {@code owner} is not enabled
   * @see #subscription(Plugin, Class)
   */
  <E extends ShardEvent> Subscription subscribe(
      Plugin owner, Class<E> type, Consumer<? super E> handler);

  /**
   * Starts building a subscription with custom options.
   *
   * <p>Nothing is registered until {@link SubscriptionBuilder#subscribe(Consumer)} is called.
   *
   * @param owner the plugin that will own the subscription
   * @param type the event type to receive, subtypes included
   * @param <E> the event type
   * @return a new builder with default options
   */
  <E extends ShardEvent> SubscriptionBuilder<E> subscription(Plugin owner, Class<E> type);

  /**
   * Returns the active subscriptions of a plugin.
   *
   * @param owner the plugin to look up
   * @return a snapshot of the active subscriptions owned by {@code owner}, empty if there are none
   */
  @Unmodifiable
  List<Subscription> subscriptions(Plugin owner);

  /**
   * Closes every active subscription of a plugin.
   *
   * @param owner the plugin whose subscriptions to close
   * @return the number of subscriptions this call closed
   */
  int unsubscribeAll(Plugin owner);
}
