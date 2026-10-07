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
package ac.shard.api.event.config;

import ac.shard.api.event.ShardEvent;
import java.util.Set;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;

/**
 * Fired after Shard replaced part of its configuration.
 *
 * <p>Sources:
 *
 * <ul>
 *   <li>{@code /shard reload} reports every area
 *   <li>a punishment sync from the panel reports {@link ConfigurationArea#PUNISHMENT_GROUPS}
 *   <li>a change of the model profile received from the inference server reports {@link
 *       ConfigurationArea#MODELS}
 * </ul>
 *
 * <p>Objects obtained earlier, such as {@link ac.shard.api.detection.Model} and {@link
 * ac.shard.api.punishment.PunishmentGroup}, describe the old configuration. Fetch them again.
 *
 * <p>Dispatched on the Shard event thread after the change. Not cancellable.
 *
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface ConfigurationChangeEvent extends ShardEvent {
  /**
   * Returns the parts of the configuration that may have changed.
   *
   * @return the changed areas, never empty
   */
  @Unmodifiable
  Set<ConfigurationArea> areas();
}
