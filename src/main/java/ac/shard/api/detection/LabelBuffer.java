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

import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

/**
 * Evidence accumulated from verdicts for one label of one player.
 *
 * <p>A probability above the label's cheat threshold raises the value. A probability below its
 * legit threshold lowers it. The label flags when the value rises strictly above {@link
 * #flagThreshold()}, and the value then drops to the label's reset value.
 *
 * @see ModelState#buffers()
 * @since 2.1.0
 */
@ApiStatus.NonExtendable
public interface LabelBuffer {
  /**
   * Returns the id of the model that owns the buffer.
   *
   * <p>A model can feed its verdicts into the buffers of another model. The owner then differs from
   * the model that produced the verdict.
   *
   * @return the owning model's id
   */
  String modelId();

  /**
   * Returns the label of the buffer.
   *
   * @return the label, or {@code null} for the model's unattributed buffer, which collects verdicts
   *     that name no public label
   */
  @Nullable LabelId label();

  /**
   * Returns the current buffer value.
   *
   * @return the value, dimensionless and never negative
   */
  double value();

  /**
   * Returns the value strictly above which the label flags.
   *
   * @return the label's own flag threshold if it has one, otherwise the model's
   */
  double flagThreshold();
}
