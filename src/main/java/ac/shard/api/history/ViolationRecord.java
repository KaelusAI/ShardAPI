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

import ac.shard.api.detection.LabelId;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;

/**
 * One logged flag.
 *
 * <p>A record is written when a flag runs a punishment group action set that contains the {@code
 * [log]} action, so one flag can produce a record for each such group.
 *
 * @see HistoryService#violations(UUID, int, int)
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface ViolationRecord {
  /**
   * Returns the name of the server that logged the flag, as set by {@code history.server-name}.
   *
   * @return the server name
   */
  String server();

  /**
   * Returns the UUID of the flagged player.
   *
   * @return the UUID
   */
  UUID playerId();

  /**
   * Returns the player's name at the time of the flag.
   *
   * @return the name
   */
  String playerName();

  /**
   * Returns when the flag was raised.
   *
   * @return the flag time
   */
  Instant createdAt();

  /**
   * Returns the violation level the flag brought the player to in its punishment group.
   *
   * @return the violation level
   */
  int violationLevel();

  /**
   * Returns the labels the flag was raised on.
   *
   * @return the labels, without stored entries that cannot be parsed
   */
  @Unmodifiable
  Set<LabelId> labels();

  /**
   * Returns the buffer value at the time of the flag.
   *
   * @return the buffer value, or 0 if none was stored
   */
  double buffer();

  /**
   * Returns the player's mitigation score at the time of the flag.
   *
   * @return the mitigation score, or 0 if none was stored
   * @see ac.shard.api.mitigation.MitigationSnapshot#score()
   */
  double mitigationScore();
}
