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
package ac.shard.api.event.mitigation;

import ac.shard.api.event.Cancellable;
import ac.shard.api.event.SessionEvent;
import ac.shard.api.mitigation.MitigationChannel;
import ac.shard.api.mitigation.MitigationTier;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

/**
 * Fired before mitigation changes one hit or heal of the mitigated player, {@link #player()}.
 *
 * <p>Fires only when the channel has an active effect. For {@link MitigationChannel#CANCEL} it
 * fires only after the chance roll succeeded, and only for melee hits.
 *
 * <p>Dispatched synchronously inside the Bukkit {@code EntityDamageByEntityEvent} or {@code
 * EntityRegainHealthEvent}, on that event's thread (the entity's region thread on Folia).
 * Cancelling leaves this hit or heal unmodified by this channel. A cancelled {@link
 * MitigationChannel#CANCEL} lets the hit through, after which the {@link MitigationChannel#MELEE}
 * multiplier may still apply and fire its own event.
 *
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface MitigationApplyEvent extends SessionEvent, Cancellable {
  /**
   * Returns the channel about to act.
   *
   * @return the channel
   */
  MitigationChannel channel();

  /**
   * Returns the current mitigation tier of the player.
   *
   * @return the tier
   */
  MitigationTier tier();

  /**
   * Returns the active mitigation rule of the player.
   *
   * @return the rule id, or {@code null} if no rule is active
   */
  @Nullable String ruleId();

  /**
   * Returns the effect about to be applied, in the ranges documented by {@link
   * ac.shard.api.mitigation.MitigationSnapshot#effects()}. For {@link MitigationChannel#PROJECTILE}
   * and {@link MitigationChannel#CRYSTAL} it is the multiplier recorded when the projectile was
   * launched or the crystal was hit.
   *
   * @return the damage or heal multiplier, or the cancel chance for {@link
   *     MitigationChannel#CANCEL}
   */
  double effect();

  /**
   * Returns the other entity involved.
   *
   * @return the victim for {@link MitigationChannel#MELEE}, {@link MitigationChannel#PROJECTILE},
   *     {@link MitigationChannel#CRYSTAL} and {@link MitigationChannel#CANCEL}, the direct damager
   *     (which may be a projectile or a mob) for {@link MitigationChannel#INCOMING}, or {@code
   *     null} for {@link MitigationChannel#HEALING}
   */
  @Nullable Entity counterpart();
}
