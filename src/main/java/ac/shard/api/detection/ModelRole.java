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
 * What a model's verdicts may drive.
 *
 * <p>Constants may be added in later versions, so a switch over this type needs a default branch.
 *
 * @see Model#roles()
 * @see Label#roles()
 * @since 2.1.0
 */
public enum ModelRole {
  /** Buffers of covered labels raise suspicious-player alerts to staff. */
  ALERT,
  /** Verdicts on covered labels feed the mitigation score. */
  MITIGATE,
  /** Flags on covered labels count toward punishments. */
  PUNISH
}
