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
package ac.shard.api.punishment;

import java.time.Duration;
import java.util.List;
import java.util.SortedSet;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.Nullable;

/**
 * A configured punishment group.
 *
 * <p>Groups form a tree. Each group counts the flags routed to it as the player's violation level
 * in that group and runs actions configured for levels. Instances are immutable and reflect the
 * configuration at the time they were obtained.
 *
 * @see PunishmentService#groups()
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface PunishmentGroup {
  /**
   * Returns the path key of the group.
   *
   * <p>The key joins the names of the group's ancestors and the group itself with {@code /}, for
   * example {@code combat/other}. It stays stable unless a group on the path is renamed in the
   * configuration.
   *
   * @return the path key
   */
  String key();

  /**
   * Returns the name of the group, which is the last segment of its key.
   *
   * @return the name, {@code other} for a fallback group
   */
  String name();

  /**
   * Returns the key of the parent group.
   *
   * @return the parent's key, or {@code null} for a top-level group
   */
  @Nullable String parentKey();

  /**
   * Returns the direct subgroups.
   *
   * @return the subgroups in configuration order with the fallback group last, or an empty list for
   *     a leaf group
   */
  @Unmodifiable
  List<PunishmentGroup> children();

  /**
   * Returns whether this is the catch-all group of its parent, or of the top level.
   *
   * <p>A fallback group takes the flags that no sibling group matches.
   *
   * @return {@code true} for a fallback group
   */
  boolean isFallback();

  /**
   * Returns how long a flag counts toward the violation level.
   *
   * <p>A group without its own setting inherits the window of its parent.
   *
   * @return the window, or {@code null} if flags never expire
   */
  @Nullable Duration expiry();

  /**
   * Returns the violation levels that have actions configured.
   *
   * <p>Every flag runs the actions of the highest level that does not exceed the new violation
   * level. Flags below the lowest level run no actions.
   *
   * @return the levels in ascending order, each at least 1, or an empty set if the group has no
   *     actions
   */
  @Unmodifiable
  SortedSet<Integer> actionLevels();
}
