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

/**
 * Source of an exemption. May grow, so switches need a default branch.
 *
 * @see ExemptionStatus#reasons(ExemptionScope)
 * @since 2.1.0
 */
public enum ExemptionReason {
  /**
   * The player has the {@code shard.disable}, {@code shard.exempt} or {@code shard.nomitigate}
   * permission.
   */
  PERMISSION,
  /** An {@link Exemption} granted by a plugin or an admin command. */
  GRANT,
  /**
   * The player joined from Bedrock Edition and Shard is configured to exempt Bedrock players from
   * detection.
   */
  BEDROCK,
  /**
   * The player is in a region where Shard's checks are disabled. The scope is {@link
   * ExemptionScope#DETECTION} or {@link ExemptionScope#ENFORCEMENT}, depending on the configured
   * region mode.
   */
  REGION
}
