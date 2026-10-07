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

import org.jetbrains.annotations.ApiStatus;

/**
 * Event whose action handlers can veto.
 *
 * <p>Shard dispatches these events synchronously on the thread making the decision and reads the
 * cancelled state once every handler has run. The state is atomic, so it is readable and writable
 * from any thread during dispatch.
 *
 * @see SubscriptionBuilder#ignoreCancelled(boolean)
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface Cancellable {
  /**
   * Returns whether a handler has vetoed the action.
   *
   * @return {@code true} if the action is cancelled
   */
  boolean isCancelled();

  /**
   * Vetoes or restores the action.
   *
   * @param cancelled {@code true} to veto the action, {@code false} to let it proceed
   * @throws IllegalStateException if called from a monitor handler or after dispatch has ended
   */
  void setCancelled(boolean cancelled);
}
