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
package ac.shard.api;

import java.util.List;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;

/**
 * One page of a newest-first result.
 *
 * <p>{@link #totalItems()} is not read atomically with {@link #items()}, so it can be out of step
 * when records are written meanwhile.
 *
 * @param <T> the type of the items
 * @see ac.shard.api.history.HistoryService
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface Page<T> {
  /**
   * Returns the items on this page, newest first.
   *
   * @return the items, at most {@link #pageSize()} of them, empty past the last page
   */
  @Unmodifiable
  List<T> items();

  /**
   * Returns the index of this page.
   *
   * @return the zero-based page index that was requested
   */
  int page();

  /**
   * Returns the requested page size.
   *
   * @return the maximum number of items per page
   */
  int pageSize();

  /**
   * Returns the number of items across all pages.
   *
   * @return the total item count at the time of the query
   */
  long totalItems();

  /**
   * Returns whether a page after this one has items.
   *
   * @return {@code true} if {@link #totalItems()} exceeds the items up to and including this page
   */
  default boolean hasNext() {
    return (long) (page() + 1) * pageSize() < totalItems();
  }
}
