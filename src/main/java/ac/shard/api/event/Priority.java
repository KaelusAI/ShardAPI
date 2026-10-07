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

/**
 * Common handler priorities.
 *
 * <p>Any {@code int} is allowed as a priority. Handlers with a lower value run earlier, and
 * handlers with equal values run in subscription order.
 *
 * @see SubscriptionBuilder#priority(int)
 * @since 2.1.0
 */
public final class Priority {
  /** Priority {@code -1000}, for handlers that must run first. */
  public static final int EARLIEST = -1000;

  /** Priority {@code -100}, for handlers that run before the default. */
  public static final int EARLY = -100;

  /** Priority {@code 0}, the default. */
  public static final int NORMAL = 0;

  /** Priority {@code 100}, for handlers that run after the default. */
  public static final int LATE = 100;

  /** Priority {@code 1000}, for handlers that run after all others except monitors. */
  public static final int LATEST = 1000;

  private Priority() {
    throw new AssertionError();
  }
}
