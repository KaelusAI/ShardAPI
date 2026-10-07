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
package ac.shard.api.event.exemption;

import ac.shard.api.event.PlayerEvent;
import ac.shard.api.exemption.Exemption;
import org.jetbrains.annotations.ApiStatus;

/**
 * Fired after an exemption was granted, replaced, revoked or expired.
 *
 * <p>Covers exemptions from plugins and from admin commands, for online and offline players.
 *
 * <p>Dispatched on the Shard event thread. Not cancellable.
 *
 * @see ac.shard.api.exemption.ExemptionService
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface ExemptionChangeEvent extends PlayerEvent {
  /**
   * Kind of change. Constants may be added in later versions, so switches need a default branch.
   *
   * @since 2.1.0
   */
  enum Change {
    /** A new exemption, with no active one of the same owner and scope before it. */
    GRANTED,
    /**
     * A new exemption that replaced an active one of the same owner and scope. The replaced
     * exemption ends without an event of its own.
     */
    REPLACED,
    /** Revoked explicitly or because its owner plugin disabled. */
    REVOKED,
    /**
     * Its duration elapsed. Expiry is detected by a periodic sweep, so the event can follow {@link
     * Exemption#expiresAt()} with a short delay.
     */
    EXPIRED
  }

  /**
   * Returns the exemption that changed. For {@link Change#REPLACED} it is the new exemption.
   *
   * @return the exemption
   */
  Exemption exemption();

  /**
   * Returns the kind of change.
   *
   * @return the change
   */
  Change change();
}
