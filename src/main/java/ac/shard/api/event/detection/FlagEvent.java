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
package ac.shard.api.event.detection;

import ac.shard.api.detection.LabelBuffer;
import ac.shard.api.event.Cancellable;
import ac.shard.api.event.SessionEvent;
import ac.shard.api.punishment.PunishmentGroup;
import java.util.List;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;

/**
 * Fired when buffers of a model with the punish role cross their flag thresholds.
 *
 * <p>Not fired for a player who is exempt or who stands in a region configured to skip punishment.
 * The crossed buffers are already consumed when the event fires and stay consumed whatever the
 * outcome.
 *
 * <p>Dispatched synchronously on the player's owning thread (the entity's region thread on Folia,
 * the main thread otherwise). Cancelling records no violation level, so no {@link
 * ac.shard.api.event.punishment.PunishmentEvent} follows and no punishment action runs.
 *
 * @see ac.shard.api.event.punishment.PunishmentEvent
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface FlagEvent extends SessionEvent, Cancellable {
  /**
   * Returns the model that owns the crossed buffers. With shared buffers this may differ from the
   * model whose verdict caused the crossing.
   *
   * @return the model id
   */
  String modelId();

  /**
   * Returns the buffers that crossed their thresholds, with the values they had at the crossing.
   *
   * @return the crossed buffers, at least one
   */
  @Unmodifiable
  List<LabelBuffer> crossed();

  /**
   * Returns the punishment groups the flag is routed to.
   *
   * @return the target groups, empty if no group matches the labels, in which case nothing is
   *     punished
   */
  @Unmodifiable
  List<PunishmentGroup> groups();
}
