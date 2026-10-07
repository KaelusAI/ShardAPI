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
package ac.shard.api.detection;

/**
 * Whether a player is being analysed, and if not, why.
 *
 * <p>Constants may be added in later versions, so a switch over this type needs a default branch.
 *
 * @see DetectionSnapshot#status()
 * @since 2.1.0
 */
public enum DetectionStatus {
  /** Nothing blocks analysis. Windows are sent to the inference backend as they become due. */
  ACTIVE,
  /**
   * The player is eligible, but Shard has no inference stream for the player, for example before
   * the first movement packet after joining.
   */
  IDLE,
  /**
   * No model profile has been received yet, or the player's stored buffers are not restored yet.
   */
  NOT_READY,
  /**
   * The player has the {@code shard.disable} permission or a {@link
   * ac.shard.api.exemption.ExemptionScope#DETECTION} exemption.
   */
  DISABLED,
  /** The player joined through Bedrock and the server exempts Bedrock players. */
  BEDROCK,
  /** The player is inside a WorldGuard region where Shard skips detection. */
  REGION,
  /** The player is riding an entity. */
  VEHICLE,
  /**
   * Sending is paused for a while, for example because the inference backend asked Shard to wait or
   * after connection errors.
   */
  COOLDOWN,
  /** The inference backend reported that its stream limit is reached. */
  CAPACITY,
  /**
   * Sending is paused because the inference backend sent a reply this Shard version could not read.
   */
  UNREADABLE_REPLY
}
