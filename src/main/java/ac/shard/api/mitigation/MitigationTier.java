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
 * Strength of a mitigation rule.
 *
 * <p>Constants are ordered by strength, and the order is part of the contract. May grow, so
 * switches need a default branch.
 *
 * @see MitigationSnapshot#tier()
 * @since 2.1.0
 */
public enum MitigationTier {
  /** No rule is applied. */
  NONE,
  /** The weakest rule tier. */
  LOW,
  /** The middle tier. Staff are alerted when a player rises to this tier or above. */
  MID,
  /** The strongest tier. */
  HIGH
}
