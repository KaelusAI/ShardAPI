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

import ac.shard.api.Page;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.ApiStatus;

/**
 * Reads the violation and mitigation log, newest first.
 *
 * <p>The log is shared by every server on the same database. Futures complete off the server
 * thread. While the database is unavailable, results can be incomplete. Any other storage error
 * completes the future exceptionally with {@link ac.shard.api.ShardStorageException}. If Shard
 * disables first, the future completes exceptionally with {@link IllegalStateException}.
 *
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface HistoryService {
  /**
   * Returns one page of a player's logged flags, newest first.
   *
   * @param playerId the player's UUID
   * @param page the zero-based page index
   * @param pageSize the number of records per page, from 1 to 100
   * @return a future completing with the page, which has no items past the last page
   * @throws IllegalArgumentException if {@code page} is negative or {@code pageSize} is outside 1
   *     to 100
   * @throws IllegalStateException if Shard is disabled
   */
  CompletableFuture<Page<ViolationRecord>> violations(UUID playerId, int page, int pageSize);

  /**
   * Returns one page of the flags of every player logged at or after a time, newest first.
   *
   * @param since the earliest flag time, inclusive. {@link Instant#EPOCH} or earlier selects the
   *     whole log.
   * @param page the zero-based page index
   * @param pageSize the number of records per page, from 1 to 100
   * @return a future completing with the page, which has no items past the last page
   * @throws IllegalArgumentException if {@code page} is negative or {@code pageSize} is outside 1
   *     to 100
   * @throws IllegalStateException if Shard is disabled
   */
  CompletableFuture<Page<ViolationRecord>> violations(Instant since, int page, int pageSize);

  /**
   * Returns the latest mitigation periods of one player.
   *
   * <p>A period is logged when it ends, so a rule that is still applied has no record yet.
   *
   * @param playerId the player's UUID
   * @param limit the maximum number of records, from 1 to 100
   * @return a future completing with the records, latest end first, or an empty list if there are
   *     none
   * @throws IllegalArgumentException if {@code limit} is outside 1 to 100
   * @throws IllegalStateException if Shard is disabled
   */
  CompletableFuture<List<MitigationRecord>> mitigations(UUID playerId, int limit);

  /**
   * Returns the latest mitigation periods of every player.
   *
   * <p>A period is logged when it ends. Records with an unreadable player UUID are skipped, so
   * fewer than {@code limit} records may be returned even when more exist.
   *
   * @param limit the maximum number of records, from 1 to 100
   * @return a future completing with the records, latest end first, or an empty list if there are
   *     none
   * @throws IllegalArgumentException if {@code limit} is outside 1 to 100
   * @throws IllegalStateException if Shard is disabled
   */
  CompletableFuture<List<MitigationRecord>> mitigations(int limit);
}
