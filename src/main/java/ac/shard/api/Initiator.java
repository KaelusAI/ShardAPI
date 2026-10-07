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

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

/**
 * Identifies who caused a change, such as a buffer reset or an exemption grant.
 *
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface Initiator {
  /**
   * Source of a change. New constants may be added, so a {@code switch} over this type needs a
   * default branch.
   *
   * @since 2.1.0
   */
  enum Kind {
    /** A plugin acting through the API. */
    PLUGIN,
    /** A command sender running a Shard command. */
    COMMAND,
    /** Shard itself, for example a punishment that resets a violation level. */
    SHARD
  }

  /**
   * Returns the source of the change.
   *
   * @return the initiator kind
   */
  Kind kind();

  /**
   * Returns a display name for the initiator.
   *
   * @return the plugin name for {@link Kind#PLUGIN}, the command sender name for {@link
   *     Kind#COMMAND}, or {@code "Shard"} for {@link Kind#SHARD}
   */
  String name();

  /**
   * Returns the plugin that caused the change.
   *
   * @return the plugin for {@link Kind#PLUGIN}, otherwise {@code null}
   */
  @Nullable Plugin plugin();
}
