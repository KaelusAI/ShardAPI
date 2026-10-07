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
package ac.shard.api.exemption;

import ac.shard.api.Initiator;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;

/**
 * One owner's exemption of one player.
 *
 * <p>Exemptions are held in memory and lost on restart. Methods are thread-safe.
 *
 * @see ExemptionService
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface Exemption {
  /**
   * Returns who granted the exemption.
   *
   * @return the owning plugin, or the command sender for an exemption granted by admin command
   */
  Initiator owner();

  /**
   * Returns the UUID of the exempt player.
   *
   * @return the UUID
   */
  UUID playerId();

  /**
   * Returns what the exemption covers.
   *
   * @return the scope
   */
  ExemptionScope scope();

  /**
   * Returns when the exemption was granted.
   *
   * @return the grant time
   */
  Instant grantedAt();

  /**
   * Returns when the exemption expires.
   *
   * @return the expiry time, or {@code null} if it lasts until revoked or the owner disables
   */
  @Nullable Instant expiresAt();

  /**
   * Returns the reason given when the exemption was granted.
   *
   * @return the reason, or {@code null} if none was given
   */
  @Nullable String reason();

  /**
   * Returns whether the exemption is in effect.
   *
   * <p>An exemption stops being active when it expires, is revoked, is replaced by a new grant of
   * the same owner and scope, or its owner plugin disables.
   *
   * @return {@code true} while in effect
   */
  boolean isActive();

  /**
   * Ends the exemption and fires {@link ac.shard.api.event.exemption.ExemptionChangeEvent}.
   *
   * <p>An exemption that has already expired is not revoked. The event then reports the expiry.
   *
   * @return {@code true} if this call revoked it, or {@code false} if it had already expired or
   *     been revoked or replaced
   */
  boolean revoke();

  /**
   * Collects the parameters of an exemption.
   *
   * <p>Obtained from {@link ExemptionService#builder(org.bukkit.plugin.Plugin, UUID)}. A builder is
   * not thread-safe.
   *
   * @since 2.1.0
   */
  @ApiStatus.NonExtendable
  interface Builder {
    /**
     * Sets what the exemption covers.
     *
     * @param scope the scope, {@link ExemptionScope#ENFORCEMENT} by default
     * @return this builder
     */
    @Contract("_ -> this")
    Builder scope(ExemptionScope scope);

    /**
     * Sets how long the exemption lasts, counted from {@link #grant()}.
     *
     * <p>Without a duration, the exemption lasts until revoked or the owner disables.
     *
     * @param duration a positive duration
     * @return this builder
     * @throws IllegalArgumentException if {@code duration} is zero or negative
     */
    @Contract("_ -> this")
    Builder duration(Duration duration);

    /**
     * Sets a free-text reason.
     *
     * @param reason the reason
     * @return this builder
     */
    @Contract("_ -> this")
    Builder reason(String reason);

    /**
     * Registers the exemption.
     *
     * <p>An exemption of the same player by the same owner with the same scope is replaced. Fires
     * {@link ac.shard.api.event.exemption.ExemptionChangeEvent}.
     *
     * @return the granted exemption
     * @throws IllegalArgumentException if the owner plugin is not enabled
     */
    Exemption grant();
  }
}
