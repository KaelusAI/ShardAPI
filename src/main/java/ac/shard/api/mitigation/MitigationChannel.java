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
package ac.shard.api.mitigation;

/**
 * What a mitigation effect acts on. May grow, so switches need a default branch.
 *
 * @see MitigationSnapshot#effects()
 * @since 2.1.0
 */
public enum MitigationChannel {
  /** Damage the player deals to other players in melee. */
  MELEE,
  /** Damage the player's projectiles deal to other players. */
  PROJECTILE,
  /** Damage from end crystals the player detonates. */
  CRYSTAL,
  /** Damage the player takes, raised without turning a survivable hit into a lethal one. */
  INCOMING,
  /** Health the player regains. */
  HEALING,
  /** Chance that a melee hit by the player is cancelled. */
  CANCEL
}
