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

import java.util.List;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;

/**
 * Buffers one model holds for a player, as part of a {@link DetectionSnapshot}.
 *
 * <p>A model that feeds its verdicts into another model's buffers holds no buffers of its own.
 *
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
@ApiStatus.Experimental
public interface ModelState {
  /**
   * Returns the model id.
   *
   * @return the id of the model
   */
  String modelId();

  /**
   * Returns the highest buffer value of the model.
   *
   * @return the highest value, unattributed buffer included, or {@code 0} if no buffer is tracked
   */
  double buffer();

  /**
   * Returns the tracked buffers.
   *
   * @return the buffers, highest value first, empty if none is tracked
   */
  @Unmodifiable
  List<LabelBuffer> buffers();

  /**
   * Returns whether the model is at its cap of tracked buffers.
   *
   * <p>At the cap, a new label replaces the weakest buffer only if that buffer is below the model's
   * flag threshold, so buffers of weaker labels may be missing.
   *
   * @return {@code true} if the number of tracked buffers reached the cap
   */
  boolean isPartial();
}
