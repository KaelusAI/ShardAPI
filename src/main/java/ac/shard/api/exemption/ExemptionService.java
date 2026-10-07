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

import java.util.List;
import java.util.UUID;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;

/**
 * Grants and revokes exemptions owned by plugins.
 *
 * <p>Exemptions are held per owner in memory, lost on restart and revoked when the owner plugin
 * disables. A player has at most one exemption per owner and scope, and granting again replaces it.
 * An exemption stops being active at its expiry time, and the expiry is reported through {@link
 * ac.shard.api.event.exemption.ExemptionChangeEvent} with a short delay.
 *
 * <p>All methods are thread-safe and take effect immediately for punishment and mitigation, and
 * from the next processed tick for detection.
 *
 * @see Exemption
 * @see ExemptionStatus
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface ExemptionService {
  /**
   * Starts an exemption of a player.
   *
   * @param owner the owning plugin, whose exemptions are revoked when it disables
   * @param playerId the player's UUID, online or offline
   * @return a new builder
   */
  Exemption.Builder builder(Plugin owner, UUID playerId);

  /**
   * Returns every active exemption of a player.
   *
   * <p>Permission, Bedrock and region exemptions are not included, see {@link ExemptionStatus}.
   *
   * @param playerId the player's UUID
   * @return the exemptions from every owner, admin commands included, in no particular order, or an
   *     empty list if there are none
   */
  @Unmodifiable
  List<Exemption> exemptions(UUID playerId);

  /**
   * Revokes every active exemption a plugin owns on one player.
   *
   * <p>Fires {@link ac.shard.api.event.exemption.ExemptionChangeEvent} for each revoked exemption.
   *
   * @param owner the owning plugin
   * @param playerId the player's UUID
   * @return the number of exemptions revoked
   */
  int revokeAll(Plugin owner, UUID playerId);

  /**
   * Revokes every active exemption a plugin owns, on all players.
   *
   * <p>Fires {@link ac.shard.api.event.exemption.ExemptionChangeEvent} for each revoked exemption.
   *
   * @param owner the owning plugin
   * @return the number of exemptions revoked
   */
  int revokeAll(Plugin owner);
}
