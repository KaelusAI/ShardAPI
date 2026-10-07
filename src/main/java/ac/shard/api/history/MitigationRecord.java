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
package ac.shard.api.history;

import ac.shard.api.mitigation.MitigationTier;
import java.time.Instant;
import java.util.UUID;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

/**
 * One period during which a mitigation rule was applied.
 *
 * @see HistoryService#mitigations(UUID, int)
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface MitigationRecord {
  /**
   * Returns the name of the server that logged the period, as set by {@code history.server-name}.
   *
   * @return the server name
   */
  String server();

  /**
   * Returns the UUID of the mitigated player.
   *
   * @return the UUID
   */
  UUID playerId();

  /**
   * Returns the ID of the applied rule.
   *
   * @return the rule ID
   */
  String ruleId();

  /**
   * Returns the tier of the applied rule.
   *
   * @return the tier, or {@link MitigationTier#NONE} if the stored tier is not recognized
   */
  MitigationTier tier();

  /**
   * Returns the peak mitigation score while the rule was applied.
   *
   * @return the peak score
   */
  double score();

  /**
   * Returns when the rule started to apply.
   *
   * @return the start time
   */
  Instant startedAt();

  /**
   * Returns when the rule stopped applying.
   *
   * <p>Shard logs a period when it ends and stores the end time with it.
   *
   * @return the end time, or {@code null} if the record has none
   */
  @Nullable Instant endedAt();
}
